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
     
     //同時新增專案以及Donate_Pic表格的圖片
     public void insertWithPics(DonateProjectVO dpVO , List<DonatePicVO> list);
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
     public List<DonateProjectVO> getAll(Map<String, String[]> map); 
}
