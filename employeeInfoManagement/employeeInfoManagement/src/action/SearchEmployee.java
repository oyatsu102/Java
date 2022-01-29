package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;
import dao.EmployeeDAO;
import model.Employee;

public class SearchEmployee implements CommonLogic {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String divisionName = null;
		int employeeID = 0;
		String searchWord = null;

		try {
			divisionName = ((String) request.getParameter("divisionName")) == "未選択" ? null
					: (String) request.getParameter("divisionName");
			//部署の選択がされない場合、"未選択"の文字列が取得される。これをnullに変換
			employeeID = Integer.parseInt(((request.getParameter("employeeID")) == "" ? "0"
					: request.getParameter("employeeID")));
			//employeeIDの入力がない場合、空文字で取得される。これを一旦文字列の"0"に置換し、Int型に変換する。
			searchWord = (String) request.getParameter("searchWord");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		//部署IDの取得(employeeのテーブルには部署名が存在しないため)
		DivisionDAO divDAO = new DivisionDAO();
		int divisionID = divDAO.getDivisionID(divisionName);

		EmployeeDAO empDAO = new EmployeeDAO();
		List<Employee> eList = empDAO.searchEmployee(divisionID, employeeID, searchWord);

		if (eList == null) {//DAOの方でエラーが起きると、ここでeListがnullになる
			return "error.jsp";
		}
		request.setAttribute("eList", eList);
		return "AllEmployee.jsp";

	}
}
