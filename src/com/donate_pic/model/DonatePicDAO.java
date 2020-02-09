package com.donate_pic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DonatePicDAO implements DonatePicDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO DONATE_PIC (Donate_Project_No, Picture) VALUES (?, ?)";
//	刪除圖片SQL
	private static final String DELETE_PIC = "DELETE FROM DONATE_PIC WHERE Donate_Pic_No = ?";	
//	查流水號
	private static final String GET_ONE_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Pic_No = ?";
//	查個別募資案圖片SQL
	private static final String GET_ByProjectNo_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Project_No = ?";
//	查全部
	private static final String GET_ALL_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC";
	
	@Override
	public void insert(DonatePicVO dppVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dppVO.getDonate_project_no());
			pstmt.setBytes(2, dppVO.getPicture());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void insert2 (DonatePicVO dppVO , Connection con) {

		PreparedStatement pstmt = null;

		try {

     		pstmt = con.prepareStatement(INSERT_STMT);

     		pstmt.setString(1, dppVO.getDonate_project_no());
			pstmt.setBytes(2, dppVO.getPicture());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Donate_Pic");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}

	}
		
	@Override
	public void delete(String donate_pic_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_PIC);
			pstmt.setString(1, donate_pic_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("已刪除圖片，編號: " + donate_pic_no);

		} catch (SQLException se) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public DonatePicVO findByPrimaryKey(String donate_pic_no) {

		DonatePicVO dppVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, donate_pic_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				dppVO = new DonatePicVO();
				dppVO.setDonate_pic_no(rs.getString("donate_pic_no"));
				dppVO.setDonate_project_no(rs.getString("donate_project_no"));
				dppVO.setPicture(rs.getBytes("picture")); 
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
		return dppVO;
	}
	
	@Override
	public List<DonatePicVO> findByDonateProjectNo(String donate_project_no) {
		List<DonatePicVO> project_list = new ArrayList<DonatePicVO>();
		DonatePicVO dppVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ByProjectNo_STMT);

			pstmt.setString(1, donate_project_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dppVO = new DonatePicVO();
				dppVO.setDonate_pic_no(rs.getString("donate_pic_no"));
				dppVO.setDonate_project_no(rs.getString("donate_project_no"));
				dppVO.setPicture(rs.getBytes("picture")); 
				project_list.add(dppVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<DonatePicVO> getAll() {
		List<DonatePicVO> project_list = new ArrayList<DonatePicVO>();
		DonatePicVO dppVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dppVO = new DonatePicVO();
				dppVO.setDonate_pic_no(rs.getString("donate_pic_no"));
				dppVO.setDonate_project_no(rs.getString("donate_project_no"));
				dppVO.setPicture(rs.getBytes("picture")); 
				project_list.add(dppVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
