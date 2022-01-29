<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Division"%>
<%@ page import="model.Employee" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<% List<Division> dList = (ArrayList<Division>)request.getAttribute("dList");%>
<% Employee emp = (Employee)request.getAttribute("emp"); %>

<% String currentDivisionName = (String)request.getAttribute("currentDivisionName");%>
<% String[] prefList = {"北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県","茨城県","栃木県","群馬県",
		                  "埼玉県","千葉県","東京都","神奈川県","新潟県","富山県","石川県","福井県","山梨県","長野県",
		                  "岐阜県","静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県","奈良県","和歌山県",
	                      "鳥取県","島根県","岡山県","広島県","山口県","徳島県","香川県","愛媛県","高知県",
	                      "福岡県","佐賀県","長崎県","熊本県","大分県","宮崎県","鹿児島県","沖縄県"}; %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員情報の追加・編集</title>
</head>
<body>
<h2>社員データを編集します</h2>

<% if(emp == null){%><%--新規追加ボタンから遷移した場合 --%>

<form action="./Main?action=action.EditEmployee&newOrEdit=new" method="post" enctype=multipart/form-data>
	<%request.setAttribute("newOrEdit","new"); %>
	<p>社員ID：<input type="text" name="empID" ></p>
	<p>名前：<input type="text" name="empName" ></p>
	<p>年齢：<input type="text" name="age" ></p>
	<p>性別：<label><input type="radio" name="sex" value="男">男</label>
			<label><input type="radio" name="sex" value="女">女</label>
	</p>
	<p>写真：</p>
	<input type="file" name="uploadfile" value="ファイルを選択"></input>

	<p>郵便番号：<input type="text" name="postNumber" placeholder="〒000-0000"></p>
	<p>都道府県：<select name="pref">
		<%for(String pref :prefList){ %>
		<option value=<%=pref%>><%=pref %></option>
		<%} %>
		</select>
	</p>
	<p>住所：<input type="text" name="address" placeholder="千代田区1-1-1"></p>
	<p>所属部署：<select name="divisionName">
		<% for(Division div :dList){ %>
		<option value=<%=div.getDivisionName()%>><%=div.getDivisionName()%></option>
		<% } %>
	</select>
	</p>
	<p>入社日：<input type="text" name="EnteredDate" placeholder="yyyy-MM-dd"></p>
	<p>退社日：<input type="text" name="QuittingDate" placeholder="yyyy-MM-dd"></p>
	<input type="submit" value="設定">
</form>
<button onclick="location.href='./Main?action=action.AllEmployee'">キャンセル</button>

<% }else{ %><%--編集ボタンから遷移した場合 --%>
<form action="./Main?action=action.EditEmployee&newOrEdit=edit&current_empID=<%=emp.getEmpID()%>" method="post" enctype=multipart/form-data> <%--enctype=multipart/form-data --%>

	<%request.setAttribute("current_empID", emp.getEmpID());%>

	<% String QuittingDate = emp.getQuittingDate(); %>
	<% if(QuittingDate.equals("null")){
		QuittingDate = "";%>
	<%}else if(QuittingDate==null){
		QuittingDate = ""; %>
	<%} %>
	<%--QuittingDateがnullなら規定値を入れる。文字列として"null"が入っている場合もあり --%>

	<p>社員ID：<input type="text" name="empID" value=<%=emp.getEmpID()%>></p>
	<p>名前：<input type="text" name="empName" value=<%=emp.getEmpName() %>></p>
	<p>年齢：<input type="text" name="age" value=<%=emp.getAge()%>></p>
	<% if(emp.getSex().equals("男")) { %>
	<p>性別：<label><input type="radio" name="sex" value="男" checked="checked">男</label>
			<label><input type="radio" name="sex" value="女">女</label>
	</p>
	<%}else{ %>
	<p>性別：<label><input type="radio" name="sex" value="男">男</label>
			<label><input type="radio" name="sex" value="女" checked="checked">女</label>
	</p>
	<% }%>
	<p>写真：</p><%--写真データを表示させる--%>
	<img src="./VerificationPhoto?picID=<%=emp.getPicID()%>" alt="登録されている画像がありません" onerror="this.onerror = null; this.src='';">

	<p><input type="file" name="uploadfile" value="ファイルを選択"></input></p>
	<input type="hidden" name="picID" value=<%=emp.getPicID()%>></input>

	<p>郵便番号：<input type="text"  name="postNumber" value=<%=emp.getPostNumber()%>></p>
	<p>都道府県：<select name="pref">
		<%for(String pref :prefList){ %>
		<option value=<%=pref%> <%if(pref.equals(emp.getPref())){%>selected<%; }%>> <%=pref%> </option>
		<%} %>
		</select>
	</p>
	<p>住所：<input type="text" name="address" value=<%=emp.getAddress()%>></p>
	<p>所属部署：
	<select name="divisionName">
		<% for(Division div :dList){ %>
		<option value=<%=div.getDivisionName()%> <%if(div.getDivisionName().equals(currentDivisionName)){%>selected<%; }%>> <%=div.getDivisionName()%> </option>
		<%} %>
	</select>

	<p>入社日：<input type="text" name="EnteredDate" placeholder="yyyy-MM-dd" value=<%=emp.getEnteredDate()%> ></p>
	<p>退社日：<input type="text" name="QuittingDate" placeholder="yyyy-MM-dd" value=<%=QuittingDate%> ></p>

	<input type="submit" value="設定">
</form>
<button onclick="location.href='./Main?action=action.AllEmployee'">キャンセル</button>

<%} %>
</body>
</html>