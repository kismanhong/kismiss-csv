package com.softtech.kismiss.csv.core;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSV;
import au.com.bytecode.opencsv.CSVReadProc;

import com.softtech.kismiss.csv.entity.CsvColumnEntity;

public class ReadFromCSV {
	
	static final Logger log = LoggerFactory.getLogger(ReadFromCSV.class);

	@SuppressWarnings("rawtypes")
	public static List<?> execute(String path, final Class<?> clazz) throws Exception {		
		CSV csv = CSV.separator(',').quote('"').create(); 
		final List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(clazz);
		final List results = new ArrayList();
		csv.read(path, new CSVReadProc() {
			@SuppressWarnings("unchecked")
			public void procRow(int rowIndex, String... values) {
//		        System.out.println(rowIndex + ": " + Arrays.asList(values));
		        results.add(readRecord(clazz, csvColumnEntities, values));
		    }
		});
		return results;
	}
	
	@SuppressWarnings("rawtypes")
	public static List execute(File file, final Class<?> clazz)
			throws Exception {
		CSV csv = CSV.separator(',').quote('"').create();
		final List results = new ArrayList();
		final List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(clazz);

		final List exceptions = new ArrayList();
		csv.read(file, new CSVReadProc() {
			@SuppressWarnings("unchecked")
			public void procRow(int rowIndex, String... values) {
				try {
					results.add(readRecord(clazz, csvColumnEntities, values));
				} catch (Exception e) {
					exceptions.add(e);
				}
			}
		});
		if (exceptions.size() > 0) {
			throw new Exception((Throwable) exceptions.get(0));
		}
		return results;
	}
	
//	public static Object execute(String path, final Object data){
//		CSV csv = CSV.separator(',').quote('"').create(); 
//		csv.write(path, new CSVWriteProc() {
//		    public void process(CSVWriter out) {				
//		    	readRecord(data.getClass(), data, out);	
//		   }
//		});
//	}
	
	private static Object readRecord(Class<?> clazz, List<CsvColumnEntity> csvColumnEntities, String[] values){		
		
		Object object = null;
		try {
			object = clazz.newInstance();
			for (int i=0; i < csvColumnEntities.size(); i++) {
				Field field = null;
				Object value = null;
				
				String[] fieldNames = StringUtils.split(csvColumnEntities.get(i).getFieldName(), ".");
				if(fieldNames.length > 1){
					field = object.getClass().getDeclaredField(fieldNames[0]);
					value = field.get(object);
					for(int j=1; j< fieldNames.length; j++){
						field = value.getClass().getDeclaredField(fieldNames[j]);
						field.setAccessible(true);
					}
				}else{
					field = clazz.getDeclaredField(csvColumnEntities.get(i).getFieldName());
					field.setAccessible(true);
				}
				field.set(object, values[i]);
			}
		} catch (Exception e) {
			log.error("Fail to read csv file caused by : {}", e);
		}
		
		return object;
//		try {					
//			final List<CsvColumnEntity> csvColumnEntities = CsvUtils.getTinyCsvColumnEntities(clazz);
//			String[] values = {};
//		    for (CsvColumnEntity csvColumnEntity : csvColumnEntities) {
//				Field field = null;
//				Object value = null;
//				
//				String[] fieldNames = StringUtils.split(csvColumnEntity.getFieldName(), ".");
//				if(fieldNames.length > 1){
//					field = obj.getClass().getDeclaredField(fieldNames[0]);
//					value = field.get(obj);
//					for(int i=1; i< fieldNames.length; i++){
//						Field fld = value.getClass().getDeclaredField(fieldNames[i]);
//						fld.setAccessible(true);
//						if(value != null){
//							value = fld.get(value);
//						}
//					}
//				}else{
//					field = clazz.getDeclaredField(csvColumnEntity.getFieldName());
//					field.setAccessible(true);
//					value = field.get(obj);
//				}
//				
//				values = ArrayUtils.add(values, value==null?null:value.toString());
//				
//			}
//		    in.writeNext(values);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Fail to read csv file caused by : {}", e.getMessage());
//		}
		
	}
}
