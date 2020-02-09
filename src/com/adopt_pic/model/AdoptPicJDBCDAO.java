package com.adopt_pic.model;

import java.util.*;


import java.io.IOException;
import java.sql.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdoptPicJDBCDAO implements AdoptPicDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "654321";	

	private static final String INSERT_STMT = 
			"INSERT INTO ADOPT_PIC (Adopt_Project_No, Picture) VALUES (?, ?)";
	private static final String DELETE = 
			"DELETE FROM ADOPT_PIC where Adopt_Pic_No = ?";
	private static final String GET_ByProjectNo_STMT = 
			"SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC WHERE Adopt_Project_No = ?";
	private static final String GET_ONE_STMT = 
			"SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC WHERE Adopt_Pic_No = ?";
	private static final String GET_ALL_STMT = 
			"SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC";


	@Override
	public void insert(AdoptPicVO appVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);			
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, appVO.getAdopt_project_no());
			pstmt.setBytes(2, appVO.getPicture());

			
			pstmt.executeUpdate();
			 
			System.out.println("新增成功");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());		
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	
	@Override
	public void delete(String adopt_pic_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adopt_pic_no);

			pstmt.executeUpdate();
			
			System.out.println("刪除成功");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	@Override
	public AdoptPicVO findByPrimaryKey(String adopt_pic_no) {

		AdoptPicVO appVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, adopt_pic_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				appVO = new AdoptPicVO();
				appVO.setAdopt_pic_no(rs.getString("Adopt_Pic_No"));
				appVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				appVO.setPicture(rs.getBytes("Picture"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return appVO;
	}

	@Override
	public List<AdoptPicVO> findByAdoptProjectNo(String adopt_project_no) {
		List<AdoptPicVO> project_list = new ArrayList<AdoptPicVO>();
		AdoptPicVO appVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ByProjectNo_STMT);

			pstmt.setString(1, adopt_project_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				appVO = new AdoptPicVO();
				appVO.setAdopt_pic_no(rs.getString("Adopt_Pic_No"));
				appVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				appVO.setPicture(rs.getBytes("Picture"));
				project_list.add(appVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return project_list;
	}
	
	@Override
	public List<AdoptPicVO> getAll() {
		List<AdoptPicVO> project_list = new ArrayList<AdoptPicVO>();
		AdoptPicVO appVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVo 也稱為 Domain objects
				appVO = new AdoptPicVO();
				appVO.setAdopt_pic_no(rs.getString("Adopt_Pic_No"));
				appVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				appVO.setPicture(rs.getBytes("Picture"));
				project_list.add(appVO);
			}
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return project_list;
	}


	
	public static void main(String[] args) {

		AdoptPicJDBCDAO dao = new AdoptPicJDBCDAO();
		byte[] project011 = null;
		
		try {
			project011 = getPictureByteArray("items/adopt_pic/project011.jpg");			
		} catch (IOException ie) {
			System.out.println(ie);
		}
		
		// 新增
//		AdoptPicVO adoptVO1 = new AdoptPicVO();
//		
//		adoptVO1.setAdopt_project_no("AP0001");
//		adoptVO1.setPicture(project011);
//		dao.insert(adoptVO1);	

//		// 刪除
//		dao.delete("APP0003");
		
		// 查詢
//		AdoptPicVO apicVO = dao.findByPrimaryKey("APP0001");
//		System.out.print  (apicVO.getAdopt_pic_no() + ",");
//		System.out.print  (apicVO.getAdopt_project_no() + ",");
//		System.out.println(apicVO.getPicture());
//
//		System.out.println("--------------------------------------------------------------");

		// 查詢
//		List<AdoptPicVO> projectList = dao.findByAdoptProjectNo("AP0003");
//		for (AdoptPicVO project : projectList) {
//			System.out.println("---------------------");
//			System.out.println(project.getAdopt_pic_no());
//			System.out.println(project.getAdopt_project_no());
//			System.out.print(project.getPicture());
//			System.out.println("---------------------");
//		}
//
		
		// 查全部
//		List<AdoptPicVO> projectList = dao.getAll();
//		for (AdoptPicVO project : projectList) {
//			System.out.println("---------------------");
//			System.out.println(project.getAdopt_pic_no());
//			System.out.println(project.getAdopt_project_no());
//			System.out.print(project.getPicture());
//			System.out.println("---------------------");
//		}
		
	}
	
	// 使用InputStream資料流方式
	public static InputStream getPictureStream(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}