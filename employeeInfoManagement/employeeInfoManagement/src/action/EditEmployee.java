package action;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DivisionDAO;
import dao.EmployeeDAO;
import dao.VerificationPhotoDAO;

public class EditEmployee implements CommonLogic {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("EditEmployee.java start");
		//全パラメータを渡して、daoのメソッドに渡して実行する
		//新規か編集か：リクエストパラメータnewOrEditの値で判断する
		//新規追加：退社日以外の項目がnullでないことを確認→あれば再度入力させる、DAOのaddEmployeeを実行
		//編集：退社日以外の項目がnullでないことを確認→あれば再度入力させる、DAOのeditEmployeeを実行

		try {
			System.out.println("パラメータの取得を開始");
			request.setCharacterEncoding("UTF-8");

			//各パラメータを取得
			int empID = Integer.parseInt(request.getParameter("empID"));
			System.out.println(empID);
			String empName = request.getParameter("empName");
			System.out.println(empName);
			int age = Integer.parseInt(request.getParameter("age"));
			System.out.println(age);
			String sex = request.getParameter("sex");
			System.out.println(sex);

			String postNumber = request.getParameter("postNumber");
			System.out.println(postNumber);
			String pref = request.getParameter("pref");
			System.out.println(pref);
			String address = request.getParameter("address");
			System.out.println(address);
			String divisionName = request.getParameter("divisionName");//部署名なので、ここから部署IDを取得する必要がある
			System.out.println(divisionName);

			Date EnteredDate = java.sql.Date.valueOf(request.getParameter("EnteredDate"));
			System.out.println(EnteredDate);
			//EnteredDateは未入力を許さない。未入力の場合ここでエラーがスローされ、エラー画面へ飛ぶ。(チェックは入れない)

			Date QuittingDate = null;
			try {//QuittingDateのみ未入力を許す。ただし型変換でエラーになるため、nullを格納する
				QuittingDate = java.sql.Date.valueOf(request.getParameter("QuittingDate"));
			} catch (IllegalArgumentException e) {
				QuittingDate = null;
			}
			System.out.println(QuittingDate);

			InputStream inputStream = null;
			Long fileSize = 0L;
			System.out.println("画像オブジェクトの取得を開始");
			try {//アップロードされた画像をinputStreamとして取得する。
				inputStream = request.getPart("uploadfile").getInputStream();
				fileSize = request.getPart("uploadfile").getSize();
				//アップロードされたファイルサイズを取得。未アップロードでも（空の状態で?）dbに登録されるためチェックが必要。
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}

			System.out.println(empID + ", " + empName + ", " + age + ", " + sex + ", " + postNumber
					+ ", " + pref + ", " + address + ", " + EnteredDate + ", " + QuittingDate);

			if (empID == 0 || empName == "" || age == 0 || sex == "" || postNumber == "" || pref == ""
					|| address == "" || divisionName == "") {
				System.out.println("必須項目が満たされていないか、入力内容に誤りがあります");
				return "error.jsp";
			} else {
				String newOrEdit = request.getParameter("newOrEdit");

				if (newOrEdit.equals("new")) {//社員情報を新規作成
					System.out.println("社員情報の新規作成を開始");
					DivisionDAO divDAO = new DivisionDAO();
					int divisionID = divDAO.getDivisionID(divisionName);//部署名から部署IDを取得

					int picID = empID;//写真ID＝社員IDとする。
					if (fileSize > 0) {//アップロードされたファイルがある場合（アップロードされていないとファイルサイズが０）
						VerificationPhotoDAO verDAO = new VerificationPhotoDAO();
						int updateNumber_addPic = verDAO.addPic(picID, inputStream);
						if (updateNumber_addPic == 0) {//更新されていなければエラー
							System.out.println("addPic error");
							request.setAttribute("errorMessage", "データベースの更新に失敗しました。");
							return "resultEditEmployee.jsp";
						}
					} else {
						System.out.println("写真データがアップロードされていません");
						return "resultEditEmployee.jsp";//新規登録で、写真データがない場合はエラー
					}

					EmployeeDAO empDAO = new EmployeeDAO();
					int updateNumber_addEmpInfo = empDAO.addEmployeeInfo(empID, empName, age, sex, picID, postNumber,
							pref,
							address,
							divisionID, EnteredDate, QuittingDate);
					if (updateNumber_addEmpInfo == 0) {
						System.out.println("addEmployeeInfo error");
						request.setAttribute("errorMessage", "データベースの更新に失敗しました。");
						return "resultEditEmployee.jsp";
					}

				} else if (newOrEdit.equals("edit")) {//既存社員情報の編集
					System.out.println("社員情報の編集を開始");
					int picID = empID;//画像ID＝社員IDとする。
					String current_empID = request.getParameter("current_empID");//編集前の部署IDを取得(更新する対象を決めるための社員ID
					DivisionDAO divDAO = new DivisionDAO();
					int divisionID = divDAO.getDivisionID(divisionName);//部署名から部署IDを取得

					if (fileSize > 0) {
						VerificationPhotoDAO verDAO = new VerificationPhotoDAO();
						int updateNum_updatePic = verDAO.updatePic(picID, inputStream);

						if (updateNum_updatePic == 0) {//picIDがテーブルになくdbの更新に失敗した場合、addPicを実行
							System.out.println("updatePic error");
							int updateNum_addPic = verDAO.addPic(picID, inputStream);//要検討
							if (updateNum_addPic == 0) {//addPicでも失敗した場合、エラーとする
								System.out.println("addPic error");
								request.setAttribute("errorMessage", "データベースの更新に失敗しました。");
								return "resultEditEmployee.jsp";
							} else {
								System.out.println("addPicに成功");
							}
						} else {
							System.out.println("updatePicに成功");
						}
					} else {
						System.out.println("写真データがアップロードされていません");
						//既存社員の編集では、写真データがない場合を許す。すでに登録済みの前提。
					}

					EmployeeDAO empDAO = new EmployeeDAO();
					int updateNumber = empDAO.editEmployeeInfo(Integer.parseInt(current_empID), empID,
							empName, age,
							sex,
							picID, postNumber,
							pref, address, divisionID, EnteredDate, QuittingDate);
					if (updateNumber == 0) {
						System.out.println("editEmployeeInfo error");
						request.setAttribute("errorMessage", "データベースの更新に失敗しました。");
						return "resultEditEmployee.jsp";
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error.jsp";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "error.jsp";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "error.jsp";
		}

		//dbが更新された場合の結果表示　※ここまで到達している＝エラーになっていない
		AllEmployee ae = new AllEmployee();
		ae.execute(request, response);
		return "resultEditEmployee.jsp";
	}
}
