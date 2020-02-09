package com.adopt_pic.model;

import java.util.*;


public interface AdoptPicDAO_interface {
          public void insert(AdoptPicVO adopt_picVO);
          public void delete(String adopt_Pic_No);
          public AdoptPicVO findByPrimaryKey(String adopt_Pic_No);
          public List<AdoptPicVO> findByAdoptProjectNo(String adopt_Project_No);
          public List<AdoptPicVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
