package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommonLogic;

/**
 * Servlet implementation class Division
 */
@WebServlet("/Employee")//employee用に変更
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		System.out.println("action1："+action);

		// 実行するLogicクラスのインスタンスを取得する
		CommonLogic logic = null;
		// 遷移先のパスを設定
		String next = "/WEB-INF/jsp/";
		// トップ画面からの遷移の場合
		if (action == null) {
			next = "TopPage.jsp";
		} else {//actionパラメータがnullでない場合
			System.out.println("action2："+action);
			try {
				// パラメータで渡されたクラスをロード
				Class clazz = Class.forName(action);
				// 該当クラスのインスタンスを取得
				logic = (CommonLogic) clazz.newInstance();
				// 該当クラスのロジックを実行
				String fileName = logic.execute(request, response);
				System.out.println("fileName："+fileName);
				// 遷移先のパスとして文字列連結
				next = next + fileName;

			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				next = next + "error.jsp";//システムエラー
				System.out.println(e);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(next);
		dispatcher.forward(request, response);//フォワード処理
	}


}
