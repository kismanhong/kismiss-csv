package com.softtech.kismiss.csv.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softtech.kismiss.csv.annotations.CsvColumn;
import com.softtech.kismiss.csv.annotations.CsvColumns;
import com.softtech.kismiss.csv.entity.CsvColumnEntity;

public class CsvUtils {
	
	static final Logger log = LoggerFactory.getLogger(CsvUtils.class);
	
	public static List<CsvColumnEntity> getTinyCsvColumnEntities(Class<?> clazz) throws Exception{
		List<CsvColumnEntity> csvColumnEntities = getCsvColumnEntities(clazz);
		Collections.sort(csvColumnEntities, new Comparator<CsvColumnEntity>(){
		     public int compare(CsvColumnEntity o1, CsvColumnEntity o2){
		         if(o1.getPosition() == o2.getPosition())
		             return 0;
		         return o1.getPosition() < o2.getPosition() ? -1 : 1;
		     }
		});
		return csvColumnEntities;
	}
	
	private static List<CsvColumnEntity> getCsvColumnEntities(Class<?> clazz) throws Exception{
		List<CsvColumnEntity> csvColumnEntities = new ArrayList<CsvColumnEntity>();
		CsvColumns csvColumns = clazz.getAnnotation(CsvColumns.class);
		if(csvColumns != null){
			CsvColumn[] csvColumns2 = csvColumns.value();
			for (CsvColumn csvColumn : csvColumns2) {
				if(StringUtils.isEmpty(csvColumn.fieldName())){
					log.error("Field name is required for field with position {}", csvColumn.position());
					throw new Exception("Field name is required for field with position " + csvColumn.position());
				}else{
					CsvColumnEntity csvColumnEntity = new CsvColumnEntity(
							csvColumn.position(), csvColumn.fieldName(), 
									csvColumn.header(), csvColumn.index(), csvColumn.pattern());
					csvColumnEntities.add(csvColumnEntity);
				}
			}
		}else{	
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				CsvColumn csvColumn = field.getAnnotation(CsvColumn.class);
				if(csvColumn != null){
					CsvColumnEntity csvColumnEntity = new CsvColumnEntity(
							csvColumn.position(), StringUtils.isEmpty(csvColumn.fieldName())?field.getName():csvColumn.fieldName(), 
									csvColumn.header(), csvColumn.index(), csvColumn.pattern());
					csvColumnEntities.add(csvColumnEntity);
				}
				
				CsvColumns csvClmns = field.getAnnotation(CsvColumns.class);
				if(csvClmns != null){
					constructCsvColumnEntity(csvClmns.value(), csvColumnEntities, field);
				}
			}
		}
		return csvColumnEntities;
	}
	
	private static void constructCsvColumnEntity(CsvColumn[] csvColumns, List<CsvColumnEntity> csvColumnEntities, Field field){
		if(csvColumns != null){
			for (CsvColumn csvColumn2 : csvColumns) {
				CsvColumnEntity csvColumnEntity = new CsvColumnEntity(
						csvColumn2.position(), StringUtils.isEmpty(csvColumn2.fieldName())?field.getName():csvColumn2.fieldName(), 
								csvColumn2.header(), csvColumn2.index(), csvColumn2.pattern());
				csvColumnEntities.add(csvColumnEntity);
			}
		}
	}
}
