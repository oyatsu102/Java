package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmployeeDAO;
import model.Employee;

@WebServlet("/DownloadFile")
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadFileServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println("start DownloadFile");
			//現在時刻の取得、フォーマット設定
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String fDate = sdf.format(date);

			response.setContentType("application/csv;charset=Shift-JIS");//ここでファイル内の文字コードを指定(UTF-8にすると日本語が文字化けする)
			String encodedFilename = URLEncoder.encode("社員情報_" + fDate + ".csv", "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename*=\"UTF-8''" + encodedFilename);
			//普通にfileNameを指定すると、ファイル名に接頭語接尾語以外に変な数値文字列が入る。ここで改めてファイル名を指定する。

			//社員情報の取得
			EmployeeDAO dao = new EmployeeDAO();
			List<Employee> eList = dao.findAll();

			try (PrintWriter writer = response.getWriter();) {
				for (Employee emp : eList) {
					int empID = emp.getEmpID();
					String empName = emp.getEmpName();
					int age = emp.getAge();
					String sex = emp.getSex();
					int picID = emp.getPicID();
					String postNumber = emp.getPostNumber();
					String pref = emp.getPref();
					String address = emp.getAddress();
					int divisionID = emp.getDivisionID();
					String enteredDate = emp.getEnteredDate();
					String quittingDate = emp.getQuittingDate();

					String outputString = empID + "," + empName + "," + age + "," + sex + "," + picID + "," + postNumber
							+ "," + pref + "," + address + "," + divisionID + "," + enteredDate + "," + quittingDate
							+ "\r\n";
					writer.print(outputString);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("start DownloadFile");
		//現在時刻の取得、フォーマット設定
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String fDate = sdf.format(date);

		response.setContentType("application/csv;charset=Shift-JIS");//ここでファイル内の文字コードを指定(UTF-8にすると日本語が文字化けする)
		String encodedFilename = URLEncoder.encode("社員情報_" + fDate + ".csv", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename*=\"UTF-8''" + encodedFilename);

		//社員情報の取得
		EmployeeDAO dao = new EmployeeDAO();
		List<Employee> eList = dao.findAll();

		try (PrintWriter writer = response.getWriter();) {
			for (Employee emp : eList) {
				int empID = emp.getEmpID();
				String empName = emp.getEmpName();
				int age = emp.getAge();
				String sex = emp.getSex();
				int picID = emp.getPicID();
				String postNumber = emp.getPostNumber();
				String pref = emp.getPref();
				String address = emp.getAddress();
				int divisionID = emp.getDivisionID();
				String enteredDate = emp.getEnteredDate();
				String quittingDate = emp.getQuittingDate();

				String outputString = empID + "," + empName + "," + age + "," + sex + "," + picID + "," + postNumber
						+ "," + pref + "," + address + "," + divisionID + "," + enteredDate + "," + quittingDate;
				writer.write(outputString);
			}
		}
	}

}
