package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import model.Employee;

public class AllEmployee implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		EmployeeDAO dao = new EmployeeDAO();
		List<Employee> eList = dao.findAll();

		request.setAttribute("eList",eList);//リクエストスコープにインスタンスを保持させる

		return "AllEmployee.jsp";
	}

}
