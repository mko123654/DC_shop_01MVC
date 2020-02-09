<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.donate_project.model.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
DonateProjectVO dpVO = (DonateProjectVO) request.getAttribute("dpVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>

<html>
<head>
<title>�Ҹ�M�� - listOneDonateProject.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>�Ҹ�M�� - listOneDonateProject.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>�Ҹ�M�׽s��</th>
		<th>�Ҹ�M�צW��</th>
		<th>�M������</th>
		<th>��a�|���s��</th>
		<th>�ؼЪ��B</th>
		<th>��o���B</th>
		<th>�M�פ��e</th>
		<th>�}�l���</th>
		<th>�������</th>
		<th>���G</th>
<!-- 		<th>�M�׹Ϥ�</th> -->
	</tr>
	<tr>
		<td>${dpVO.donate_project_no}</td>
		<td>${dpVO.donate_project_name}</td>
		<td>${dpVO.project_type}</td>
		<td>${dpVO.founder_no}</td>
		<td>${dpVO.goal}</td>
		<td>${dpVO.money}</td>
		<td>${dpVO.donate_content}</td> 
		<td>${dpVO.start_date}</td>
		<td>${dpVO.end_date}</td>
		<td>${dpVO.donate_result}</td>
	</tr>
</table>

</body>
</html>