package com.adopt_list.model;
import java.sql.Clob;
import java.sql.Date;

public class AdoptListVO implements java.io.Serializable{

	private String  adopt_list_no;
	private String  adopt_project_no; 
	private String  adopter_no;
	private String  real_name;
	private String 	phone;
	private Integer age;
	private String  id_card;
	private String  address;
	private String  email;
	private String  sex;
	private Date  	date_of_application;
	private Integer status;
	public String getAdopt_list_no() {
		return adopt_list_no;
	}
	public void setAdopt_list_no(String adopt_list_no) {
		this.adopt_list_no = adopt_list_no;
	}
	public String getAdopt_project_no() {
		return adopt_project_no;
	}
	public void setAdopt_project_no(String adopt_project_no) {
		this.adopt_project_no = adopt_project_no;
	}
	public String getAdopter_no() {
		return adopter_no;
	}
	public void setAdopter_no(String adopter_no) {
		this.adopter_no = adopter_no;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getDate_of_application() {
		return date_of_application;
	}
	public void setDate_of_application(Date date_of_application) {
		this.date_of_application = date_of_application;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}