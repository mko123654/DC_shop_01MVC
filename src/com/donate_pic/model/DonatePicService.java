package com.donate_pic.model;

import java.util.List;



public class DonatePicService {
	
	private DonatePicDAO_interface dao;
	
	public DonatePicService() {
		dao = new DonatePicDAO();
	}
	
	//�s�W
	public DonatePicVO addDonatePic(String donate_project_no, byte[] picture) {
		
		DonatePicVO dppVO  = new DonatePicVO();
		dppVO.setDonate_project_no(donate_project_no);
		dppVO.setPicture(picture);
		dao.insert(dppVO);
		
		return dppVO;
	}
	
	//insert2  (�ѤF�g~~)
	public void insert2(DonatePicVO donatePicVO, java.sql.Connection con ) {
		dao.insert2(donatePicVO, con);
	}
	
	//�R��
	public void deleteDonatePic(String donate_pic_no) {
		dao.delete(donate_pic_no);
	}
	
	//�d��(�y����)
	public DonatePicVO getOnePic(String donate_pic_no) {
		return dao.findByPrimaryKey(donate_pic_no);
	}
	
	//�d��(�αM�׽s��)
	public List<DonatePicVO> findPicByDPno(String donate_project_no) {
		return dao.findByDonateProjectNo(donate_project_no);
	}
	
	//�d����
	public List<DonatePicVO> getAll() {
		return dao.getAll();
	}
	
}
