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
   <tr><td><h3>貓狗大棧 募資專案管理頁面: Home</h3></td></tr>
</table>

<h3>募資專案查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li>查詢所有募資專案<a href='listAllDonateProject.jsp'>---點此</a> <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="dp.do" >
        <b>輸入募資專案編號 :</b>
        <input type="text" name="donate_project_no" placeholder="EX: DP0001">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="dpSvc" scope="page" class="com.donate_project.model.DonateProjectService" />
   
  <li>
     <FORM METHOD="post" ACTION="dp.do" >
       <b>選擇專案編號:</b>
       <select size="1" name="donate_project_no">
         <c:forEach var="dpVO" items="${dpSvc.all}" > 
          <option value="${dpVO.donate_project_no}">${dpVO.donate_project_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
</ul>


<h3>新增募資專案</h3>

<ul>
  <li>新增募資專案<a href='addDonateProject.jsp'>---點此</a></li>
</ul>

</body>
</html>