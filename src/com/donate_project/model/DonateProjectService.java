package com.donate_project.model;

import java.sql.Date;
import java.util.List;

import com.donate_pic.model.DonatePicVO;

public class DonateProjectService {
	
	private DonateProjectDAO_interface dao;
	
	public DonateProjectService() {
		dao = new DonateProjectJDBCDAO();
	}
	
	//新增
	public DonateProjectVO addDonateProject(String founder_no, Integer project_type, 
			String dpnate_project_name, String donate_content, Integer goal, Date start_date, 
			Date end_date) {
		
		DonateProjectVO dpVO  = new DonateProjectVO();
		
		dpVO.setFounder_no(founder_no);
		dpVO.setProject_type(project_type);
		dpVO.setDonate_project_name(dpnate_project_name);
		dpVO.setDonate_content(donate_content);
		dpVO.setGoal(goal);
		dpVO.setStart_date(start_date);
		dpVO.setEnd_date(end_date);
		dao.insert(dpVO);
		
		return dpVO;
	}
	
	//修改
	public DonateProjectVO updateDonateProject(String donate_project_no, String founder_no, Integer project_type, 
			String donate_project_name, String donate_content, Integer goal, Date start_date, 
			Date end_date, String donate_result) {
		
		DonateProjectVO dpVO  = new DonateProjectVO();
		
		dpVO.setDonate_project_no(donate_project_no);
		dpVO.setFounder_no(founder_no);
		dpVO.setProject_type(project_type);
		dpVO.setDonate_project_name(donate_project_name);
		dpVO.setDonate_content(donate_content);
		dpVO.setGoal(goal);
		dpVO.setStart_date(start_date);
		dpVO.setEnd_date(end_date);
		dpVO.setDonate_result(donate_result);
		dao.update(dpVO);
		
		return dpVO;
	}
	
	//刪除
	public void deleteDonateProject(String donate_project_no) {
		dao.delete(donate_project_no);
	}
	
	//查詢(用專案編號)
	public DonateProjectVO getOneProject(String donate_project_no) {
		return dao.findByPrimaryKey(donate_project_no);
	}
	
	//查全部
	public List<DonateProjectVO> getAll() {
		return dao.getAll();
	}
	
	//新增專案同時新增圖片  (你忘了寫~)
	public void insertWithDPics(DonateProjectVO dpVO,List<DonatePicVO> dPicVOList){
		dao.insertWithPics(dpVO, dPicVOList);
	}

}
