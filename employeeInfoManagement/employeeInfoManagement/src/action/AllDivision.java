//モデル。DBから値をとってきて（DivisionDAOを実行する）、jspファイルへ表示を依頼。
package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;
import model.Division;

public class AllDivision implements CommonLogic{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		DivisionDAO dao = new DivisionDAO();
		List<Division> dList = dao.findAll();
		
		request.setAttribute("dList",dList);//リクエストスコープにインスタンスを保持させる
		
		return "AllDivision.jsp";
	}
}
