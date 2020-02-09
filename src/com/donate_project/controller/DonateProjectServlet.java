package com.donate_project.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.donate_pic.model.DonatePicVO;
import com.donate_project.controller.UploadPicServlet.UploadPic;
import com.donate_project.model.*;

@MultipartConfig
public class DonateProjectServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String donate_project_no = req.getParameter("donate_project_no");
				if (donate_project_no == null || (donate_project_no.trim()).length() == 0) {
					errorMsgs.add("�п�J�Ҹ�M�׽s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				DonateProjectVO dpVO = dpSvc.getOneProject(donate_project_no);
				if (dpVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("dpVO", dpVO); // ��Ʈw���X��dpVO����,�s�Jreq
				String url = "/donateProject/listOneDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				/***************************2.�}�l�d�߸��****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				DonateProjectVO dpVO = dpSvc.getOneProject(donate_project_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("dpVO", dpVO);         // ��Ʈw���X��dpVO����,�s�Jreq
				String url = "/donateProject/update_donateProject_input.jsp"; 
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/listAllDonateProject.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				String donate_project_name = req.getParameter("donate_project_name");
				if (donate_project_name == null || donate_project_name.trim().length() == 0) {
					errorMsgs.add("�M�צW��: �ФŪť�");
				}
				
				Integer project_type = new Integer(req.getParameter("project_type").trim());
				
				String founder_no = req.getParameter("founder_no").trim();
				if (founder_no == null || founder_no.trim().length() == 0) {
					errorMsgs.add("��Ϸ|���s��: �ФŪť�");
				}	
				
				Integer goal = null;
				try {
					goal = new Integer(req.getParameter("goal").trim());
				} catch (NumberFormatException e) {
					goal = 0;
					errorMsgs.add("�ؼЪ��B�ж�Ʀr.");
				}
				
				String donate_content = req.getParameter("donate_content");
				if (donate_content == null || donate_content.length() == 0) {
					errorMsgs.add("�M�פ��e: �ФŪť�");
				}	
				
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}
				
				String donate_result = req.getParameter("donate_result");	
				

				DonateProjectVO dpVO  = new DonateProjectVO();
				dpVO.setDonate_project_no(donate_project_no);
				dpVO.setFounder_no(founder_no);
				dpVO.setProject_type(project_type);
				dpVO.setDonate_project_name(donate_project_name);
				dpVO.setDonate_content(donate_content);
				dpVO.setGoal(goal);
				dpVO.setStart_date(start_date);
				dpVO.setEnd_date(end_date);
				dpVO.setDonate_result(donate_result);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpVO", dpVO); // �t����J�榡���~��dpVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/update_donateProject_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				dpVO = dpSvc.updateDonateProject(donate_project_no, founder_no, project_type, donate_project_name, donate_content, goal,
						start_date, end_date, donate_result);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("dpVO", dpVO); // ��Ʈwupdate���\��,���T����dpVO����,�s�Jreq
				String url = "/donateProject/listOneDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/update_donateProject_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String donate_project_name = req.getParameter("donate_project_name");
				if (donate_project_name == null || donate_project_name.trim().length() == 0) {
					errorMsgs.add("�M�צW��: �ФŪť�");
				}
				
				Integer project_type = new Integer(req.getParameter("project_type").trim());
				
				String founder_no = req.getParameter("founder_no").trim();
				if (founder_no == null || founder_no.trim().length() == 0) {
					errorMsgs.add("��Ϸ|���s��: �ФŪť�");
				}	
				
				Integer goal = null;
				try {
					goal = new Integer(req.getParameter("goal").trim());
				} catch (NumberFormatException e) {
					goal = 0;
					errorMsgs.add("�ؼЪ��B�ж�Ʀr.");
				}
				
				String donate_content = req.getParameter("donate_content");
				if (donate_content == null || donate_content.length() == 0) {
					errorMsgs.add("�M�פ��e: �ФŪť�");
				}	
				
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�}�l���!");
				}
				
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�������!");
				}
				
				//�B�z�M�׹Ϥ�
				/*
				Collection<Part> parts = req.getParts();
				List<byte[]> dPics = new ArrayList<byte[]>();  //��Ϥ��̱ƱƯ��s�i�h
				for (Part dPic : parts) {
					InputStream inPic = dPic.getInputStream();  //�g�Ϥ��i��Ʈw
					byte[] emp_pic = null;
					if (inPic.available()!=0) {
						emp_pic = new byte[inPic.available()];  //�Hbyte[]�Φ��s�Jlist
						dPics.add(emp_pic);
						inPic.read(emp_pic);
						inPic.close();
					}
				}
				*/
				List<Part> parts = new ArrayList<Part>();
				//Part part = req.getPart("dPic1");
				parts.add(req.getPart("dPic1"));
				parts.add(req.getPart("dPic2"));
				parts.add(req.getPart("dPic3"));
				List<byte[]> dPics = new ArrayList<byte[]>();  //��Ϥ��̱ƱƯ��s�i�h
				for (Part dPic : parts) {
					InputStream inPic = dPic.getInputStream();  //�g�Ϥ��i��Ʈw
					byte[] emp_pic = null;
					if (inPic.available()!=0) {
						emp_pic = new byte[inPic.available()];  //�Hbyte[]�Φ��s�Jlist
						dPics.add(emp_pic);
						inPic.read(emp_pic);
						inPic.close();
					}
				}
				
				
				
				DonateProjectVO dpVO  = new DonateProjectVO();
				dpVO.setFounder_no(founder_no);
				dpVO.setProject_type(project_type);
				dpVO.setDonate_project_name(donate_project_name);
				dpVO.setDonate_content(donate_content);
				dpVO.setGoal(goal);
				dpVO.setStart_date(start_date);
				dpVO.setEnd_date(end_date);
				
				//�M�׹Ϥ��Q��donate_picVO
				List<DonatePicVO> dPicVOList = new ArrayList<DonatePicVO>();
				for (byte[] dPicture :dPics) {  //��Ϥ��Q��VO //��i�h����A�s�쫬�A��VO��list
					DonatePicVO dPicVO = new DonatePicVO();  //�Ϥ��զX��-���ݱ����M�צۼW�D��
					dPicVO.setPicture(dPicture);
					dPicVOList.add(dPicVO);
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpVO", dpVO); // �t����J�榡���~��dpVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/addDonateProject.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
//				DonateProjectService dpSvc = new DonateProjectService();  //�o�ӵL�k�P�ɾާ@��Ӫ��
//				dpVO = dpSvc.addDonateProject(founder_no, project_type, donate_project_name, donate_content, goal,
//						start_date, end_date);
				
				//�s�W�M�� �]�P�ɷs�W�Ϥ���檺�@�C�C��� ����M�ת��ۼW�D��
				DonateProjectService dpSvc = new DonateProjectService();
				dpSvc.insertWithDPics(dpVO, dPicVOList);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/donateProject/listAllDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/donateProject/addDonateProject.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				/***************************2.�}�l�R�����***************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				dpSvc.deleteDonateProject(donate_project_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/donateProject/listAllDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/listAllDonateProject.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
