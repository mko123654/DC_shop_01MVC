package com.adopt_project.model;

import java.util.*;

public interface AdoptProjectDAO_interface {
          public void insert(AdoptProjectVO apVO);
          public void update(AdoptProjectVO apVO);
          public void delete(String adopt_project_no);
          public AdoptProjectVO findByPrimaryKey(String adopt_project_no);
          public List<AdoptProjectVO> findByFounderNo(String founder_no);
          public List<AdoptProjectVO> findByAdopterNo(String adopter_no);
          public List<AdoptProjectVO> findByPetCategory(Integer pet_category);
          public List<AdoptProjectVO> findByFounderLocation(String founder_location);
          public List<AdoptProjectVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
