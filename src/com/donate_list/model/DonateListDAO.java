package com.donate_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DonateListDAO implements DonateListDAO_interface {
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

	//	新增SQL
	private static final String INSERT_STMT = "INSERT INTO DONATE_LIST(Donate_Project_No, Donor_No, Amount, Donate_Date) VALUES ( ?, ?, ?, sysdate)";
	//	查單一明細SQL
	private static final String GET_ONE_STMT = "SELECT Donate_List_No, Donate_Project_No, Donor_No, Amount, to_char(Donate_Date,'yyyy-mm-dd') Donate_Date FROM DONATE_LIST WHERE Donate_List_No = ?";
	//	查全部SQL
	private static final String GET_ALL_STMT = "SELECT Donate_List_No, Donate_Project_No, Donor_No, Amount, to_char(Donate_Date,'yyyy-mm-dd') Donate_Date FROM DONATE_LIST";
	//	查個別募資案的明細SQL
	private static final String GET_ByProjectNo_STMT = "SELECT Donate_List_No, Donate_Project_No, Donor_No, Amount, to_char(Donate_Date,'yyyy-mm-dd') Donate_Date FROM DONATE_LIST WHERE Donate_Project_No = ?";
	//	查個會員的明細SQL
	private static final String GET_ByDonorNo_STMT = "SELECT Donate_List_No, Donate_Project_No, Donor_No, Amount, to_char(Donate_Date,'yyyy-mm-dd') Donate_Date FROM DONATE_LIST WHERE Donor_No = ?";



	@Override
	public void insert(DonateListVO dlVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dlVO.getDonate_project_no());
			pstmt.setString(2, dlVO.getDonor_no());
			pstmt.setInt(3, dlVO.getAmount());

			pstmt.executeUpdate();

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
	public DonateListVO findByPrimaryKey(String donate_list_no) {
		DonateListVO dlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, donate_list_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dlVO = new DonateListVO();
				dlVO.setDonate_list_no(rs.getString("Donate_List_No"));
				dlVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dlVO.setDonor_no(rs.getString("Donor_No"));
				dlVO.setAmount(rs.getInt("Amount"));
				dlVO.setDonate_date(rs.getDate("Donate_Date"));
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
		return dlVO;
	}


	@Override
	public List<DonateListVO> getAll() {
		List<DonateListVO> allList = new ArrayList<DonateListVO>();
		DonateListVO dlVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dlVO = new DonateListVO();
				dlVO.setDonate_list_no(rs.getString("Donate_List_No"));
				dlVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dlVO.setDonor_no(rs.getString("Donor_No"));
				dlVO.setAmount(rs.getInt("Amount"));
				dlVO.setDonate_date(rs.getDate("Donate_Date"));
				allList.add(dlVO); // Store the row in the list
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
		return allList;
	}

	@Override
	public List<DonateListVO> findByDonateProjectNo(String donate_project_no) {
		List<DonateListVO> project_list = new ArrayList<DonateListVO>();
		DonateListVO dlVO = null;
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
				dlVO = new DonateListVO();
				dlVO.setDonate_list_no(rs.getString("Donate_List_No"));
				dlVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dlVO.setDonor_no(rs.getString("Donor_No"));
				dlVO.setAmount(rs.getInt("Amount"));
				dlVO.setDonate_date(rs.getDate("Donate_Date"));
				project_list.add(dlVO); // Store the row in the list
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
	public List<DonateListVO> findByDonorNo(String donor_no) {
		List<DonateListVO> donor_list = new ArrayList<DonateListVO>();
		DonateListVO dlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ByDonorNo_STMT);

			pstmt.setString(1, donor_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dlVO = new DonateListVO();
				dlVO.setDonate_list_no(rs.getString("Donate_List_No"));
				dlVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dlVO.setDonor_no(rs.getString("Donor_No"));
				dlVO.setAmount(rs.getInt("Amount"));
				dlVO.setDonate_date(rs.getDate("Donate_Date"));
				donor_list.add(dlVO); // Store the row in the list
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
		return donor_list;
	}

	@Override
	public List<DonateListVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}
}