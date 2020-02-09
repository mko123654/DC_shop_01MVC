package com.adopt_pic.model;
import java.sql.Clob;
import java.sql.Date;

public class AdoptPicVO implements java.io.Serializable{

	private String  adopt_pic_no;
	private String  adopt_project_no; 
	private byte[]  picture;
	
	public String getAdopt_pic_no() {
		return adopt_pic_no;
	}
	public void setAdopt_pic_no(String adopt_pic_no) {
		this.adopt_pic_no = adopt_pic_no;
	}
	public String getAdopt_project_no() {
		return adopt_project_no;
	}
	public void setAdopt_project_no(String adopt_project_no) {
		this.adopt_project_no = adopt_project_no;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}