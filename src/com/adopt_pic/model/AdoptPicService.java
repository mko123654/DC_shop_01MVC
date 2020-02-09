package com.adopt_pic.model;

import java.util.List;


public class AdoptPicService {
	
	private AdoptPicDAO_interface dao;
	
	public AdoptPicService() {
		dao = new AdoptPicDAO();
	}
	
	//新增
	public AdoptPicVO addAdoptPic(String adopt_project_no, byte[] picture) {
		
		AdoptPicVO appVO  = new AdoptPicVO();
		appVO.setAdopt_project_no(adopt_project_no);
		appVO.setPicture(picture);
		dao.insert(appVO);
		
		return appVO;
	}
	
	//刪除
	public void deleteAdoptPic(String adopt_pic_no) {
		dao.delete(adopt_pic_no);
	}
	
	//查詢(流水號)
		public AdoptPicVO getOnePic(String adopt_pic_no) {
			return dao.findByPrimaryKey(adopt_pic_no);
		}
	
	//查詢(用專案編號)
	public List<AdoptPicVO> findByAdpotProjectNo(String adopt_project_no) {
		return dao.findByAdoptProjectNo(adopt_project_no);
	}	

}
