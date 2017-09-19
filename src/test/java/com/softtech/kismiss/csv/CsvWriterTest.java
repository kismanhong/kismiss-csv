package com.softtech.kismiss.csv;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.softtech.kismiss.csv.core.WriteToCSV;

public class CsvWriterTest {

	@Test
	public void testWrite() throws Exception{
		
		List<Student> students = new ArrayList<Student>();
		
		Student student = new Student();
		student.setAddress("Jalan Ubud belok dikit");
		student.setName("Kisman");
		student.setTelephone("08567077556");
		student.setSuperAddress("adshjhdjhfdjs");
		student.setDate(new Date());
		
		Course course = new Course();
		course.setName("course 1");
		course.setDescription("desc 1");
		student.setCourse(course);
		
		students.add(student);
		Student student1 = new Student();
		student1.setAddress("Jalan Denpasar");
		student1.setName("Yogi");
		student1.setTelephone("021344545436");
		student1.setSuperAddress("super address");
		student1.setDate(new Date());
		
		course = new Course();
		course.setName("course 2");
		course.setDescription("desc 2");
		student1.setCourse(course);
		
		students.add(student1);
		WriteToCSV.execute("/Users/kisman/Documents/test/csv-file.csv", Student.class, students);
		
//		List results = ReadFromCSV.execute("K:/test/csv-file.csv", Student.class);
//		for (Object object : results) {
//			Student std = (Student) object;
//			System.out.println(ToStringBuilder.reflectionToString(std));
//		}
	}
}
