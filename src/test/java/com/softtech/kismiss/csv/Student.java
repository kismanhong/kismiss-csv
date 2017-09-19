package com.softtech.kismiss.csv;

import java.util.Date;

import com.softtech.kismiss.csv.annotations.CsvColumn;
import com.softtech.kismiss.csv.annotations.CsvColumns;

//@CsvColumns({ 
//	@CsvColumn(position = 1, fieldName = "name"),
//	@CsvColumn(position = 2, fieldName = "address"),
//	@CsvColumn(position = 3, fieldName = "telephone"),
//	@CsvColumn(position = 4, fieldName = "superAddress"),
//	@CsvColumn(position = 5, fieldName = "date", pattern = "dd-MMM-yyyy"),
//	@CsvColumn(position = 6, fieldName = "course.name")
//})
public class Student extends Address {

	@CsvColumn(position = 1)
	private String name;
	
	@CsvColumn(position = 2)
	private String address;
	
	@CsvColumns( { 
			@CsvColumn(position = 3), 
			@CsvColumn(position = 4, fieldName = "superAddress")})
	private String telephone;
	
	@CsvColumn(position = 5, pattern = "dd-MMM-yyyy")
	private Date date;
	
	@CsvColumn(position = 6, fieldName = "course.name")
	private Course course;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}
