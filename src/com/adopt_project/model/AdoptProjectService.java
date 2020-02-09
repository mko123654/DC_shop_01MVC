package com.adopt_project.model;

import java.util.List;


public class AdoptProjectService {
	
	private AdoptProjectDAO_interface dao;
	
	public AdoptProjectService() {
		dao = new AdoptProjectDAO();
	}
	
	//�s�W
	public AdoptProjectVO addAdoptProject(String founder_no, String adopter_no, String  adopt_project_name,
			Integer pet_category, String adopt_content, Integer adopt_status, String  adopt_result, Integer sex, String age,
			String breed, Integer chip, Integer birth_control, String founder_location) {
		
		AdoptProjectVO apVO = new AdoptProjectVO();
		
		apVO.setFounder_no(founder_no);
		apVO.setAdopter_no(adopter_no);
		apVO.setAdopt_project_name(adopt_project_name);
		apVO.setPet_category(pet_category);
		apVO.setAdopt_content(adopt_content); 
		apVO.setAdopt_status(adopt_status);
		apVO.setAdopt_result(adopt_result);
		apVO.setSex(sex);
		apVO.setAge(age);
		apVO.setBreed(breed);
		apVO.setChip(chip);
		apVO.setBirth_control(birth_control);
		apVO.setFounder_location(founder_location);
		dao.insert(apVO);
		
		return apVO;
	}
	
	//�ק�
	public AdoptProjectVO updateAdoptProject(String founder_no, String adopter_no, String  adopt_project_name,
			Integer pet_category, String  adopt_content, Integer adopt_status, String  adopt_result, Integer sex, String age,
			String breed, Integer chip, Integer birth_control, String founder_location) {
		
		AdoptProjectVO apVO = new AdoptProjectVO();
		
		apVO.setFounder_no(founder_no);
		apVO.setAdopter_no(adopter_no);
		apVO.setAdopt_project_name(adopt_project_name);
		apVO.setPet_category(pet_category);
		apVO.setAdopt_content(adopt_content); 
		apVO.setAdopt_status(adopt_status);
		apVO.setAdopt_result(adopt_result);
		apVO.setSex(sex);
		apVO.setAge(age);
		apVO.setBreed(breed);
		apVO.setChip(chip);
		apVO.setBirth_control(birth_control);
		apVO.setFounder_location(founder_location);
		dao.insert(apVO);
		
		return apVO;
	}
	
	//�R��
	public void deleteAdoptProject(String adopt_project_no) {
		dao.delete(adopt_project_no);
	}
	
	//�{�i�M�׽s���d��
	public AdoptProjectVO getOneProject(String adopt_project_no) {
		return dao.findByPrimaryKey(adopt_project_no);
	}
	
	//�d����
	public List<AdoptProjectVO> getAll() {
		return dao.getAll();
	}
	
	//�d��϶}���{�i��
	public List<AdoptProjectVO> getFounderProject(String founder_no) {
		return dao.findByFounderNo(founder_no);
	}
	
	//�d�|����i�쪺�{�i��
	public List<AdoptProjectVO> getAdopterProject(String adopter_no) {
		return dao.findByFounderNo(adopter_no);
	}
	
	//�d��/���{�i��
	public List<AdoptProjectVO> getPetCategoryProject(String pet_category) {
		return dao.findByFounderNo(pet_category);
	}
	
	//�ζ�Ϧa�Ϭd�{�i��
	public List<AdoptProjectVO> getFounderLocationProject(String founder_location) {
		return dao.findByFounderNo(founder_location);
	}

	
}
