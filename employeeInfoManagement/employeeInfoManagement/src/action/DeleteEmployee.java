package action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import dao.VerificationPhotoDAO;

public class DeleteEmployee implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int empID = 0;
		int picID = 0;

		try {
			request.setCharacterEncoding("UTF-8");
			empID = Integer.parseInt(request.getParameter("empID"));
			picID = Integer.parseInt(request.getParameter("picID"));
			System.out.println("empID："+empID+", picID："+picID);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		int updateNumber_empdao = 0;
		int updateNumber_verdao = 0;

		if(empID != 0) {
			EmployeeDAO empdao = new EmployeeDAO();
			updateNumber_empdao = empdao.deleteEmployee(empID);
			if(updateNumber_empdao > 0) {//empの削除が成功した場合のみ、画像テーブルの削除も実施。画像だけ削除されたくないため。
				VerificationPhotoDAO verdao = new VerificationPhotoDAO();
				updateNumber_verdao = verdao.deletePic(picID);
			}else {
				request.setAttribute("deleteResult", "error");
				AllEmployee ae = new AllEmployee();
				ae.execute(request, response);
				return "AllEmployee.jsp";
			}
		}else {
			return "error.jsp";
		}

		if(updateNumber_empdao > 0 && updateNumber_verdao > 0) {
			System.out.println("AllEmployeeのメソッドを実行");
			AllEmployee ae = new AllEmployee();
			ae.execute(request, response);
			System.out.println(empID+"の削除が完了しました。");
			return "AllEmployee.jsp";
		}else {
			request.setAttribute("deleteResult", "error");
			AllEmployee ae = new AllEmployee();
			ae.execute(request, response);
			return "AllEmployee.jsp";
		}
	}

}
