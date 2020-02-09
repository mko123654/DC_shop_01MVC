package com.donate_project.model;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
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

import com.donate_pic.model.DonatePicDAO;
import com.donate_pic.model.DonatePicJDBCDAO;
import com.donate_pic.model.DonatePicVO;

public class DonateProjectDAO implements DonateProjectDAO_interface{
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
	private static final String INSERT_STMT = "INSERT INTO DONATE_PROJECT (Founder_No, Project_Type, Donate_Project_Name,"
			+ "Donate_Content, Goal, Start_Date, End_Date) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
//	修改SQL
	private static final String UPDATE = "UPDATE DONATE_PROJECT set Founder_No=?, Project_Type=?, Donate_Project_Name=?, Donate_Content=?,"
			+ "Goal=?, Start_Date=? , End_Date=?, Donate_Result=? where Donate_Project_No = ?";
	
//	查全部SQL
	private static final String GET_ALL_STMT = "SELECT Donate_Project_No, Founder_No, Project_Type, Donate_Project_Name, Donate_Content,Goal,"
			+ "Money, to_char(Start_Date,'yyyy-mm-dd') Start_Date, to_char(End_Date,'yyyy-mm-dd') End_Date, Donate_Result FROM DONATE_PROJECT";
	
//	募資專案編號查個別專案
	private static final String GET_ONE_STMT = "SELECT Donate_Project_No, Founder_No, Project_Type, Donate_Project_Name, Donate_Content,Goal," 
			+ "Money, to_char(Start_Date,'yyyy-mm-dd') Start_Date, to_char(End_Date,'yyyy-mm-dd') End_Date, Donate_Result FROM DONATE_PROJECT where Donate_Project_No = ?";
	
//	刪除專案
	private static final String DELETE = "DELETE FROM DONATE_PROJECT where Donate_Project_No = ?";
	
	
	@Override
	public void insert(DonateProjectVO dpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dpVO.getFounder_no());
			pstmt.setInt(2, dpVO.getProject_type());
			pstmt.setString(3, dpVO.getDonate_project_name());
			pstmt.setString(4, dpVO.getDonate_content());
			pstmt.setInt(5, dpVO.getGoal());
			pstmt.setDate(6, dpVO.getStart_date());
			pstmt.setDate(7, dpVO.getEnd_date());

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
	public void insertWithPics (DonateProjectVO dpVO , List<DonatePicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增專案
			String cols[] = {"DONATE_PROJECT_NO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			
			pstmt.setString(1, dpVO.getFounder_no());
			pstmt.setInt(2, dpVO.getProject_type());
			pstmt.setString(3, dpVO.getDonate_project_name());
			pstmt.setString(4, dpVO.getDonate_content());
			pstmt.setInt(5, dpVO.getGoal());
			pstmt.setDate(6, dpVO.getStart_date());
			pstmt.setDate(7, dpVO.getEnd_date());
			pstmt.setString(8, dpVO.getDonate_result());
			
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_donate_project_no = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_donate_project_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_donate_project_no +"(剛新增成功的專案編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增圖片
			DonatePicDAO dao = new DonatePicDAO();
			for (DonatePicVO aProject : list) {
				aProject.setDonate_project_no(next_donate_project_no) ;
				dao.insert2(aProject,con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("新增專案編號" + next_donate_project_no + "時,共有圖片" + list.size()
					+ "張同時被新增");
	
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-Donate_Project");
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
	public void update(DonateProjectVO dpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, dpVO.getFounder_no());
			pstmt.setInt(2, dpVO.getProject_type());
			pstmt.setString(3, dpVO.getDonate_project_name());
			pstmt.setString(4, dpVO.getDonate_content());		
			pstmt.setInt(5, dpVO.getGoal());
			pstmt.setDate(6, dpVO.getStart_date());
			pstmt.setDate(7, dpVO.getEnd_date());
			pstmt.setString(8, dpVO.getDonate_result());
			pstmt.setString(9, dpVO.getDonate_project_no());
			
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
	public void delete(String donate_project_no) {
		// 要先刪掉明細，才可以刪掉專案 
		// 但實務上不可以這麼做，只能在專案完全沒人捐款時移除
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, donate_project_no);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後

			System.out.println("刪除專案編號" + donate_project_no);
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
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
	public DonateProjectVO findByPrimaryKey(String donate_project_no) {
		DonateProjectVO dpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, donate_project_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dpVO = new DonateProjectVO();
				dpVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dpVO.setFounder_no(rs.getString("Founder_No"));
				dpVO.setProject_type(rs.getInt("Project_Type"));
				dpVO.setDonate_project_name(rs.getString("Donate_Project_Name"));
				dpVO.setDonate_content(rs.getString("Donate_Content"));
				dpVO.setGoal(rs.getInt("Goal"));
				dpVO.setMoney(rs.getInt("Money"));
				dpVO.setStart_date(rs.getDate("Start_Date"));
				dpVO.setEnd_date(rs.getDate("End_Date"));
				dpVO.setDonate_result(rs.getString("Donate_Result"));
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
		return dpVO;
	}
	
	@Override
	public List<DonateProjectVO> getAll() {
		List<DonateProjectVO> list = new ArrayList<DonateProjectVO>();
		DonateProjectVO dpVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				dpVO = new DonateProjectVO();
				dpVO.setDonate_project_no(rs.getString("Donate_Project_No"));
				dpVO.setFounder_no(rs.getString("Founder_No"));
				dpVO.setProject_type(rs.getInt("Project_Type"));
				dpVO.setDonate_project_name(rs.getString("Donate_Project_Name"));
				dpVO.setDonate_content(rs.getString("Donate_Content"));
				dpVO.setGoal(rs.getInt("Goal"));
				dpVO.setMoney(rs.getInt("Money"));
				dpVO.setStart_date(rs.getDate("Start_Date"));
				dpVO.setEnd_date(rs.getDate("End_Date"));
				dpVO.setDonate_result(rs.getString("Donate_Result"));
				list.add(dpVO); // Store the row in the list
			}
				
			// Handle any driver errors
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
		return list;
	}

	@Override
	public List<DonateProjectVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
