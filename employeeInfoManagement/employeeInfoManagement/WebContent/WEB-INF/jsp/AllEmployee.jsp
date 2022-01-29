<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Employee"%>
<%@ page import="action.AllEmployee"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% List<Employee> eList = (ArrayList<Employee>) request.getAttribute("eList"); %>
<% String deleteResult = null; %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員一覧</title>
</head>
<body>
<% deleteResult = (String)request.getAttribute("deleteResult");%>
<% if (deleteResult != null && deleteResult=="error") {%>
	<p> 削除することができませんでした。</p>
<% }%>

	<h2>社員一覧：</h2>
	<% if(eList == null || eList.size()==0) {%>
	<p>登録されている社員がいません。</p>
	<%}else{ %>
	<table>
		<tr>
			<th>社員ID</th>
			<th>名前</th>
		</tr>
		<% for(Employee emp :eList){ %>
		<tr>
			<th><%=emp.getEmpID()%></th>
			<th><%=emp.getEmpName()%></th><%--表示するのはIDと氏名のみ。その他は編集時に使用 --%>

			<th>
			<input type="button" value="編集" onclick="location.href='./Main?action=action.NavigateToEditEmployee&old_EmpID=<%=emp.getEmpID()%>&old_EmpName=<%=emp.getEmpName()%>&old_Age=<%=emp.getAge()%>&old_Sex=<%=emp.getSex()%>&old_PicID=<%=emp.getPicID()%>&old_postNumber=<%=emp.getPostNumber()%>&old_pref=<%=emp.getPref()%>&old_address=<%=emp.getAddress()%>&old_divisionID=<%=emp.getDivisionID()%>&old_enteredDate=<%=emp.getEnteredDate()%>&old_quittingDate=<%=emp.getQuittingDate()%>'">
			<%request.setAttribute("emp",emp);%>

			</th>
			<th><input type="button" value="削除" onclick="location.href='./Main?action=action.DeleteEmployee&empID=<%=emp.getEmpID()%>&picID=<%=emp.getPicID()%>'"></th>
		<%} %>
		</tr>
	<%} %>
	</table>
	<p><input type="button" value="新規作成" onclick="location.href='./Main?action=action.NavigateToEditEmployee'"></p>
	<p><input type="button" value="検索" onclick="location.href='./Main?action=action.NavigateToSearchEmployee'"></p>
	<p><input type="button" value="csvに出力" onclick="location.href='./DownloadFile'"></p>

</body>
</html>