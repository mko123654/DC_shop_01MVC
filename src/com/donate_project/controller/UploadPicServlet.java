package com.donate_project.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.sql.DataSource;


@WebServlet("/UploadPicServlet.do")
@MultipartConfig

public class UploadPicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");

		Collection<Part> parts = req.getParts(); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
		for (Part part : parts) {
			if (getFileNameFromPart(part)!=null){	
				
				InputStream fin = part.getInputStream();
				String filename = getFileNameFromPart(part);

				UploadPic upLoad = new UploadPic();
				upLoad.insertPic(filename, fin); //以InputStream直接送進資料庫BLOB欄位
				fin.close();
			}
		}
	}

	//取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) { 
		String header = part.getHeader("content-disposition"); //從前面第一個範例(版本1-基本測試)可得知此head的值
		String filename = header.substring(header.lastIndexOf("=") + 2, header.length() - 1);
		if (filename.length() == 0) { 
			return null; 
		} 
		return filename; 
	}
	
	public class UploadPic {

		public void insertPic(String fileName, InputStream fin) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				Context ctx = new javax.naming.InitialContext();
				DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
				con = ds.getConnection();

				pstmt =	con.prepareStatement(
						"insert into DONATE_PIC (Donate_Project_No, Picture)  values(?, ?)");
				pstmt.setString(1, "DP0001");
				pstmt.setBinaryStream(2, fin); 
				pstmt.executeUpdate();

				fin.close();
				pstmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	
}
