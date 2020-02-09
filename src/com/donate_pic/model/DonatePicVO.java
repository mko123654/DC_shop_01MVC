package com.donate_pic.model;

import java.util.Arrays;

public class DonatePicVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	public DonatePicVO() {
		super();
	}
	
	private String donate_pic_no;
	private String donate_project_no;
	private byte[] picture;
	
	public String getDonate_pic_no() {
		return donate_pic_no;
	}
	public void setDonate_pic_no(String donate_pic_no) {
		this.donate_pic_no = donate_pic_no;
	}
	public String getDonate_project_no() {
		return donate_project_no;
	}
	public void setDonate_project_no(String donate_project_no) {
		this.donate_project_no = donate_project_no;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}
