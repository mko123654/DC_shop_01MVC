package com.adopt_list.model;

import java.sql.Date;
import java.util.List;


public class AdoptListService {
	
	private AdoptListDAO_interface dao;
	
	public AdoptListService() {
		dao = new AdoptListDAO();
	}
	
	//穝糤
	public AdoptListVO addAdoptList(String adopt_project_no, String adopter_no, String real_name, String phone,
			Integer age, String id_card, String address, String email, String sex, Integer status) {
		
		AdoptListVO alVO = new AdoptListVO();
		alVO.setAdopt_project_no(adopt_project_no);
		alVO.setAdopter_no(adopter_no);
		alVO.setReal_name(real_name);
		alVO.setPhone(phone);
		alVO.setAge(age); 
		alVO.setId_card(id_card);
		alVO.setAddress(address);
		alVO.setEmail(email);
		alVO.setSex(sex);
		alVO.setStatus(status);
		dao.insert(alVO);

		return alVO;
	}
	
	//埃
	public void deleteAdoptList(String adopt_list_no) {
		dao.delete(adopt_list_no);
	}
	
	//琩高(ノ瑈絪腹)
	public AdoptListVO findByPrimaryKey(String adopt_list_no) {
		return dao.findByPrimaryKey(adopt_list_no);
	}

	//琩高(ノ盡絪腹)
	public List<AdoptListVO> findByProjectNo(String adopt_project_no) {
		return dao.findByProjectNo(adopt_project_no);
	}
	
	//琩高(ノ穦絪腹)
	public List<AdoptListVO> findByAdopterNo(String Donor_No) {
		return dao.findByAdopterNo(Donor_No);
	}
	
	//琩场
	public List<AdoptListVO> getAll() {
		return dao.getAll();
	}

}
