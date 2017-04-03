package com.mtech.dissertation.enterprisebatch.data;

public class EnterpriseData {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private int age;
	private int companytenure;
	private int workinghour;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public EnterpriseData() {

	}

	public EnterpriseData(Long id, String firstName, String lastName,
			String email, String gender, int age, int companytenure,
			int workinghour) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.companytenure = companytenure;
		this.workinghour = workinghour;
	}

	@Override
	public String toString() {
		return id + "," + firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getCompanytenure() {
		return companytenure;
	}

	public void setCompanytenure(int companytenure) {
		this.companytenure = companytenure;
	}

	public int getWorkinghour() {
		return workinghour;
	}

	public void setWorkinghour(int workinghour) {
		this.workinghour = workinghour;
	}

}
