package com.softtech.kismiss.csv.entity;

public class CsvColumnEntity {
	private int position;
	private String fieldName;
	private String header;
	private boolean index;
	private String pattern;
	
	public CsvColumnEntity(int position, String fieldName, String header, boolean index, String pattern){
		this.position = position;
		this.fieldName = fieldName;
		this.header = header;
		this.index = index;
		this.pattern = pattern;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isIndex() {
		return index;
	}

	public void setIndex(boolean index) {
		this.index = index;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	

}
