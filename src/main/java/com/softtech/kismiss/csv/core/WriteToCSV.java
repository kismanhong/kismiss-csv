package com.softtech.kismiss.csv.core;

import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVWriteProc;
import au.com.bytecode.opencsv.CSVWriter;

import com.softtech.kismiss.csv.entity.CsvColumnEntity;

public class WriteToCSV {
	
	static final Logger log = LoggerFactory.getLogger(WriteToCSV.class);

	public static void execute(String path, final Class<?> clazz, final Collection<?> data) throws Exception {		
		CSV csv = CSV.separator(',').quote('"').create(); 
		final List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(clazz);
		csv.write(path, new CSVWriteProc() {
		    public void process(CSVWriter out) {	
		    	int index = 1;
				for (Object obj : data){
					writeRecord(csvColumnEntities, clazz, obj, out, index);	
					index++;
				}
		   }
		});
	}
	
	public static void execute(String path, final Object data) throws Exception{
		CSV csv = CSV.separator(',').quote('"').create(); 
		final List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(data.getClass());
		csv.write(path, new CSVWriteProc() {
		    public void process(CSVWriter out) {				
				writeRecord(csvColumnEntities, data.getClass(), data, out, 0);	
		   }
		});
	}
	
	private static void writeRecord(List<CsvColumnEntity> csvColumnEntities, Class<?> clazz, Object obj, CSVWriter out, int index){		
		try {	
		    out.writeNext(parseToRecord(clazz, csvColumnEntities, obj, index));
		} catch (Exception e) {
			log.error("Fail to write csv file caused by : {}", e);
		}
		
	}
	
	public static void writeToStream(final Class<?> clazz, final Collection<?> data, char separator, char quote, HttpServletResponse response, 
			ServletOutputStream out, String outputName) throws Exception{
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition","filename="+outputName);
	    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(out));
	    CSVWriter writer = new CSVWriter(buff, separator, quote);
	    writer.writeAll(parseToCollection(clazz, data));
	    writer.close();
	}
	
	public static void writeToStream(final Class<?> clazz, final Collection<?> data, char separator, HttpServletResponse response, 
			String outputName) throws Exception{
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition","filename="+outputName);
	    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
	    CSVWriter writer = new CSVWriter(buff, separator);
	    writer.writeAll(parseToCollection(clazz, data));
	    writer.close();
	}
	
	public static void writeToStream(final Class<?> clazz, final Collection<?> data, char separator, OutputStream outputStream) throws Exception{
	    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(outputStream));
	    CSVWriter writer = new CSVWriter(buff, separator);
	    writer.writeAll(parseToCollection(clazz, data));
	    writer.close();
	}
	
	private static String[] parseHeader(List<CsvColumnEntity> csvColumnEntities){
		String[] values = {};
		if(csvColumnEntities != null && csvColumnEntities.get(0) != null && StringUtils.isEmpty(csvColumnEntities.get(0).getHeader())){
			return null;
		}
		
		for (CsvColumnEntity csvColumnEntity : csvColumnEntities) {
			values = ArrayUtils.add(values, csvColumnEntity.getHeader());	
		}
		return values;
	}
	
	private static String[] parseToRecord(Class<?> clazz, List<CsvColumnEntity> csvColumnEntities, Object obj, int index) throws Exception{					

		String[] values = {};
		
		for (CsvColumnEntity csvColumnEntity : csvColumnEntities) {
			Field field = null;
			Object value = null;
				if(csvColumnEntity.isIndex()){
					value = index;
				}else{
					String[] fieldNames = StringUtils.split(csvColumnEntity.getFieldName(), ".");
					if(fieldNames.length > 1){
						PropertyDescriptor pd = new PropertyDescriptor(fieldNames[0], clazz);
						Method getter = pd.getReadMethod();
						value = getter.invoke(obj);
						
						for(int i=1; i< fieldNames.length; i++){
							if(value != null){
								pd = new PropertyDescriptor(fieldNames[i], value.getClass());
								getter = pd.getReadMethod();
								value = getter.invoke(value);
							}
						}
					}else{					
						try {
							PropertyDescriptor pd = new PropertyDescriptor(csvColumnEntity.getFieldName(), clazz);
							Method getter = pd.getReadMethod();
							value = getter.invoke(obj);
						} catch (Exception e) {
							log.warn("Cannot read from getter method, message {} , read from field value", e);
							
							field = ReflectionUtils.findField(clazz, csvColumnEntity.getFieldName());
							field.setAccessible(true);
							value = field.get(obj);
						}
					}
					
					if(StringUtils.isNotEmpty(csvColumnEntity.getPattern())){
						value = extractValue(value, csvColumnEntity.getPattern());
					}
				}
			
			values = ArrayUtils.add(values, value==null?"":value.toString());
		}
		return values;
	}
	
	private static List<String[]> parseToCollection(Class<?> clazz, Collection<?> data) throws Exception {
		List<String[]> results = new ArrayList<String[]>();
		List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(clazz);
		String[] header = parseHeader(csvColumnEntities);
		if(header != null){
			results.add(header);
		}
		
		int index = 1;
		for (Object obj : data) {
			results.add(parseToRecord(clazz, csvColumnEntities, obj, index));
			index++;
		}
		return results;
	}
	
	private static String extractValue(Object value, String pattern){
		if(value == null){
			return "";
		}else if(value.getClass().isAssignableFrom(Date.class)){
			return DateFormatUtils.format((Date)value, pattern);
		}else{
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			return decimalFormat.format((Double)value);
		}
	}
}
