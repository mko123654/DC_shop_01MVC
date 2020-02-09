package com.donate_list.model;

import java.util.*;
import com.donate_list.model.DonateListVO;

public interface DonateListDAO_interface {
	//捐款明細只能新增和查詢
	 public void insert(DonateListVO donateListVO);
     public DonateListVO findByPrimaryKey(String donate_list_no);
     public List<DonateListVO> findByDonateProjectNo(String donate_project_no);
     public List<DonateListVO> findByDonorNo(String donor_no);
     public List<DonateListVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
     public List<DonateListVO> getAll(Map<String, String[]> map); 
}
