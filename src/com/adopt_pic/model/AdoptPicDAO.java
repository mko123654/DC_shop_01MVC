package com.adopt_pic.model;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class AdoptPicDAO implements AdoptPicDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//	新增圖片SQL
	private static final String INSERT_STMT = "INSERT INTO ADOPT_PIC (Adopt_Project_No, Picture) VALUES (?, ?)";
//	刪除圖片SQL
	private static final String DELETE = "DELETE FROM ADOPT_PIC where Adopt_Pic_No = ?";
//	查流水編號
	private static final String GET_ONE_STMT = "SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC WHERE Adopt_Pic_No = ?";
//	查個別認養案圖片SQL
	private static final String GET_ByProjectNo_STMT = "SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC WHERE Adopt_Project_No = ?";
//	查全部
	private static final String GET_ALL_STMT = "SELECT Adopt_Pic_No, Adopt_Project_No, Picture FROM ADOPT_PIC";
	
	@Override
	public void insert(AdoptPicVO appVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();		
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, appVO.getAdopt_project_no());
			pstmt.setBytes(2, appVO.getPicture());

			
			pstmt.executeUpdate();
			 
			System.out.println("新增成功");
			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adopt_pic_no);

			pstmt.executeUpdate();
			
			System.out.println("刪除成功");
			
			// Handle any driver errors
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
			
			con = ds.getConnection();
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

}
