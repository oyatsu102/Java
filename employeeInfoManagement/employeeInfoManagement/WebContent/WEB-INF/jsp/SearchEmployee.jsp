<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Division"%>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% List<Division> dList = (ArrayList<Division>)request.getAttribute("dList");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員情報検索</title>
</head>
<body>
  <h2>社員情報検索</h2>
  <p>条件を指定して社員情報を検索します</p>

  <form action="./Main?action=action.SearchEmployee" method="post">
   <p>所属部署：
   <select name="divisionName">
   　<option  value="未選択">未選択</option>
     <% for(Division div :dList){ %>
     <option value=<%=div.getDivisionName()%>><%=div.getDivisionName()%></option>
     <%} %>
   </select>
   </p>
   <p>社員ID：<input type="text" name="employeeID"></p>
   <p>名前に含まれる文字：<input type="text" name="searchWord"></p>

   <input type="submit" value="検索">
  </form>

</body>
</html>