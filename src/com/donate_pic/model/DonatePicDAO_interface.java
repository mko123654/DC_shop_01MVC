package com.donate_pic.model;

import java.util.List;


public interface DonatePicDAO_interface {
	 public void insert(DonatePicVO dppVO);
     public void delete(String donate_pic_no);
     public DonatePicVO findByPrimaryKey(String donate_pic_no);
     public List<DonatePicVO> findByDonateProjectNo(String donate_project_no);
     public List<DonatePicVO> getAll();
     
     //�P�ɷs�W�M�׻P�Ϥ�
     public void insert2 (DonatePicVO donatePicVO , java.sql.Connection con);

}

