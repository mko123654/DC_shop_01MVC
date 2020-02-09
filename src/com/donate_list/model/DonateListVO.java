package com.donate_list.model;


import java.sql.Date;

public class DonateListVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	public DonateListVO() {
		super();
	}
	
	private String donate_list_no;
	private String donate_project_no;
	private String donor_no;
	private Integer amount;
	private Date donate_date;

	public String getDonate_list_no() {
		return donate_list_no;
	}
	public void setDonate_list_no(String donate_list_no) {
		this.donate_list_no = donate_list_no;
	}
	public String getDonate_project_no() {
		return donate_project_no;
	}
	public void setDonate_project_no(String donate_project_no) {
		this.donate_project_no = donate_project_no;
	}
	public String getDonor_no() {
		return donor_no;
	}
	public void setDonor_no(String donor_no) {
		this.donor_no = donor_no;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Date getDonate_date() {
		return donate_date;
	}
	public void setDonate_date(Date donate_date) {
		this.donate_date = donate_date;
	}

}
