package com.adopt_project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdoptProjectDAO implements AdoptProjectDAO_interface {
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

			con = ds.getConnection();	
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adopt_project_no);

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
	public AdoptProjectVO findByPrimaryKey(String adopt_project_no) {

		AdoptProjectVO apVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
}
