package com.donate_list.model;

import java.util.List;


public class DonateListService {
	
	private DonateListDAO_interface dao;
	
	public DonateListService() {
		dao = new DonateListDAO();
	}
	
	//新增
	public DonateListVO addDonateList(String donate_project_no, 
			String donor_no, Integer amount) {
		
		DonateListVO dlVO = new DonateListVO();
		
		dlVO.setDonate_project_no(donate_project_no);
		dlVO.setDonor_no(donor_no);
		dlVO.setAmount(amount);
		dao.insert(dlVO);
		
		return dlVO;
	}
	
	//查詢(用流水編號)
	public DonateListVO findByPrimaryKey(String donate_list_no) {
		return dao.findByPrimaryKey(donate_list_no);
	}
		
	//查詢(用專案編號)
	public List<DonateListVO> findByDonateProjectNo(String donate_project_no) {
		return dao.findByDonateProjectNo(donate_project_no);
	}
		
	//查詢(用會員編號)
	public List<DonateListVO> findByDonorNo(String donor_no) {
		return dao.findByDonorNo(donor_no);
	}
		
	//查全部
	public List<DonateListVO> getAll() {
		return dao.getAll();
	}

}
