

package com.donate_project.model;

import java.sql.Date;

public class DonateProjectVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	
	public DonateProjectVO() {
		super();
	}
	
	
	private String donate_project_no;
	private String donate_project_name;
	private Integer project_type;
	private String founder_no;
	private Integer goal;
	private String donate_content;
	private Date start_date;
	private Date end_date;
	private Integer money;
	private String donate_result;


	public String getDonate_project_no() {
		return donate_project_no;
	}
	public void setDonate_project_no(String donate_project_no) {
		this.donate_project_no = donate_project_no;
	}
	public String getDonate_project_name() {
		return donate_project_name;
	}
	public void setDonate_project_name(String donate_project_name) {
		this.donate_project_name = donate_project_name;
	}
	public Integer getProject_type() {
		return project_type;
	}
	public void setProject_type(Integer project_type) {
		this.project_type = project_type;
	}
	public String getFounder_no() {
		return founder_no;
	}
	public void setFounder_no(String founder_no) {
		this.founder_no = founder_no;
	}
	public Integer getGoal() {
		return goal;
	}
	public void setGoal(Integer goal) {
		this.goal = goal;
	}
	public String getDonate_content() {
		return donate_content;
	}
	public void setDonate_content(String donate_content) {
		this.donate_content = donate_content;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getDonate_result() {
		return donate_result;
	}
	public void setDonate_result(String donate_result) {
		this.donate_result = donate_result;
	}

	
}
