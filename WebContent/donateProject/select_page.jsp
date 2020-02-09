<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM DonateProject: Home</title>

<style>
  table#table-1 {
	width: 800px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h3 {
    display: block;
    margin-bottom: 2px;
    font-size :30px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>�ߪ��j�� �Ҹ�M�׺޲z����: Home</h3></td></tr>
</table>

<h3>�Ҹ�M�׬d��:</h3>
	
<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li>�d�ߩҦ��Ҹ�M��<a href='listAllDonateProject.jsp'>---�I��</a> <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="dp.do" >
        <b>��J�Ҹ�M�׽s�� :</b>
        <input type="text" name="donate_project_no" placeholder="EX: DP0001">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="�e�X">
    </FORM>
  </li>

  <jsp:useBean id="dpSvc" scope="page" class="com.donate_project.model.DonateProjectService" />
   
  <li>
     <FORM METHOD="post" ACTION="dp.do" >
       <b>��ܱM�׽s��:</b>
       <select size="1" name="donate_project_no">
         <c:forEach var="dpVO" items="${dpSvc.all}" > 
          <option value="${dpVO.donate_project_no}">${dpVO.donate_project_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="�e�X">
    </FORM>
  </li>
  
</ul>


<h3>�s�W�Ҹ�M��</h3>

<ul>
  <li>�s�W�Ҹ�M��<a href='addDonateProject.jsp'>---�I��</a></li>
</ul>

</body>
</html>