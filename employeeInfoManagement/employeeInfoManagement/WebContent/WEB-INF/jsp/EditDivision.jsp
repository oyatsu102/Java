<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署の追加・編集</title>
</head>
<body>

<% String old_divisionName = (String)request.getParameter("old_divisionName"); %>
<% if (old_divisionName != null) {%><%--編集対象のdivisionNameを取得--%>
	<h2>部署データを編集します</h2>
	<p><%=old_divisionName%>を編集します</p>
<%} else{%>
	<h2>部署データを新規作成します</h2>
<%} %>
<form action="./Main?action=action.EditDivision&old_divisionName=<%=old_divisionName%>" method="post">
	<p>名前：<input type="text" name="new_divisionName"></p>
	<input type="submit" value="設定" onclick="location.href='./Main'">
 	<input type="button" value="キャンセル" onclick="location.href='./Main?action=action.AllDivision'">

</form>
</body>
</html>