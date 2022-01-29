//部署の編集作業

package action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;

public class EditDivision implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//リクエストパラメータの取得
		/* AllDivisionページの、
		 * ①新規作成ボタンから遷移：入力された新規部署名
		 * ②既存部署の編集ボタンから遷移：対象の既存部署名、入力された部署名
		 * パラメータを2つ渡す
		  */
		String old_divisionName = null;
		String new_divisionName = null;

			try {
				System.out.println("EditDivision start");
				request.setCharacterEncoding("UTF-8");

				//パラメータを取得した際、nullだとエラーになるため、nullなら空文字に変換する
				old_divisionName = (request.getParameter("old_divisionName") == null) ? "" : request.getParameter("old_divisionName");
				new_divisionName = (request.getParameter("new_divisionName") == null) ? "" : request.getParameter("new_divisionName");
				int updateNumber = 0;

				if(!new_divisionName.isEmpty()) {
					if(old_divisionName == null  || old_divisionName.equals("null")) {//新規作成 →jspでnullが文字列として認識されていることがある。
						System.out.println("新規作成します");
						DivisionDAO dao = new DivisionDAO();
						updateNumber = dao.addDivisionName(new_divisionName);

					}else{//既存の部署名を更新
						System.out.println("更新します");
						DivisionDAO dao = new DivisionDAO();
						updateNumber = dao.editDivisionName(old_divisionName, new_divisionName);
					}

					if(updateNumber > 0) {
					  System.out.println("AllDivisionのメソッドを実行");
					  AllDivision ad = new AllDivision();
					  ad.execute(request, response);
					  return "resultEditDivision.jsp";
					}else {//dbの更新に失敗
						request.setAttribute("errorMessage", "データベースの更新に失敗しました。" );
						return "resultEditDivision.jsp";
					}
				}else {//欄への入力がなかった場合
					return "error.jsp";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
	}

}
