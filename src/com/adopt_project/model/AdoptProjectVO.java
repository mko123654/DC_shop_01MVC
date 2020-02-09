package com.adopt_project.model;


public class AdoptProjectVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String  adopt_project_no;
	private String  founder_no; 
	private String  adopter_no;
	private String  adopt_project_name;
	private Integer pet_category;
	private String  adopt_content;
	private Integer adopt_status;
	private String  adopt_result;
	private Integer sex;
	private String  age;
	private String  breed;
	private Integer chip;
	private Integer birth_control;
	private String  founder_location;
	public String getAdopt_project_no() {
		return adopt_project_no;
	}
	public void setAdopt_project_no(String adopt_project_no) {
		this.adopt_project_no = adopt_project_no;
	}
	public String getFounder_no() {
		return founder_no;
	}
	public void setFounder_no(String founder_no) {
		this.founder_no = founder_no;
	}
	public String getAdopter_no() {
		return adopter_no;
	}
	public void setAdopter_no(String adopter_no) {
		this.adopter_no = adopter_no;
	}
	public String getAdopt_project_name() {
		return adopt_project_name;
	}
	public void setAdopt_project_name(String adopt_project_name) {
		this.adopt_project_name = adopt_project_name;
	}
	public Integer getPet_category() {
		return pet_category;
	}
	public void setPet_category(Integer pet_category) {
		this.pet_category = pet_category;
	}
	public String getAdopt_content() {
		return adopt_content;
	}
	public void setAdopt_content(String adopt_content) {
		this.adopt_content = adopt_content;
	}
	public Integer getAdopt_status() {
		return adopt_status;
	}
	public void setAdopt_status(Integer adopt_status) {
		this.adopt_status = adopt_status;
	}
	public String getAdopt_result() {
		return adopt_result;
	}
	public void setAdopt_result(String adopt_result) {
		this.adopt_result = adopt_result;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public Integer getChip() {
		return chip;
	}
	public void setChip(Integer chip) {
		this.chip = chip;
	}
	public Integer getBirth_control() {
		return birth_control;
	}
	public void setBirth_control(Integer birth_control) {
		this.birth_control = birth_control;
	}
	public String getFounder_location() {
		return founder_location;
	}
	public void setFounder_location(String founder_location) {
		this.founder_location = founder_location;
	}
	
}