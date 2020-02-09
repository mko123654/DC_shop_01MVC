package com.donate_project.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.donate_pic.*;
import com.donate_pic.model.DonatePicJDBCDAO;
import com.donate_pic.model.DonatePicVO;



public class DonateProjectJDBCDAO implements DonateProjectDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "654321";

	//	�s�WSQL
	private static final String INSERT_STMT = "INSERT INTO DONATE_PROJECT (Founder_No, Project_Type, Donate_Project_Name,"
			+ "Donate_Content, Goal, Start_Date, End_Date, Donate_Result) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
	//	�ק�SQL
	private static final String UPDATE = "UPDATE DONATE_PROJECT set Founder_No=?, Project_Type=?, Donate_Project_Name=?, Donate_Content=?,"
			+ "Goal=?, Start_Date=?, End_Date=?, Donate_Result=? where Donate_Project_No = ?";

	//	�d����SQL
	private static final String GET_ALL_STMT = "SELECT Donate_Project_No, Founder_No, Project_Type, Donate_Project_Name, Donate_Content,Goal,"
			+ "Money, to_char(Start_Date,'yyyy-mm-dd') Start_Date, to_char(End_Date,'yyyy-mm-dd') End_Date, Donate_Result FROM DONATE_PROJECT";

	//	�Ҹ�M�׽s���d�ӧO�M��
	private static final String GET_ONE_STMT = "SELECT Donate_Project_No, Founder_No, Project_Type, Donate_Project_Name, Donate_Content,Goal," 
			+ "Money, to_char(Start_Date,'yyyy-mm-dd') Start_Date, to_char(End_Date,'yyyy-mm-dd') End_Date, Donate_Result FROM DONATE_PROJECT where Donate_Project_No = ?";

	//	�R���M��
	private static final String DELETE_DPproject = "DELETE FROM DONATE_PROJECT where Donate_Project_No = ?";


	@Override
	public void insert(DonateProjectVO dpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dpVO.getFounder_no());
			pstmt.setInt(2, dpVO.getProject_type());
			pstmt.setString(3, dpVO.getDonate_project_name());
			pstmt.setString(4, dpVO.getDonate_content());
			pstmt.setInt(5, dpVO.getGoal());
			pstmt.setDate(6, dpVO.getStart_date());
			pstmt.setDate(7, dpVO.getEnd_date());
			pstmt.setString(8, dpVO.getDonate_result());

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
	public void insertWithPics (DonateProjectVO dpVO , List<DonatePicVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			// ���s�W�M��
			String cols[] = {"donate_project_no"};
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
			//�����������ۼW�D���
			String next_donate_project_no = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_donate_project_no = rs.getString(1);
				System.out.println("�ۼW�D���= " + next_donate_project_no +"(��s�W���\���M�׽s��)");
			} else {
				System.out.println("�����o�ۼW�D���");
			}
			rs.close();
			// �A�P�ɷs�W�Ϥ�
			DonatePicJDBCDAO dao = new DonatePicJDBCDAO();
			for (DonatePicVO aProject : list) {
				aProject.setDonate_project_no(next_donate_project_no) ;
				dao.insert2(aProject,con);
			}

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�s�W�M�׽s��" + next_donate_project_no + "��,�@���Ϥ�" + list.size()
			+ "�i�P�ɳQ�s�W");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-Donate_Project");
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public void delete(String donate_project_no) {
		// �n���R�����ӡA�~�i�H�R���M�� 
		// ����ȤW���i�H�o�򰵡A�u��b�M�ק����S�H���ڮɲ���

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(DELETE_DPproject);
			pstmt.setString(1, donate_project_no);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���M�׽s��" + donate_project_no);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, donate_project_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO �]�٬� Domain objects
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO �]�٬� Domain objects
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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




	public static void main(String[] args) {

		DonateProjectJDBCDAO DPdao = new DonateProjectJDBCDAO();
		String str1  = null;
		String str2  = null;
		String str3  = null;
		String str4  = null;
		String str5  = null;
		String str6  = null;
		String str7  = null;
		String str8  = null;
		String str9  = null;
		String str10 = null;
		String str11 = null;
		String str12 = null;
		String str13 = null;
		String str14 = null;
		String str15 = null;

		String result1 = null;
		String result2 = null;

		try {
			str1  = getLongString("items/donate_project_content/donate_project01.txt");
			str2  = getLongString("items/donate_project_content/donate_project02.txt");
			str3  = getLongString("items/donate_project_content/donate_project02.txt");
			str4  = getLongString("items/donate_project_content/donate_project02.txt");
			str5  = getLongString("items/donate_project_content/donate_project02.txt");
			str6  = getLongString("items/donate_project_content/donate_project02.txt");
			str7  = getLongString("items/donate_project_content/donate_project02.txt");
			str8  = getLongString("items/donate_project_content/donate_project02.txt");
			str9  = getLongString("items/donate_project_content/donate_project02.txt");
			str10 = getLongString("items/donate_project_content/donate_project02.txt");
			str11 = getLongString("items/donate_project_content/donate_project02.txt");
			str12 = getLongString("items/donate_project_content/donate_project02.txt");
			str13 = getLongString("items/donate_project_content/donate_project02.txt");
			str14 = getLongString("items/donate_project_content/donate_project02.txt");
			str15 = getLongString("items/donate_project_content/donate_project02.txt");
			result1 = getLongString("items/donate_project_result/donate_result01.txt");
			result2 = getLongString("items/donate_project_result/donate_result02.txt");


		} catch (IOException ie) {
			System.out.println(ie);	
		}



		// �s�W (OK! 2020/01/29) 
		//	DonateProjectVO dpVO1  = new DonateProjectVO();
		//	//1
		//	Date start1 = java.sql.Date.valueOf("2020-03-20");
		//	Date end1 = java.sql.Date.valueOf("2020-04-20");
		//	dpVO1.setFounder_no("M0003");
		//	dpVO1.setProject_type(1);
		//	dpVO1.setDonate_project_name("�ڬO�@�����u��Ŧ�f�v���ߡU�߫}�����Ҹ�p�e");
		//	dpVO1.setDonate_content(str1);
		//	dpVO1.setGoal(60000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result(result1);
		//	DPdao.insert(dpVO1);
		//	//2
		//	Date start2 = java.sql.Date.valueOf("2020-03-30");
		//	Date end2 = java.sql.Date.valueOf("2020-05-30");
		//	dpVO1.setFounder_no("M0001");
		//	dpVO1.setProject_type(1);
		//	dpVO1.setDonate_project_name("�u���U���g���l���ߡv�����p��");
		//	dpVO1.setDonate_content(str2);
		//	dpVO1.setGoal(40000);
		//	dpVO1.setStart_date(start2);
		//	dpVO1.setEnd_date(end2);
		//	dpVO1.setDonate_result(result2);
		//	DPdao.insert(dpVO1);
		//	//3
		//	dpVO1.setFounder_no("M0002");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�N�ŷx�Ǩ�U�Ө��� �u���@�˪���͡v");
		//	dpVO1.setDonate_content(str3);
		//	dpVO1.setGoal(80000);
		//	dpVO1.setStart_date(start2);
		//	dpVO1.setEnd_date(end2);
		//	dpVO1.setDonate_result(result2);
		//	DPdao.insert(dpVO1);
		//	//4
		//	dpVO1.setFounder_no("M0002");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("����s�ػݭn�j�a���R��");
		//	dpVO1.setDonate_content(str4);
		//	dpVO1.setGoal(350000);
		//	dpVO1.setStart_date(start2);
		//	dpVO1.setEnd_date(end2);
		//	dpVO1.setDonate_result(result2);
		//	DPdao.insert(dpVO1);
		//	//5
		//	dpVO1.setFounder_no("M0005");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�H�V�e�x-���L�̤@�y�Ź�");
		//	dpVO1.setDonate_content(str5);
		//	dpVO1.setGoal(70000);
		//	dpVO1.setStart_date(start2);
		//	dpVO1.setEnd_date(end2);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//6
		//	dpVO1.setFounder_no("M0005");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�T�}�ߪ��Ʊ�");
		//	dpVO1.setDonate_content(str6);
		//	dpVO1.setGoal(120000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//7
		//	dpVO1.setFounder_no("M0001");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("���s�����͵����ҭp�e");
		//	dpVO1.setDonate_content(str7);
		//	dpVO1.setGoal(268800);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//8
		//	dpVO1.setFounder_no("M0001");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("���x�W�ʪ����A�y��");
		//	dpVO1.setDonate_content(str8);
		//	dpVO1.setGoal(106800);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//9
		//	dpVO1.setFounder_no("M0004");
		//	dpVO1.setProject_type(1);
		//	dpVO1.setDonate_project_name("�u���e�f�����ϴ��p�e");
		//	dpVO1.setDonate_content(str9);
		//	dpVO1.setGoal(88000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//10
		//	dpVO1.setFounder_no("M0004");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("���U���j������Ĥl");
		//	dpVO1.setDonate_content(str10);
		//	dpVO1.setGoal(150000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//11
		//	dpVO1.setFounder_no("M0004");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�Ὤ��p�Ļ������j�M��");
		//	dpVO1.setDonate_content(str11);
		//	dpVO1.setGoal(250000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//12
		//	dpVO1.setFounder_no("M0004");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("��s�ߤ��R�߷Ź��M��");
		//	dpVO1.setDonate_content(str12);
		//	dpVO1.setGoal(250000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//13
		//	dpVO1.setFounder_no("M0006");
		//	dpVO1.setProject_type(1);
		//	dpVO1.setDonate_project_name("�ѯf��������~��[�o�M��");
		//	dpVO1.setDonate_content(str13);
		//	dpVO1.setGoal(231000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//14
		//	dpVO1.setFounder_no("M0006");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�������j���U�p�e");
		//	dpVO1.setDonate_content(str14);
		//	dpVO1.setGoal(95000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);
		//	//15
		//	dpVO1.setFounder_no("M0003");
		//	dpVO1.setProject_type(0);
		//	dpVO1.setDonate_project_name("�h�оɪ����ݭn�������h��ͬ�");
		//	dpVO1.setDonate_content(str15);
		//	dpVO1.setGoal(95000);
		//	dpVO1.setStart_date(start1);
		//	dpVO1.setEnd_date(end1);
		//	dpVO1.setDonate_result("");
		//	DPdao.insert(dpVO1);

		// �M��+�Ϥ�1:N�s�W
		DonateProjectVO dpVO1  = new DonateProjectVO();
		dpVO1.setFounder_no("M0004");
		dpVO1.setProject_type(1);
		dpVO1.setDonate_project_name("�u��Ŧ�f�v����");
		dpVO1.setDonate_content(str1);
		dpVO1.setGoal(10000);
		dpVO1.setStart_date(java.sql.Date.valueOf("2020-03-20"));
		dpVO1.setEnd_date(java.sql.Date.valueOf("2020-04-20"));
		dpVO1.setDonate_result(result1);

		List<DonatePicVO> picList = new ArrayList<DonatePicVO>();
		byte[] dp01_1 = null;
		byte[] dp01_2 = null;
		
		try {
			dp01_1  = getPictureByteArray("items/donate_pic/dp01_1.jpg");
			dp01_2  = getPictureByteArray("items/donate_pic/dp01_2.jpg");
		} catch (IOException ie) {
			System.out.println(ie);
		}
		
		DonatePicVO pic1 = new DonatePicVO();
		pic1.setPicture(dp01_1);
		
		DonatePicVO pic2 = new DonatePicVO();
		pic2.setPicture(dp01_2);
		
		picList.add(pic1);
		picList.add(pic2);
		
		DPdao.insertWithPics(dpVO1, picList);


		// �ק�  (OK! 2020/01/23)  
		//	Date start3 = java.sql.Date.valueOf("2020-12-20");
		//	Date end3 = java.sql.Date.valueOf("2020-03-20");
		//	DonateProjectVO dpVO2  = new DonateProjectVO();
		//	dpVO2.setFounder_no("M0002");
		//	dpVO2.setProject_type(1);
		//	dpVO2.setDonate_project_name("JBDC���եαM��(�ק�)");
		//	dpVO2.setDonate_content(""); 
		//	dpVO2.setGoal(10000);
		//	dpVO2.setMoney(5000);
		//	dpVO2.setStart_date(start3);
		//	dpVO2.setEnd_date(end3);
		//	dpVO2.setDonate_result("");
		//	dpVO2.setDonate_Project_No("DP0018");
		//	DPdao.update(dpVO2);

		// �R�� (OK! 2020/01/24) 
		//	DPdao.delete("DP0019");


		// �d�߱M�׽s�� (OK! 2020/01/23) 
		//	DonateProjectVO dpVO4 = DPdao.findByPrimaryKey("DP0007");
		//	System.out.println("---------------------");
		//	System.out.println(dpVO4.getDonate_project_no());
		//	System.out.println(dpVO4.getFounder_no());
		//	System.out.println(dpVO4.getProject_type());
		//	System.out.println(dpVO4.getDonate_project_name());
		//	System.out.println(dpVO4.getDonate_content());
		//	System.out.println(dpVO4.getGoal());
		//	System.out.println(dpVO4.getMoney());
		//	System.out.println(dpVO4.getStart_date());
		//	System.out.println(dpVO4.getEnd_date());
		//	System.out.println(dpVO4.getDonate_result());
		//	System.out.println("---------------------");


		//�d����(OK! 2020/01/24)
			List<DonateProjectVO> alist = DPdao.getAll();
				for (DonateProjectVO aProject : alist) {
					System.out.println("---------------------");
					System.out.println(aProject.getDonate_project_no());
					System.out.println(aProject.getFounder_no());
					System.out.println(aProject.getProject_type());
					System.out.println(aProject.getDonate_project_name());
					System.out.println(aProject.getDonate_content());
					System.out.println(aProject.getGoal());
					System.out.println(aProject.getMoney());
					System.out.println(aProject.getStart_date());
					System.out.println(aProject.getEnd_date());
					System.out.println(aProject.getDonate_result());
					System.out.println("---------------------");
				}


	}

	// �ϥ�String(CLOB)
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

	// �ϥ�byte[]�覡(BLOB)
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