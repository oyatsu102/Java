<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<%if (errorMessage == null) {%>
<title>成功</title>
<%} else {%>
<title>エラー</title>
<%}%>

</head>
<body>
	<%if (errorMessage == null) {%>
	<p>データベースの登録に成功しました。</p>
	<%} else {%>
	<p>データベースの登録に失敗しました。</p>
	<%}%>
	<input type="button" value="トップページへ" onclick="location.href='./Main?action=action.AllEmployee'">
</body>
</html>