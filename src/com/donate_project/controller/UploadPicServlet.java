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

		Collection<Part> parts = req.getParts(); // Servlet3.0�s�W�FPart�����A���ڭ̤�K���i���ɮפW�ǳB�z
		for (Part part : parts) {
			if (getFileNameFromPart(part)!=null){	
				
				InputStream fin = part.getInputStream();
				String filename = getFileNameFromPart(part);

				UploadPic upLoad = new UploadPic();
				upLoad.insertPic(filename, fin); //�HInputStream�����e�i��ƮwBLOB���
				fin.close();
			}
		}
	}

	//���X�W�Ǫ��ɮצW�� (�]��API������method,�ҥH�����ۦ漶�g)
	public String getFileNameFromPart(Part part) { 
		String header = part.getHeader("content-disposition"); //�q�e���Ĥ@�ӽd��(����1-�򥻴���)�i�o����head����
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
