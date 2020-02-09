package com.donate_list.model;

import java.util.*;
import com.donate_list.model.DonateListVO;

public interface DonateListDAO_interface {
	//���ک��ӥu��s�W�M�d��
	 public void insert(DonateListVO donateListVO);
     public DonateListVO findByPrimaryKey(String donate_list_no);
     public List<DonateListVO> findByDonateProjectNo(String donate_project_no);
     public List<DonateListVO> findByDonorNo(String donor_no);
     public List<DonateListVO> getAll();
     //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
     public List<DonateListVO> getAll(Map<String, String[]> map); 
}
