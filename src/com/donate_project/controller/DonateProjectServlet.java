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
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String donate_project_no = req.getParameter("donate_project_no");
				if (donate_project_no == null || (donate_project_no.trim()).length() == 0) {
					errorMsgs.add("請輸入募資專案編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				DonateProjectVO dpVO = dpSvc.getOneProject(donate_project_no);
				if (dpVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("dpVO", dpVO); // 資料庫取出的dpVO物件,存入req
				String url = "/donateProject/listOneDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				/***************************2.開始查詢資料****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				DonateProjectVO dpVO = dpSvc.getOneProject(donate_project_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("dpVO", dpVO);         // 資料庫取出的dpVO物件,存入req
				String url = "/donateProject/update_donateProject_input.jsp"; 
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/listAllDonateProject.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				String donate_project_name = req.getParameter("donate_project_name");
				if (donate_project_name == null || donate_project_name.trim().length() == 0) {
					errorMsgs.add("專案名稱: 請勿空白");
				}
				
				Integer project_type = new Integer(req.getParameter("project_type").trim());
				
				String founder_no = req.getParameter("founder_no").trim();
				if (founder_no == null || founder_no.trim().length() == 0) {
					errorMsgs.add("園區會員編號: 請勿空白");
				}	
				
				Integer goal = null;
				try {
					goal = new Integer(req.getParameter("goal").trim());
				} catch (NumberFormatException e) {
					goal = 0;
					errorMsgs.add("目標金額請填數字.");
				}
				
				String donate_content = req.getParameter("donate_content");
				if (donate_content == null || donate_content.length() == 0) {
					errorMsgs.add("專案內容: 請勿空白");
				}	
				
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
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
					req.setAttribute("dpVO", dpVO); // 含有輸入格式錯誤的dpVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/update_donateProject_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				dpVO = dpSvc.updateDonateProject(donate_project_no, founder_no, project_type, donate_project_name, donate_content, goal,
						start_date, end_date, donate_result);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("dpVO", dpVO); // 資料庫update成功後,正確的的dpVO物件,存入req
				String url = "/donateProject/listOneDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/update_donateProject_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

//			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String donate_project_name = req.getParameter("donate_project_name");
				if (donate_project_name == null || donate_project_name.trim().length() == 0) {
					errorMsgs.add("專案名稱: 請勿空白");
				}
				
				Integer project_type = new Integer(req.getParameter("project_type").trim());
				
				String founder_no = req.getParameter("founder_no").trim();
				if (founder_no == null || founder_no.trim().length() == 0) {
					errorMsgs.add("園區會員編號: 請勿空白");
				}	
				
				Integer goal = null;
				try {
					goal = new Integer(req.getParameter("goal").trim());
				} catch (NumberFormatException e) {
					goal = 0;
					errorMsgs.add("目標金額請填數字.");
				}
				
				String donate_content = req.getParameter("donate_content");
				if (donate_content == null || donate_content.length() == 0) {
					errorMsgs.add("專案內容: 請勿空白");
				}	
				
				java.sql.Date start_date = null;
				try {
					start_date = java.sql.Date.valueOf(req.getParameter("start_date").trim());
				} catch (IllegalArgumentException e) {
					start_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入開始日期!");
				}
				
				java.sql.Date end_date = null;
				try {
					end_date = java.sql.Date.valueOf(req.getParameter("end_date").trim());
				} catch (IllegalArgumentException e) {
					end_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入結束日期!");
				}
				
				//處理專案圖片
				/*
				Collection<Part> parts = req.getParts();
				List<byte[]> dPics = new ArrayList<byte[]>();  //把圖片們排排站存進去
				for (Part dPic : parts) {
					InputStream inPic = dPic.getInputStream();  //寫圖片進資料庫
					byte[] emp_pic = null;
					if (inPic.available()!=0) {
						emp_pic = new byte[inPic.available()];  //以byte[]形式存入list
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
				List<byte[]> dPics = new ArrayList<byte[]>();  //把圖片們排排站存進去
				for (Part dPic : parts) {
					InputStream inPic = dPic.getInputStream();  //寫圖片進資料庫
					byte[] emp_pic = null;
					if (inPic.available()!=0) {
						emp_pic = new byte[inPic.available()];  //以byte[]形式存入list
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
				
				//專案圖片利用donate_picVO
				List<DonatePicVO> dPicVOList = new ArrayList<DonatePicVO>();
				for (byte[] dPicture :dPics) {  //把圖片利用VO //放進去之後再存到型態為VO的list
					DonatePicVO dPicVO = new DonatePicVO();  //圖片組合們-等待接收專案自增主鍵
					dPicVO.setPicture(dPicture);
					dPicVOList.add(dPicVO);
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("dpVO", dpVO); // 含有輸入格式錯誤的dpVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/donateProject/addDonateProject.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
//				DonateProjectService dpSvc = new DonateProjectService();  //這個無法同時操作兩個表格
//				dpVO = dpSvc.addDonateProject(founder_no, project_type, donate_project_name, donate_content, goal,
//						start_date, end_date);
				
				//新增專案 也同時新增圖片表格的一列列資料 拿到專案的自增主鍵
				DonateProjectService dpSvc = new DonateProjectService();
				dpSvc.insertWithDPics(dpVO, dPicVOList);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/donateProject/listAllDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/donateProject/addDonateProject.jsp");
//				failureView.forward(req, res);
//			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String donate_project_no = req.getParameter("donate_project_no");
				
				/***************************2.開始刪除資料***************************************/
				DonateProjectService dpSvc = new DonateProjectService();
				dpSvc.deleteDonateProject(donate_project_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/donateProject/listAllDonateProject.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/donateProject/listAllDonateProject.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
