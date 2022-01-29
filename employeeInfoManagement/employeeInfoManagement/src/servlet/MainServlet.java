package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommonLogic;

@WebServlet("/Main")
@MultipartConfig()
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//どの処理の実行も経由できるようなサーブレットにする。= 機能追加を容易にする

	public MainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("DivisionServlet doGet start.");

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		System.out.println("action1："+action);

		CommonLogic logic = null;// 実行するLogicクラスのインスタンスを取得する
		String next = "/WEB-INF/jsp/";// 遷移先のパスを設定
		if (action == null) {// トップ画面からの遷移の場合
			next = "TopPage.jsp";
		} else {//actionパラメータがnullでない場合
			System.out.println("action2："+action);
			try {
				Class clazz = Class.forName(action);// パラメータで渡されたクラスをロード
				logic = (CommonLogic) clazz.newInstance();// 該当クラスのインスタンスを取得
				String fileName = logic.execute(request, response);	// 該当クラスのロジックを実行
				System.out.println("fileName："+fileName);// 遷移先のパスとして文字列連結
				next = next + fileName;

			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
				// システムエラーとしてエラー画面を表示
				next = next + "error.jsp";
				e.printStackTrace();
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(next);
		dispatcher.forward(request, response);//フォワード処理
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DivisionServlet doPost start.");

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");// 実行するLogicクラスのインスタンスを取得する
				CommonLogic logic = null;
				String next = "/WEB-INF/jsp/";// 遷移先のパスを設定
				if (action == null) {// トップ画面からの遷移の場合
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

					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException e) {
						next = next + "error.jsp";//システムエラー
						System.out.println(e);
					}
				}
				RequestDispatcher dispatcher = request.getRequestDispatcher(next);
				dispatcher.forward(request, response);//フォワード処理
	  }
}
