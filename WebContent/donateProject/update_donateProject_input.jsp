<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.donate_project.model.*"%>

<%
DonateProjectVO dpVO = (DonateProjectVO) request.getAttribute("dpVO"); //EmpServlet.java(Concroller), �s�Jreq��empVO����
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�Ҹ�M�׭ק� - update_donateProject_input.jsp</title>
<script type="text/JavaScript" src="<%=request.getContextPath()%>/ckeditor/ckeditor.js"></script> 
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
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>�Ҹ�M�׭ק� - update_donateProject_input.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/donateProject/dp.do" name="form1">
<table>
	<tr>
		<td>�Ҹ�M�׽s��<font color=red><b>*</b></font></td>
		<td>${dpVO.donate_project_no}</td>
	</tr>
	<tr>
		<td>�Ҹ�M�צW��</td>
		<td><input type="TEXT" name="donate_project_name" size="45" value="${dpVO.donate_project_name}" /></td>
	</tr>
	<tr>
		<td>�M������</td>
		<td><select size="1" name="project_type">
				<option value="0">�@��M��
				<option value="1">���M��
		</select></td>
	</tr>
	<tr>
		<td>��a�|���s��</td>
		<td><input type="TEXT" name="founder_no" size="45"	value="${dpVO.founder_no}" /></td>
	</tr>
	<tr>
		<td>�ؼЪ��B</td>
		<td><input type="TEXT" name="goal" size="45"	value="${dpVO.goal}" /></td>
	</tr>
	<tr>
		<td>�M�פ��e:</td>
		<td><textarea name="donate_content" id="donate_content" rows="10" cols="80">
			<%=dpVO.getDonate_content()%>
		</textarea>
		<script>
			CKEDITOR.replace('donate_content', { width : 700 });
		</script></td>
	</tr>
	
	<tr>
		<td>�}�l���</td>
		<td><input name="start_date" id="start_date" type="text" ></td>
	</tr>
	<tr>
		<td>�������</td>
		<td><input name="end_date" id="end_date" type="text" ></td>
	</tr>
	<tr>
		<td>���G</td>
		<td><textarea name="donate_result" id="donate_result" rows="10" cols="80">
			<%=(dpVO == null) ? "��J�A�Q�n�g�����e... " : dpVO.getDonate_result()%>
		</textarea> <script>
			CKEDITOR.replace('donate_result', {
				width : 700
			});
		</script></td>
		
	</tr>

	<jsp:useBean id="dpSvc" scope="page" class="com.donate_project.model.DonateProjectService" />
	

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="donate_project_no" value="${dpVO.donate_project_no}">
<input type="submit" value="�e�X�ק�"></FORM>
</body>



<!-- =========================================�H�U�� datetimepicker �������]�w========================================== -->

<% 
  java.sql.Date start_date = null;
  try {
	  start_date = dpVO.getStart_date();
   } catch (Exception e) {
	   start_date = new java.sql.Date(System.currentTimeMillis());
   }
  
  java.sql.Date end_date = null;
  try {
	  end_date = dpVO.getEnd_date();
   } catch (Exception e) {
	   end_date = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date').val()?$('#end_date').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#end_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date').val()?$('#start_date').val():false
	   })
	  },
	  timepicker:false
	 });
});
</script>
</html>