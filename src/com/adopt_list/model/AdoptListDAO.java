package com.adopt_list.model;

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

public class AdoptListDAO implements AdoptListDAO_interface {
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
			"INSERT INTO ADOPT_LIST (Adopt_Project_No, Adopter_No, Real_Name, Phone, Age, ID_Card, Address, Email, Sex, Date_Of_Application, Status)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
	//	刪除
	private static final String DELETE = 
			"DELETE FROM ADOPT_LIST WHERE Adopt_List_No= ?";
	//	用認養單號查
	private static final String GET_ONE_STMT = 
			"SELECT Adopt_List_No, Adopt_Project_No, Adopter_No, Real_Name, Phone, Age, ID_Card, Address, Email, to_char(Date_Of_Application,'yyyy-mm-dd') Date_Of_Application, Status FROM ADOPT_LIST where Adopt_List_No = ?";
	//	用專案編號查
	private static final String GET_ByProjectNo_STMT = 
			"SELECT Adopt_List_No, Adopt_Project_No, Adopter_No, Real_Name, Phone, Age, ID_Card, Address, Email, to_char(Date_Of_Application,'yyyy-mm-dd') Date_Of_Application, Status FROM ADOPT_LIST where Adopt_Project_No = ?";
	//	用申請認養會員編號查
	private static final String GET_ByAdopterNo_STMT = 
			"SELECT Adopt_List_No, Adopt_Project_No, Adopter_No, Real_Name, Phone, Age, ID_Card, Address, Email, to_char(Date_Of_Application,'yyyy-mm-dd') Date_Of_Application, Status FROM ADOPT_LIST where Adopter_No = ?";
	//	查全部
	private static final String GET_ALL_STMT = 
			"SELECT Adopt_List_No, Adopt_Project_No, Adopter_No, Real_Name, Phone, Age, ID_Card, Address, Email, to_char(Date_Of_Application,'yyyy-mm-dd') Date_Of_Application, Status FROM ADOPT_LIST order by Adopt_List_No";

	@Override
	public void insert(AdoptListVO alVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, alVO.getAdopt_project_no());
			pstmt.setString(2, alVO.getAdopter_no());
			pstmt.setString(3, alVO.getReal_name());
			pstmt.setString(4, alVO.getPhone());
			pstmt.setInt   (5, alVO.getAge());
			pstmt.setString(6, alVO.getId_card());
			pstmt.setString(7, alVO.getAddress());
			pstmt.setString(8, alVO.getEmail());
			pstmt.setString(9, alVO.getSex());
			pstmt.setInt   (10, alVO.getStatus());

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
	public void delete(String adopt_list_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, adopt_list_no);
			pstmt.executeUpdate();

			System.out.println("刪除成功");

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
	public AdoptListVO findByPrimaryKey(String adopt_list_no) {

		AdoptListVO alVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, adopt_list_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// alVO 也稱為 Domain objects
				alVO = new AdoptListVO();
				alVO.setAdopt_list_no(rs.getString("Adopt_List_No"));
				alVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				alVO.setAdopter_no(rs.getString("Adopter_No"));
				alVO.setReal_name(rs.getString("Real_Name"));
				alVO.setPhone(rs.getString("Phone"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setId_card(rs.getString("ID_Card"));
				alVO.setAddress(rs.getString("Address"));
				alVO.setEmail(rs.getString("Email"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setDate_of_application(rs.getDate("Date_Of_Application"));
				alVO.setStatus(rs.getInt("Status"));
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
		return alVO;
	}

	@Override
	public List<AdoptListVO> findByProjectNo(String adopt_project_no) {
		List<AdoptListVO> list = new ArrayList<AdoptListVO>();
		AdoptListVO alVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = con.prepareStatement(GET_ByProjectNo_STMT);
			pstmt.setString(1, adopt_project_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				alVO = new AdoptListVO();
				alVO.setAdopt_list_no(rs.getString("Adopt_List_No"));
				alVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				alVO.setAdopter_no(rs.getString("Adopter_No"));
				alVO.setReal_name(rs.getString("Real_Name"));
				alVO.setPhone(rs.getString("Phone"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setId_card(rs.getString("ID_Card"));
				alVO.setAddress(rs.getString("Address"));
				alVO.setEmail(rs.getString("Email"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setDate_of_application(rs.getDate("Date_Of_Application"));
				alVO.setStatus(rs.getInt("Status"));
				list.add(alVO); // Store the row in the list
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
		return list;
	}

	@Override
	public List<AdoptListVO> findByAdopterNo(String adopter_no) {
		List<AdoptListVO> list = new ArrayList<AdoptListVO>();
		AdoptListVO alVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			pstmt = con.prepareStatement(GET_ByAdopterNo_STMT);
			pstmt.setString(1, adopter_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				alVO = new AdoptListVO();
				alVO.setAdopt_list_no(rs.getString("Adopt_List_No"));
				alVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				alVO.setAdopter_no(rs.getString("Adopter_No"));
				alVO.setReal_name(rs.getString("Real_Name"));
				alVO.setPhone(rs.getString("Phone"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setId_card(rs.getString("ID_Card"));
				alVO.setAddress(rs.getString("Address"));
				alVO.setEmail(rs.getString("Email"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setDate_of_application(rs.getDate("Date_Of_Application"));
				alVO.setStatus(rs.getInt("Status"));
				list.add(alVO); // Store the row in the list
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
		return list;
	}

	@Override
	public List<AdoptListVO> getAll() {
		List<AdoptListVO> list = new ArrayList<AdoptListVO>();
		AdoptListVO alVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				alVO = new AdoptListVO();
				alVO.setAdopt_list_no(rs.getString("Adopt_List_No"));
				alVO.setAdopt_project_no(rs.getString("Adopt_Project_No"));
				alVO.setAdopter_no(rs.getString("Adopter_No"));
				alVO.setReal_name(rs.getString("Real_Name"));
				alVO.setPhone(rs.getString("Phone"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setId_card(rs.getString("ID_Card"));
				alVO.setAddress(rs.getString("Address"));
				alVO.setEmail(rs.getString("Email"));
				alVO.setAge(rs.getInt("Age"));
				alVO.setDate_of_application(rs.getDate("Date_Of_Application"));
				alVO.setStatus(rs.getInt("Status"));
				list.add(alVO); // Store the row in the list
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
		return list;
	}
}
