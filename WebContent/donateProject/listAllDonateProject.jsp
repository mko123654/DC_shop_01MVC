<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.donate_project.model.*"%>
<%@ page import="com.donate_pic.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    DonateProjectService dpSvc = new DonateProjectService();
    List<DonateProjectVO> list = dpSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<%
	DonatePicService dppSvc = new DonatePicService();
    List<DonatePicVO> picList = dppSvc.getAll();
    pageContext.setAttribute("picList",picList);
%>

<html>
<head>
<title>所有募資專案 - listAllDonateProject.jsp</title>

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
	width: 800px;
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
  img {
    width: 100%;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有募資專案 - listAllDonateProject.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>募資專案編號</th>
		<th>募資專案名稱</th>
		<th>專案類型</th>
		<th>園地會員編號</th>
		<th>目標金額</th>
		<th>獲得金額</th>
		<th>專案內容</th>
		<th>開始日期</th>
		<th>結束日期</th>
		<th>成果</th>
		<th>專案圖片</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="dpVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
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
<%-- <td>${dppSvc.findPicByDPno(dpVO.Donate_Project_No).Picture}</td> --%>
			<td>
			
			</td>
			
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/donateProject/dp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="donate_project_no"  value="${dpVO.donate_project_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/donateProject/dp.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="donate_project_no"  value="${dpVO.donate_project_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>