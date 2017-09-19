# Kismiss-csv

[Powered by: Kisman Hong]

Read and write csv file.

# Example:

```sh
public class Address {

	private String superAddress;

	public String getSuperAddress() {
		return superAddress;
	}

	public void setSuperAddress(String superAddress) {
		this.superAddress = superAddress;
	}
	
}


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
		WriteToCSV.execute("[path]/csv-file.csv", Student.class, students);
		
		List results = ReadFromCSV.execute("K:/test/csv-file.csv", Student.class);
		for (Object object : results) {
			Student std = (Student) object;
			System.out.println(ToStringBuilder.reflectionToString(std));
		}
```

