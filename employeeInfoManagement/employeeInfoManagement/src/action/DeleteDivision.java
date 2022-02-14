package action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;

public class DeleteDivision implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String old_divisionName = null;

		try {
			request.setCharacterEncoding("UTF-8");
			old_divisionName = request.getParameter("old_divisionName");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int updateNumber = 0;

		if(old_divisionName != null) {
			DivisionDAO dao = new DivisionDAO();
			updateNumber = dao.deleteDivisionName(old_divisionName);
		}else {
			return "error.jsp";
		}

		if(updateNumber > 0) {
			System.out.println(old_divisionName+"の削除が完了しました。");
			System.out.println("AllDivisionのメソッドを実行");
			AllDivision ad = new AllDivision();
			ad.execute(request, response);
			return "AllDivision.jsp";
		}else {
			request.setAttribute("deleteResult", "error");
			AllDivision ad = new AllDivision();
			ad.execute(request, response);
			return "AllDivision.jsp";
		}
	}
}
