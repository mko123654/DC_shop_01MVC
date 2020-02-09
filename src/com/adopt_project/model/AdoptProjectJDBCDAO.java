package com.adopt_project.model;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

public class AdoptProjectJDBCDAO implements AdoptProjectDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "654321";	
	
//	新增
	private static final String INSERT_STMT = 
		"INSERT INTO ADOPT_PROJECT (Founder_No, Adopter_No, Adopt_Project_Name, Pet_Category, Adopt_Content, Adopt_Status, Adopt_Result, Sex, Age, Breed, Chip, Birth_Control, Founder_Location)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//	修改
	private static final String UPDATE = 
			"UPDATE ADOPT_PROJECT set Founder_No=?, Adopter_No=?, Adopt_Project_Name=?, Pet_Category=?, Adopt_Content=?, Adopt_Status=?, Adopt_Result=?, Sex=?, Age=?, Breed=?, Chip=?, Birth_Control=?, Founder_Location=?"
					+ "where Adopt_Project_No=?";
//	刪除
	private static final String DELETE = 
			"DELETE FROM ADOPT_PROJECT where Adopt_Project_No = ?";
//	認養專案編號查個別專案
	private static final String GET_ONE_STMT = 
			"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT where Adopt_Project_No = ?";
//	查園區開的認養案
	private static final String GET_ByFounderNo_STMT = 
		"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT where Founder_No = ?";
//	查會員領養到的認養案
	private static final String GET_ByAdopterNo_STMT = 
			"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT where Adopter_No = ?";
//	查貓/狗認養案
	private static final String GET_ByPetCategory_STMT = 
			"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT where Pet_Category = ?";
//	用園區地區查認養案
	private static final String GET_ByFounderLocation_STMT = 
			"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT where Founder_Location = ?";
//	查全部
	private static final String GET_ALL_STMT = 
			"SELECT Adopt_Project_No,Founder_No,Adopter_No,Adopt_Project_Name,Pet_Category,Adopt_Content,Adopt_Status,Adopt_Result,Sex,Age,Breed,Chip,Birth_Control,Founder_Location FROM ADOPT_PROJECT order by Adopt_Project_No";

	@Override
	public void insert(AdoptProjectVO apVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);			
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, apVO.getFounder_no());
			pstmt.setString(2, apVO.getAdopter_no());
			pstmt.setString(3, apVO.getAdopt_project_name());
			pstmt.setInt   (4, apVO.getPet_category());
			pstmt.setString(5, apVO.getAdopt_content());
			pstmt.setInt   (6, apVO.getAdopt_status());
			pstmt.setString(7, apVO.getAdopt_result());
			pstmt.setInt   (8, apVO.getSex());
			pstmt.setString(9, apVO.getAge());
			pstmt.setString(10, apVO.getBreed());
			pstmt.setInt   (11, apVO.getChip());
			pstmt.setInt   (12, apVO.getBirth_control());
			pstmt.setString(13, apVO.getFounder_location());
			
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
	public void update(AdoptProjectVO apVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, apVO.getFounder_no());
			pstmt.setString(2, apVO.getAdopter_no());
			pstmt.setString(3, apVO.getAdopt_project_name());
			pstmt.setInt   (4, apVO.getPet_category());
			pstmt.setString(5, apVO.getAdopt_content());
			pstmt.setInt   (6, apVO.getAdopt_status());
			pstmt.setString(7, apVO.getAdopt_result());
			pstmt.setInt   (8, apVO.getSex());
			pstmt.setString(9, apVO.getAge());
			pstmt.setString(10, apVO.getBreed());
			pstmt.setInt   (11, apVO.getChip());
			pstmt.setInt   (12, apVO.getBirth_control());
			pstmt.setString(13, apVO.getFounder_location());
			pstmt.setString(14, apVO.getAdopt_project_no());

			pstmt.executeUpdate();

			System.out.println("修改完成");
	
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
	public void delete(String adopt_project_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adopt_project_no);

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
	public AdoptProjectVO findByPrimaryKey(String adopt_project_no) {

		AdoptProjectVO apVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, adopt_project_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
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
		return apVO;
	}

	@Override
	public List<AdoptProjectVO> findByFounderNo(String founder_no) {
		List<AdoptProjectVO> list = new ArrayList<AdoptProjectVO>();
		AdoptProjectVO apVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ByFounderNo_STMT);
			pstmt.setString(1, founder_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
				list.add(apVO); // Store the row in the list
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
		return list;
	}
	
	@Override
	public List<AdoptProjectVO> findByAdopterNo(String adopter_no) {
		List<AdoptProjectVO> list = new ArrayList<AdoptProjectVO>();
		AdoptProjectVO apVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ByAdopterNo_STMT);
			pstmt.setString(1, adopter_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
				list.add(apVO); // Store the row in the list
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
		return list;
	}
	
	@Override
	public List<AdoptProjectVO> findByPetCategory(Integer pet_category) {
		List<AdoptProjectVO> list = new ArrayList<AdoptProjectVO>();
		AdoptProjectVO apVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ByPetCategory_STMT);
			pstmt.setInt(1, pet_category);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
				list.add(apVO); // Store the row in the list
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
		return list;
	}
	@Override
	public List<AdoptProjectVO> findByFounderLocation(String founder_location) {
		List<AdoptProjectVO> list = new ArrayList<AdoptProjectVO>();
		AdoptProjectVO apVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ByFounderLocation_STMT);
			pstmt.setString(1, founder_location);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
				list.add(apVO); // Store the row in the list
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
		return list;
	}
	
	@Override
	public List<AdoptProjectVO> getAll() {
		List<AdoptProjectVO> list = new ArrayList<AdoptProjectVO>();
		AdoptProjectVO apVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				apVO = new AdoptProjectVO();
				apVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				apVO.setFounder_no(rs.getString("Founder_No"));
				apVO.setAdopter_no(rs.getString("Adopter_No"));
				apVO.setAdopt_project_name(rs.getString("Adopt_Project_Name"));
				apVO.setPet_category(rs.getInt("Pet_Category"));
				apVO.setAdopt_content(rs.getString("Adopt_Content"));
				apVO.setAdopt_status(rs.getInt("Adopt_Status"));
				apVO.setAdopt_result(rs.getString("Adopt_Result"));
				apVO.setSex(rs.getInt("Sex"));
				apVO.setAge(rs.getString("Age"));
				apVO.setBreed(rs.getString("Breed"));
				apVO.setChip(rs.getInt("Chip"));
				apVO.setBirth_control(rs.getInt("Birth_Control"));
				apVO.setFounder_location(rs.getString("Founder_Location"));
				list.add(apVO); // Store the row in the list
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
		return list;
	}
	
	public static void main(String[] args) {

		AdoptProjectJDBCDAO dao = new AdoptProjectJDBCDAO();
		
		String content01  = null,result01 = null;		  
		try {
			content01 = getLongString("items/adopt_project_content/content01.txt");
			result01 = getLongString("items/adopt_project_result//result01.txt");
		} catch (IOException ie) {
			System.out.println(ie);	
		}

		// 新增
//		AdoptProjectVO apVO1 = new AdoptProjectVO();
//		apVO1.setFounder_no("M0001");
//		apVO1.setAdopter_no("M0006");
//		apVO1.setAdopt_project_name("見人就翻肚幼母犬");
//		apVO1.setPet_category(1);
//		apVO1.setAdopt_content(content01); 
//		apVO1.setAdopt_status(0);
//		apVO1.setAdopt_result(result01);
//		apVO1.setSex(0);
//		apVO1.setAge("12w5d");
//		apVO1.setBreed("混種犬");
//		apVO1.setChip(1);
//		apVO1.setBirth_control(1);
//		apVO1.setFounder_location("台東市");
//		
//		dao.insert(apVO1);	

//		// 修改
//		AdoptProjectVO apVO2 = new AdoptProjectVO();
//		apVO2.setFounder_no("M0002");
//		apVO2.setAdopter_no("M0004");
//		apVO2.setAdopt_project_name("可愛小浪浪回家");
//		apVO2.setPet_category(0);
//		apVO2.setAdopt_content(null); 
//		apVO2.setAdopt_status(1);
//		apVO2.setAdopt_result(null);
//		apVO2.setSex(1);
//		apVO2.setAge("5y3m");
//		apVO2.setBreed("貴賓狗");
//		apVO2.setChip(0);
//		apVO2.setBirth_control(1);
//		apVO2.setFounder_location("新北市");
//		apVO2.setAdopt_project_no("AP0009");
//		
//		dao.update(apVO2);

//		// 刪除
//		dao.delete("AP0002");

//		// 認養專案編號查詢
//		AdoptProjectVO apVO3 = dao.findByPrimaryKey("AP0001");
//		System.out.print(apVO3.getAdopt_project_no() + ",");
//		System.out.print(apVO3.getFounder_no() + ",");
//		System.out.print(apVO3.getAdopter_no() + ",");
//		System.out.print(apVO3.getAdopt_project_name() + ",");
//		System.out.print(apVO3.getPet_category() + ",");
//		System.out.print(apVO3.getAdopt_content() + ",");
//		System.out.print(apVO3.getAdopt_status() + ",");
//		System.out.print(apVO3.getAdopt_result() + ",");
//		System.out.print(apVO3.getSex() + ",");
//		System.out.print(apVO3.getAge() + ",");
//		System.out.print(apVO3.getBreed() + ",");
//		System.out.print(apVO3.getChip() + ",");
//		System.out.print(apVO3.getBirth_control() + ",");
//		System.out.print(apVO3.getFounder_location());
//		System.out.println("--------------------------------------------------------------");

//		// 查全部
//		List<AdoptProjectVO> list = dao.getAll();
//		for (AdoptProjectVO aEmp : list) {
//			System.out.print(aEmp.getAdopt_project_no() + ",");
//			System.out.print(aEmp.getFounder_no() + ",");
//			System.out.print(aEmp.getAdopter_no() + ",");
//			System.out.print(aEmp.getAdopt_project_name() + ",");
//			System.out.print(aEmp.getPet_category() + ",");
//			System.out.print(aEmp.getAdopt_content() + ",");
//			System.out.print(aEmp.getAdopt_status() + ",");
//			System.out.print(aEmp.getAdopt_result() + ",");
//			System.out.print(aEmp.getSex() + ",");
//			System.out.print(aEmp.getAge() + ",");
//			System.out.print(aEmp.getBreed() + ",");
//			System.out.print(aEmp.getChip() + ",");
//			System.out.print(aEmp.getBirth_control() + ",");
//			System.out.print(aEmp.getFounder_location());
//			System.out.println();
//		}
		
//		//	查園區開的認養案
//		List<AdoptProjectVO> list = dao.findByFounderNo("M0001");
//		for (AdoptProjectVO aEmp : list) {
//			System.out.print(aEmp.getAdopt_project_no() + ",");
//			System.out.print(aEmp.getFounder_no() + ",");
//			System.out.print(aEmp.getAdopter_no() + ",");
//			System.out.print(aEmp.getAdopt_project_name() + ",");
//			System.out.print(aEmp.getPet_category() + ",");
//			System.out.print(aEmp.getAdopt_content() + ",");
//			System.out.print(aEmp.getAdopt_status() + ",");
//			System.out.print(aEmp.getAdopt_result() + ",");
//			System.out.print(aEmp.getSex() + ",");
//			System.out.print(aEmp.getAge() + ",");
//			System.out.print(aEmp.getBreed() + ",");
//			System.out.print(aEmp.getChip() + ",");
//			System.out.print(aEmp.getBirth_control() + ",");
//			System.out.print(aEmp.getFounder_location());
//			System.out.println();
//		}
		
//		//	查會員領養到的認養案
//		List<AdoptProjectVO> list = dao.findByAdopterNo("M0006");
//		for (AdoptProjectVO aEmp : list) {
//			System.out.print(aEmp.getAdopt_project_no() + ",");
//			System.out.print(aEmp.getFounder_no() + ",");
//			System.out.print(aEmp.getAdopter_no() + ",");
//			System.out.print(aEmp.getAdopt_project_name() + ",");
//			System.out.print(aEmp.getPet_category() + ",");
//			System.out.print(aEmp.getAdopt_content() + ",");
//			System.out.print(aEmp.getAdopt_status() + ",");
//			System.out.print(aEmp.getAdopt_result() + ",");
//			System.out.print(aEmp.getSex() + ",");
//			System.out.print(aEmp.getAge() + ",");
//			System.out.print(aEmp.getBreed() + ",");
//			System.out.print(aEmp.getChip() + ",");
//			System.out.print(aEmp.getBirth_control() + ",");
//			System.out.print(aEmp.getFounder_location());
//			System.out.println();
//		}
		
		//	查貓/狗認養案
//		List<AdoptProjectVO> list = dao.findByPetCategory(1);
//		for (AdoptProjectVO aEmp : list) {
//			System.out.print(aEmp.getAdopt_project_no() + ",");
//			System.out.print(aEmp.getFounder_no() + ",");
//			System.out.print(aEmp.getAdopter_no() + ",");
//			System.out.print(aEmp.getAdopt_project_name() + ",");
//			System.out.print(aEmp.getPet_category() + ",");
//			System.out.print(aEmp.getAdopt_content() + ",");
//			System.out.print(aEmp.getAdopt_status() + ",");
//			System.out.print(aEmp.getAdopt_result() + ",");
//			System.out.print(aEmp.getSex() + ",");
//			System.out.print(aEmp.getAge() + ",");
//			System.out.print(aEmp.getBreed() + ",");
//			System.out.print(aEmp.getChip() + ",");
//			System.out.print(aEmp.getBirth_control() + ",");
//			System.out.print(aEmp.getFounder_location());
//			System.out.println();
//		}
		
//		//	用園區地區查認養案
//		List<AdoptProjectVO> list = dao.findByFounderLocation("台北市");
//		for (AdoptProjectVO aEmp : list) {
//			System.out.print(aEmp.getAdopt_project_no() + ",");
//			System.out.print(aEmp.getFounder_no() + ",");
//			System.out.print(aEmp.getAdopter_no() + ",");
//			System.out.print(aEmp.getAdopt_project_name() + ",");
//			System.out.print(aEmp.getPet_category() + ",");
//			System.out.print(aEmp.getAdopt_content() + ",");
//			System.out.print(aEmp.getAdopt_status() + ",");
//			System.out.print(aEmp.getAdopt_result() + ",");
//			System.out.print(aEmp.getSex() + ",");
//			System.out.print(aEmp.getAge() + ",");
//			System.out.print(aEmp.getBreed() + ",");
//			System.out.print(aEmp.getChip() + ",");
//			System.out.print(aEmp.getBirth_control() + ",");
//			System.out.print(aEmp.getFounder_location());
//			System.out.println();
//		}
		
		
	}
	
	// 使用String
	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}

	// 使用資料流
	public static Reader getLongStringStream(String path) throws IOException {
		return new FileReader(path);

	}
}