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
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
//	�s�W�Ϥ�SQL
	private static final String INSERT_STMT = "INSERT INTO DONATE_PIC (Donate_Project_No, Picture) VALUES (?, ?)";
//	�R���Ϥ�SQL
	private static final String DELETE_PIC = "DELETE FROM DONATE_PIC WHERE Donate_Pic_No = ?";	
//	�d�y����
	private static final String GET_ONE_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Pic_No = ?";
//	�d�ӧO�Ҹ�׹Ϥ�SQL
	private static final String GET_ByProjectNo_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Project_No = ?";
//	�d����
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
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-Donate_Pic");
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
			
			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_PIC);
			pstmt.setString(1, donate_pic_no);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�w�R���Ϥ��A�s��: " + donate_pic_no);

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
				// empVo �]�٬� Domain objects
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
				// deptVO �]�٬� Domain objects
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
				// deptVO �]�٬� Domain objects
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
