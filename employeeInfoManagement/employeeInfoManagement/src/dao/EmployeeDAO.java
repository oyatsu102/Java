package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;

import model.Employee;

public class EmployeeDAO {
	Connection conn = null;
	ResultSet rs = null;
	int ri = 0;//更新系SQL実行時の変化した行数
	PreparedStatement pstmt = null;

	public List<Employee> findAll() {
		System.out.println("employeefindAll start.");
		List<Employee> eList = new ArrayList<Employee>();

		try {
			Class.forName("org.h2.Driver");//JDBCドライバの読み込み
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");//dbへ接続

			String sql = "SELECT * FROM Employee;";//sql文を準備
			pstmt = conn.prepareStatement(sql);//準備したsqlをdbに届けるPrepareStatementインスタンスを取得。
			rs = pstmt.executeQuery();//SQLを実行し、ResultSetインスタンスに結果を格納。
			while (rs.next()) {
				int empID = rs.getInt("社員ID");
				String empName = rs.getString("名前");
				int age = rs.getInt("年齢");
				String sex = rs.getString("性別");
				int picID = rs.getInt("写真ID");
				String postNumber = rs.getString("郵便番号");
				String pref = rs.getString("都道府県");
				String address = rs.getString("住所");
				int divisionID = rs.getInt("部署ID");
				String enteredDate = rs.getString("入社日");
				String quittingDate = rs.getString("退社日");

				Employee employee = new Employee(empID, empName, age, sex, picID, postNumber, pref, address, divisionID,
						enteredDate, quittingDate);//取得した値をEmployeeインスタンスに格納
				System.out.println(empID + empName + age + sex + picID + postNumber + pref + address + divisionID
						+ enteredDate + quittingDate);
				eList.add(employee);//リストにemployeeインスタンスを格納
			}
		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return null;
		} finally {//PrepareStatementインスタンス、ResultSetインスタンスのクローズ。
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return eList;
	}

	public List<Employee> searchEmployee(int divisionID, int employeeID, String searchWord) {
		System.out.println("start searchEmployee");
		List<Employee> eList = new ArrayList<Employee>();

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");

			System.out.println("divisionID：" + divisionID + ", employeeID：" + employeeID + ", searchWord：" + searchWord);

			String expression = null;//条件式の作成
			//sql文のパラメータをセットするための変数を初期化
			int setInt1 = 0;
			int setInt2 = 0;
			String setString = null;

			if (divisionID != 0) {
				expression = "部署ID=?";
				setInt1 = divisionID;
				System.out.println("部署IDで検索");
				if (employeeID != 0) {
					expression = expression + " And 社員ID=?";
					setInt2 = employeeID;
					System.out.println("部署IDと社員IDで検索");
					if (!(StringUtils.isNullOrEmpty(searchWord))) {
						expression = expression + " And 名前 like ?";
						setString = "%" + searchWord + "%";
						System.out.println("部署IDと社員IDと名前に含む文字で検索");
					}
				} else if (!(StringUtils.isNullOrEmpty(searchWord))) {
					expression = expression + " And 名前 like ?";
					setString = "%" + searchWord + "%";
					System.out.println("部署IDと名前に含む文字で検索");
				}
			//divisionIDの入力がない場合
			} else if (employeeID != 0) {
				expression = "社員ID=?";
				setInt1 = employeeID;
				System.out.println("社員IDで検索");
				if (!(StringUtils.isNullOrEmpty(searchWord))) {
					expression = expression + " And 名前 like ?";
					setString = "%" + searchWord + "%";
					System.out.println("社員IDと名前に含む文字で検索");
				}
			} else if (!(StringUtils.isNullOrEmpty(searchWord))) {
				expression = "名前 like ?";
				setString = "%" + searchWord + "%";
				System.out.println("名前に含む文字で検索");
			} else {//全ての入力がない場合、全社員を表示させる。
				System.out.println("入力値なし");
				expression = null;
			}

			System.out.println("expression：" + expression);//作成した条件式を確認
			String sql = null;
			if (expression != null) {
				sql = "SELECT * FROM Employee WHERE " + expression + ";";//sql文を準備
			} else {
				sql = "SELECT * FROM Employee;";
			}
			pstmt = conn.prepareStatement(sql);//準備したsqlをdbに届けるPrepareStatementインスタンスを取得。

			if (setInt1 != 0) {
				pstmt.setInt(1, setInt1);
				if (setInt2 != 0) {
					pstmt.setInt(2, setInt2);
					if (setString != null) {
						pstmt.setString(3, setString);
					}
				} else {
					if (setString != null) {
						pstmt.setString(2, setString);
					}
				}
			} else if (setString != null) {
				pstmt.setString(1, setString);
			}

			rs = pstmt.executeQuery();//SQLを実行し、ResultSetインスタンスに結果を格納。
			while (rs.next()) {
				int empID = rs.getInt("社員ID");
				String empName = rs.getString("名前");
				Employee employee = new Employee(empID, empName);//取得した値をEmployeeインスタンスに格納
				eList.add(employee);//リストにemployeeインスタンスを格納
			}
		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return null;
		} finally {//PrepareStatementインスタンス、ResultSetインスタンスのクローズ。
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (pstmt != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (conn != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return eList;
	}

	public int editEmployeeInfo(int current_empID, int empID, String empName, int age, String sex, int picID,
			String postNumber, String pref, String address, int divisionID, Date EnteredDate, Date QuittingDate) {
		System.out.println("editEmployeeInfo start.");

		try {
			Class.forName("org.h2.Driver");//jdbcドライバの読み込み
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");//dbへ接続

			String sql = "UPDATE EMPLOYEE SET 社員ID=?,名前=?,年齢=?,性別=?,写真ID=?,郵便番号=?,都道府県=?,住所=?,部署ID=?,入社日=?,退社日=? WHERE 社員ID=?";
			//String sql = "UPDATE EMPLOYEE SET ID=?,名前=?,年齢=?,性別=?,写真ID=?,郵便番号=?,都道府県=?,住所=?,部署ID=?,入社日=?,退社日=? WHERE 社員ID=?";//デバッグ　異常系テスト用

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empID);
			pstmt.setString(2, empName);
			pstmt.setInt(3, age);
			pstmt.setString(4, sex);
			pstmt.setInt(5, picID);
			pstmt.setString(6, postNumber);
			pstmt.setString(7, pref);
			pstmt.setString(8, address);
			pstmt.setInt(9, divisionID);
			pstmt.setDate(10, EnteredDate);
			pstmt.setDate(11, QuittingDate);
			pstmt.setInt(12, current_empID);

			ri = pstmt.executeUpdate();//sql実行、更新行数を格納
			return ri;
		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return ri;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int addEmployeeInfo(int empID, String empName, int age, String sex, int picID, String postNumber,
			String pref, String address, int divisionID, Date EnterDate, Date QuittingDate) {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "INSERT INTO Employee (社員ID,名前,年齢,性別,写真ID,郵便番号,都道府県,住所,部署ID,入社日,退社日) VALUES (?,?,?,?,?,?,?,?,?,?,?)"; //新規行追加、社員IDは連番にするので指定しない
			//String sql = "INSERT (ID, 部署名) VALUES (SELECT COUNT(*)  FROM DIVISION+1,?)"; //デバッグ（異常系テスト用）

			pstmt = conn.prepareStatement(sql);//準備したSQLをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setInt(1, empID);//sqlのパラメータをセット,番号は1スタート
			pstmt.setString(2, empName);
			pstmt.setInt(3, age);
			pstmt.setString(4, sex);
			pstmt.setInt(5, picID);
			pstmt.setString(6, postNumber);
			pstmt.setString(7, pref);
			pstmt.setString(8, address);
			pstmt.setInt(9, divisionID);
			pstmt.setDate(10, EnterDate);
			pstmt.setDate(11, QuittingDate);

			ri = pstmt.executeUpdate();//SQLを実行
			return ri;

		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return ri;
		} finally {//PrepareStatementインスタンス,ResultSetインスタンスのクローズ
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return ri;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return ri;
				}
			}

		}
	}

	public int deleteEmployee(int empID) {
		try {
			Class.forName("org.h2.Driver");//jdbcドライバの読み込み
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");//dbへ接続

			String sql = "DELETE FROM Employee WHERE 社員ID=?";
			//String sql = "DELETE FROM Employee WHERE ID=?";//異常系テスト用
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empID);
			ri = pstmt.executeUpdate();//SQLを実行
			return ri;

		}catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return ri;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int setPicID(int current_empID, int picID) {
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "UPDATE Employee SET 写真ID=? WHERE 社員ID=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, picID);
			pstmt.setInt(2, current_empID);

			ri = pstmt.executeUpdate();//sql実行、更新行数を格納
			return ri;

		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return ri;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
