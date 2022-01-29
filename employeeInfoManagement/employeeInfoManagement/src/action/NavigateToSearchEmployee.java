package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;
import model.Division;

public class NavigateToSearchEmployee implements CommonLogic {

	//部署一覧を取得し、SearchEmployeeに遷移
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		DivisionDAO divDAO = new DivisionDAO();
		List<Division> dList = divDAO.findAll();
		request.setAttribute("dList", dList);

		return "SearchEmployee.jsp";
	}

}
