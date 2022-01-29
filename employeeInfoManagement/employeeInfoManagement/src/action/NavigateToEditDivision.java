
//部署の編集、新規作成ページへ→新規作成画面に遷移するのみ

package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NavigateToEditDivision implements CommonLogic{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "EditDivision.jsp";
	}


}
