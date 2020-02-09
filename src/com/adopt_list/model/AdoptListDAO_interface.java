package com.adopt_list.model;

import java.util.*;

public interface AdoptListDAO_interface {
          public void insert(AdoptListVO alVO);
          public void delete(String adopt_list_no);
          public AdoptListVO findByPrimaryKey(String adopt_list_no);
          public List<AdoptListVO> findByProjectNo(String adopt_project_no);
          public List<AdoptListVO> findByAdopterNo(String adopter_no);
          public List<AdoptListVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
