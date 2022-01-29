<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="model.Division"%>
<%@ page import="action.AllDivision"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% request.setCharacterEncoding("UTF-8");%>
<%
	List<Division> dList = (ArrayList<Division>) request.getAttribute("dList");
%>
<% String deleteResult = null; %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署一覧</title>
</head>

<body>
<% deleteResult = (String)request.getAttribute("deleteResult");%>
<% if (deleteResult != null && deleteResult=="error") {%>
	<p> 削除することができませんでした。</p>
<% }%>
	<h2>部署一覧：</h2>
	<table>
		<tr>
			<th>ID</th>
			<th>部署名</th>
		</tr>
		<% for (Division div : dList) { %>
		<tr>
			<th><%=div.getDivisionID()%></th>
			<th><%=div.getDivisionName()%></th>
			<%--編集ページへ遷移し、対象部署を表示→パラメータを付加して遷移 --%>
			<th><input type="button" value="編集" onclick="location.href='./Main?action=action.NavigateToEditDivision&old_divisionName=<%=div.getDivisionName()%>'">
			<th><input type="button" value="削除" onclick="location.href='./Main?action=action.DeleteDivision&old_divisionName=<%=div.getDivisionName()%>'">
		<% }%>
		</tr>
	</table>
 <input type="button" value="新規追加" onclick="location.href='./Main?action=action.NavigateToEditDivision'">

</body>
</html>