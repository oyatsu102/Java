package action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;
import model.Division;
import model.Employee;

public class NavigateToEditEmployee implements CommonLogic {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("NavigateToEditEmployee Start.");
		try {
			request.setCharacterEncoding("UTF-8");
			String old_EmpID = request.getParameter("old_EmpID");

			DivisionDAO divDAO = new DivisionDAO();
			List<Division> dList;

			if (old_EmpID != null) {//編集ボタンからの遷移（社員IDのパラメータがある場合）
				System.out.println("編集ボタンからの遷移");
				int empID = Integer.parseInt(request.getParameter("old_EmpID"));
				String empName = (String) request.getParameter("old_EmpName");
				int age = Integer.parseInt(request.getParameter("old_Age"));
				String sex = (String) request.getParameter("old_Sex");
				int picID = Integer.parseInt(request.getParameter("old_PicID"));
				String postNumber = (String)request.getParameter("old_postNumber");
				String pref = (String)request.getParameter("old_pref");
				String address = (String)request.getParameter("old_address");
				int divisionID = Integer.parseInt(request.getParameter("old_divisionID"));
				String enteredDate = (String)(request.getParameter("old_enteredDate"));
				String quittingDate = (String)(request.getParameter("old_quittingDate"));

				System.out.println("編集対象の情報を表示します");
				System.out.println(empID+", "+empName+", "+age+", "+sex+", "+picID+", " +postNumber+","+ pref+", "+address+", "+ divisionID+", "+enteredDate+", "+quittingDate);

				Employee emp = new Employee(empID, empName, age, sex, picID, postNumber, pref, address, divisionID, enteredDate,quittingDate);
				//取得したパラメータでEmployeeインスタンスを作成
				request.setAttribute("emp", emp);

				dList = divDAO.findAll();
				request.setAttribute("dList", dList);//dListをリクエストスコープにセット→編集画面のプルダウンで全部署から選択できるようにするため。

				//部署IDにひもづく部署名を取得しリクエストパラメータにセット(部署IDがある場合=編集ボタンから遷移した場合)
				String str_divisionID = request.getParameter("old_divisionID");
				if (str_divisionID != null) {
					String divisionName = divDAO.getDivisionName(divisionID);
					request.setAttribute("currentDivisionName", divisionName);
				}

			} else {//新規ボタンからの遷移（パラメータなし）
				System.out.println("新規作成。dListを作成");
				dList = divDAO.findAll();
				request.setAttribute("dList", dList);
				//dListをリクエストスコープにセット→編集画面のプルダウンで全部署から選択できるようにするため。
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		}
		return "EditEmployee.jsp";
	}

}
