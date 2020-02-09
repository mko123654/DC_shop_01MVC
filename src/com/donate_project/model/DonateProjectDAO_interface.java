package com.donate_project.model;

import java.util.*;

import com.donate_pic.model.DonatePicVO;
import com.donate_project.model.DonateProjectVO;


public interface DonateProjectDAO_interface {
	 public void insert(DonateProjectVO dpVO);
     public void update(DonateProjectVO dpVO);
     public void delete(String donate_project_no);
     public DonateProjectVO findByPrimaryKey(String donate_project_no);
     public List<DonateProjectVO> getAll();
     
     //�P�ɷs�W�M�ץH��Donate_Pic��檺�Ϥ�
     public void insertWithPics(DonateProjectVO dpVO , List<DonatePicVO> list);
     //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
     public List<DonateProjectVO> getAll(Map<String, String[]> map); 
}
