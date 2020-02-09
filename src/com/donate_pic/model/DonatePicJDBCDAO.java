package com.donate_pic.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DonatePicJDBCDAO implements DonatePicDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "654321";
	
//	新增圖片SQL
	private static final String INSERT_STMT = "INSERT INTO DONATE_PIC (Donate_Project_No, Picture) VALUES (?, ?)";
//	刪除圖片SQL
	private static final String DELETE_PIC = "DELETE FROM DONATE_PIC WHERE Donate_Pic_No = ?";	
//	查個別募資案圖片SQL
	private static final String GET_ByProjectNo_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Project_No = ?";
//	查流水號
	private static final String GET_ONE_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC WHERE Donate_Pic_No = ?";
//	查全部
	private static final String GET_ALL_STMT = "SELECT Donate_Pic_No, Donate_Project_No, Picture FROM DONATE_PIC";
	
	@Override
	public void insert(DonatePicVO dppVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dppVO.getDonate_project_no());
			pstmt.setBytes(2, dppVO.getPicture());

			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_PIC);
			pstmt.setString(1, donate_pic_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("已刪除圖片，編號: " + donate_pic_no);

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		
	public static void main(String[] args) {

		DonatePicJDBCDAO DPicdao = new DonatePicJDBCDAO();
		byte[] dp01_1 = null;
		byte[] dp01_2 = null;
		byte[] dp02_1  = null;
		byte[] dp02_2  = null;
		byte[] dp03_1  = null;
		byte[] dp03_2  = null;
		byte[] dp03_3  = null;
		byte[] dp04    = null;
		byte[] dp05_1  = null;
		byte[] dp05_2  = null;
		byte[] dp06_1  = null;
		byte[] dp06_2  = null;
		byte[] dp06_3  = null;
		byte[] dp07  = null;
		byte[] dp08  = null;
		byte[] dp09  = null;
		byte[] dp10 = null;
		byte[] dp11  = null;
		byte[] dp12  = null;
		byte[] dp13 = null;
		byte[] dp14 = null;
		byte[] dp15 = null;
		
		try {
			dp01_1  = getPictureByteArray("items/donate_pic/dp01_1.jpg");
			dp01_2  = getPictureByteArray("items/donate_pic/dp01_2.jpg");
			dp02_1  = getPictureByteArray("items/donate_pic/dp02_1.jpg");
			dp02_2  = getPictureByteArray("items/donate_pic/dp02_2.jpg");
			dp03_1  = getPictureByteArray("items/donate_pic/dp03_1.jpg");
			dp03_2  = getPictureByteArray("items/donate_pic/dp03_2.jpg");
			dp03_3  = getPictureByteArray("items/donate_pic/dp03_3.jpg");
			dp04    = getPictureByteArray("items/donate_pic/dp04.jpg");
			dp05_1  = getPictureByteArray("items/donate_pic/dp05_1.jpg");
			dp05_2  = getPictureByteArray("items/donate_pic/dp05_2.jpg");
			dp06_1  = getPictureByteArray("items/donate_pic/dp06_1.jpg");
			dp06_2  = getPictureByteArray("items/donate_pic/dp06_2.jpg");
			dp06_3  = getPictureByteArray("items/donate_pic/dp06_3.jpg");
			dp07  = getPictureByteArray("items/donate_pic/dp07.jpg");
			dp08  = getPictureByteArray("items/donate_pic/dp08.jpg");
			dp09  = getPictureByteArray("items/donate_pic/dp09.jpg");
			dp10  = getPictureByteArray("items/donate_pic/dp10.jpg");
			dp11  = getPictureByteArray("items/donate_pic/dp11.jpg");
			dp12  = getPictureByteArray("items/donate_pic/dp12.jpg");
			dp13  = getPictureByteArray("items/donate_pic/dp13.jpg");
			dp14  = getPictureByteArray("items/donate_pic/dp14.jpg");
			dp15  = getPictureByteArray("items/donate_pic/dp15.jpg");
			
		} catch (IOException ie) {
			System.out.println(ie);
		}

		// 新增 (OK! 2020/01/25) 
		DonatePicVO dpicVO1  = new DonatePicVO();
//		//1
//		dpicVO1.setDonate_project_no("DP0001");
//		dpicVO1.setPicture(dp01_1);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0001");
//		dpicVO1.setPicture(dp01_2);
//		DPicdao.insert(dpicVO1);
//		//2
//		dpicVO1.setDonate_project_no("DP0002");
//		dpicVO1.setPicture(dp02_1);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0002");
//		dpicVO1.setPicture(dp02_2);
//		DPicdao.insert(dpicVO1);
//		//3
//		dpicVO1.setDonate_project_no("DP0003");
//		dpicVO1.setPicture(dp03_1);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0003");
//		dpicVO1.setPicture(dp03_2);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0003");
//		dpicVO1.setPicture(dp03_3);
//		DPicdao.insert(dpicVO1);
//		//4
//		dpicVO1.setDonate_project_no("DP0004");
//		dpicVO1.setPicture(dp04);
//		DPicdao.insert(dpicVO1);
//		//5
//		dpicVO1.setDonate_project_no("DP0005");
//		dpicVO1.setPicture(dp05_1);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0005");
//		dpicVO1.setPicture(dp05_2);
//		DPicdao.insert(dpicVO1);
//		//6
//		dpicVO1.setDonate_project_no("DP0006");
//		dpicVO1.setPicture(dp06_1);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0006");
//		dpicVO1.setPicture(dp06_2);
//		DPicdao.insert(dpicVO1);
//		dpicVO1.setDonate_project_no("DP0006");
//		dpicVO1.setPicture(dp06_3);
//		DPicdao.insert(dpicVO1);
//		//7
//		dpicVO1.setDonate_project_no("DP0007");
//		dpicVO1.setPicture(dp07);
//		DPicdao.insert(dpicVO1);
//		//8
//		dpicVO1.setDonate_project_no("DP0008");
//		dpicVO1.setPicture(dp08);
//		DPicdao.insert(dpicVO1);
//		//9
//		dpicVO1.setDonate_project_no("DP0009");
//		dpicVO1.setPicture(dp09);
//		DPicdao.insert(dpicVO1);
//		//10
//		dpicVO1.setDonate_project_no("DP0010");
//		dpicVO1.setPicture(dp10);
//		DPicdao.insert(dpicVO1);
//		//11
//		dpicVO1.setDonate_project_no("DP0011");
//		dpicVO1.setPicture(dp11);
//		DPicdao.insert(dpicVO1);
//		//12
//		dpicVO1.setDonate_project_no("DP0012");
//		dpicVO1.setPicture(dp12);
//		DPicdao.insert(dpicVO1);
//		//13
//		dpicVO1.setDonate_project_no("DP0013");
//		dpicVO1.setPicture(dp13);
//		DPicdao.insert(dpicVO1);
//		//14
//		dpicVO1.setDonate_project_no("DP0014");
//		dpicVO1.setPicture(dp14);
//		DPicdao.insert(dpicVO1);
//		//15
//		dpicVO1.setDonate_project_no("DP0015");
//		dpicVO1.setPicture(dp15);
//		DPicdao.insert(dpicVO1);
		
		
		// 刪除 (OK! 2020/01/25) 
//		DPicdao.delete("DPP0011");
		
		// 查詢
//		DonatePicVO dppVO = DPicdao.findByPrimaryKey("DPP0001");
//		System.out.print  (dppVO.getDonate_pic_no() + ",");
//		System.out.print  (dppVO.getDonate_project_no() + ",");
//		System.out.println(dppVO.getPicture());
//
//		System.out.println("--------------------------------------------------------------");
//		
		
		// 查個別募資案 (OK! 2020/01/25) 
//		List<DonatePicVO> projectList = DPicdao.findByDonateProjectNo("DP0003");
//		for (DonatePicVO project : projectList) {
//			System.out.println("---------------------");
//			System.out.println(project.getDonate_pic_no());
//			System.out.println(project.getDonate_project_no());
//			System.out.print(project.getPicture());
//			System.out.println("---------------------");
//		}
		
		// 全部 (OK!) 
//		List<DonatePicVO> projectList = DPicdao.getAll();
//		for (DonatePicVO project : projectList) {
//			System.out.println("---------------------");
//			System.out.println(project.getDonate_pic_no());
//			System.out.println(project.getDonate_project_no());
//			System.out.print(project.getPicture());
//			System.out.println("---------------------");
//		}
			
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
