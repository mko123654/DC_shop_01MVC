package com.donate_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DonateListJDBCDAO implements DonateListDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "654321";
	
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			
			pstmt.setString(1, dlVO.getDonate_project_no());
			pstmt.setString(2, dlVO.getDonor_no());
			pstmt.setInt(3, dlVO.getAmount());
			
			pstmt.executeUpdate();

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
	public DonateListVO findByPrimaryKey(String donate_list_no) {
		DonateListVO dlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<DonateListVO> findByDonorNo(String donor_no) {
		List<DonateListVO> donor_list = new ArrayList<DonateListVO>();
		DonateListVO dlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return donor_list;
	}
	
	@Override
	public List<DonateListVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {

		DonateListJDBCDAO DLdao = new DonateListJDBCDAO();
		
		

		// 新增 (OK! 2020/01/25) 
//		DonateListVO dlVO1  = new DonateListVO();
//		//1
//		dlVO1.setDonate_project_no("DP0001");
//		dlVO1.setDonor_no("M0002");
//		dlVO1.setAmount(1550);
//		DLdao.insert(dlVO1);
//		//2
//		dlVO1.setDonate_project_no("DP0003");
//		dlVO1.setDonor_no("M0006");
//		dlVO1.setAmount(100);
//		DLdao.insert(dlVO1);
//		//3
//		dlVO1.setDonate_project_no("DP0008");
//		dlVO1.setDonor_no("M0001");
//		dlVO1.setAmount(1200);
//		DLdao.insert(dlVO1);
//		//4
//		dlVO1.setDonate_project_no("DP0001");
//		dlVO1.setDonor_no("M0001");
//		dlVO1.setAmount(3000);
//		DLdao.insert(dlVO1);
//		//5
//		dlVO1.setDonate_project_no("DP0007");
//		dlVO1.setDonor_no("M0001");
//		dlVO1.setAmount(900);
//		DLdao.insert(dlVO1);
//		//6
//		dlVO1.setDonate_project_no("DP0008");
//		dlVO1.setDonor_no("M0005");
//		dlVO1.setAmount(500);
//		DLdao.insert(dlVO1);
//		//7
//		dlVO1.setDonate_project_no("DP0004");
//		dlVO1.setDonor_no("M0005");
//		dlVO1.setAmount(800);
//		DLdao.insert(dlVO1);
//		//8
//		dlVO1.setDonate_project_no("DP0001");
//		dlVO1.setDonor_no("M0003");
//		dlVO1.setAmount(1500);
//		DLdao.insert(dlVO1);
//		//9
//		dlVO1.setDonate_project_no("DP0002");
//		dlVO1.setDonor_no("M0004");
//		dlVO1.setAmount(500);
//		DLdao.insert(dlVO1);
//		//10
//		dlVO1.setDonate_project_no("DP0002");
//		dlVO1.setDonor_no("M0005");
//		dlVO1.setAmount(3200);
//		DLdao.insert(dlVO1);
//		//11
//		dlVO1.setDonate_project_no("DP0006");
//		dlVO1.setDonor_no("M0006");
//		dlVO1.setAmount(5200);
//		DLdao.insert(dlVO1);
//		//12
//		dlVO1.setDonate_project_no("DP0002");
//		dlVO1.setDonor_no("M0006");
//		dlVO1.setAmount(500);
//		DLdao.insert(dlVO1);
//		//13
//		dlVO1.setDonate_project_no("DP0011");
//		dlVO1.setDonor_no("M0001");
//		dlVO1.setAmount(600);
//		DLdao.insert(dlVO1);
//		//14
//		dlVO1.setDonate_project_no("DP0011");
//		dlVO1.setDonor_no("M0005");
//		dlVO1.setAmount(8900);
//		DLdao.insert(dlVO1);
//		//15
//		dlVO1.setDonate_project_no("DP0013");
//		dlVO1.setDonor_no("M0005");
//		dlVO1.setAmount(100);
//		DLdao.insert(dlVO1);

	
		
		// 查詢明細編號 (OK! 2020/01/25) 
//		DonateListVO dlVO2 = DLdao.findByPrimaryKey("DPL0001");
//		System.out.println("---------------------");
//		System.out.println(dlVO2.getDonate_list_no());
//		System.out.println(dlVO2.getDonate_project_no());
//		System.out.println(dlVO2.getDonor_no());
//		System.out.println(dlVO2.getAmount());
//		System.out.println(dlVO2.getDonate_date());
//		System.out.println("---------------------");
		
		//查全部 (OK! 2020/01/25)
//		List<DonateListVO> alist = DLdao.getAll();
//			for (DonateListVO aProject : alist) {
//				System.out.println("---------------------");
//				System.out.println(aProject.getDonate_list_no());
//				System.out.println(aProject.getDonate_project_no());
//				System.out.println(aProject.getDonor_no());
//				System.out.println(aProject.getAmount());
//				System.out.println(aProject.getDonate_date());
//				System.out.println("---------------------");
//			}
		
		// 查個別募資案的明細 (OK! 2020/01/25) 
//		List<DonateListVO> projectList = DLdao.findByDonateProjectNo("DP0001");
//		for (DonateListVO project : projectList) {
//			System.out.println("---------------------");
//			System.out.println(project.getDonate_list_no());
//			System.out.println(project.getDonate_project_no());
//			System.out.println(project.getDonor_no());
//			System.out.println(project.getAmount());
//			System.out.println(project.getDonate_date());
//			System.out.println("---------------------");
//		}
			
		// 查個別會員的明細 (OK! 2020/01/25) 
		List<DonateListVO> donorList = DLdao.findByDonorNo("M0001");
		for (DonateListVO donor : donorList) {
			System.out.println("---------------------");
			System.out.println(donor.getDonate_list_no());
			System.out.println(donor.getDonate_project_no());
			System.out.println(donor.getDonor_no());
			System.out.println(donor.getAmount());
			System.out.println(donor.getDonate_date());
			System.out.println("---------------------");
		}
		
	}
	
}


