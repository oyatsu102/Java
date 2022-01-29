package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Division;

public class DivisionDAO {
	Connection conn = null;
	ResultSet rs = null;
	int ri = 0;//更新系のSQL実行時に変化した行数が返される。Int型の変数を用意。
	PreparedStatement pstmt = null;

	public List<Division> findAll() {
		System.out.println("findAll start.");
		List<Division> dList = new ArrayList<Division>();

		try {
			Class.forName("org.h2.Driver");//JDBCドライバを読み込み(ドライバクラス名はdbによって異なる)
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");//データベースへ接続

			String sql = "SELECT ID, 部署名 FROM DIVISION;"; //SQL文を準備
			pstmt = conn.prepareStatement(sql);//準備したSQLをdbに届けるPrepareStatementインスタンスを取得
			rs = pstmt.executeQuery();//SQLを実行し、ResultSetインスタンスに結果を格納
			while (rs.next()) {
				int division_id = rs.getInt("ID");//項目ごとに値を取得。項目数分記載。
				String division_name = rs.getString("部署名");
				Division division = new Division(division_id, division_name);//取得した値をDivisionインスタンスに保存する
				dList.add(division);//リストにDivisionインスタンスを追加
			}

		} catch (SQLException |ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return null;

		} finally {//PrepareStatementインスタンス,ResultSetインスタンスのクローズ
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
					return null;
				}
			}
		}
		return dList;
	}

	public int editDivisionName(String old_divisionName, String new_divisionName) {

		System.out.println("editDivisionName start.");

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "UPDATE DIVISION SET 部署名=? WHERE 部署名=?;";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new_divisionName);//パラメータを後からセットする（SQL文中に直で書いてもエラーになる）
			pstmt.setString(2, old_divisionName);
			ri = pstmt.executeUpdate();//SQLを実行し、更新行数を格納(更新系と検索系でここのメソッド違うので注意)
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

	public int addDivisionName(String new_divisionName) {

		System.out.println("addDivisionName start.");

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			System.out.println("db connection start.");
			String sql = "INSERT INTO DIVISION (ID, 部署名) VALUES (SELECT COUNT(*) FROM DIVISION+1,?)";
			//String sql = "INSERT (ID, 部署名) VALUES (SELECT COUNT(*) FROM DIVISION+1,?)"; //異常系テスト用

			System.out.println("sql execute start.");
			pstmt = conn.prepareStatement(sql);//準備したSQLをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setString(1, new_divisionName);//sqlのパラメータをセット,番号は1スタート
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

	public int deleteDivisionName(String old_divisionName) {
		System.out.println("deleteDivisionName start.");
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			System.out.println("db connection start.");
			String sql = "DELETE FROM DIVISION WHERE 部署名=?";
			//String sql = "DELETE FROM DIVISION WHERE 部署=?";//異常系テスト用
			pstmt = conn.prepareStatement(sql);//準備したSQLをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setString(1, old_divisionName);//sqlのパラメータをセット,番号は1スタート
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

	public String getDivisionName(int divisionNumber) {
		System.out.println("getDivisionName start.");
		String divisionName = null;

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");

			String sql = "SELECT 部署名 FROM DIVISION WHERE ID=?;";
			pstmt = conn.prepareStatement(sql);//準備したSQLをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setInt(1, divisionNumber);//sqlのパラメータをセット
			rs = pstmt.executeQuery();//SQLを実行し、ResultSetインスタンスに結果を格納

			while (rs.next()) {
				divisionName = rs.getString("部署名");
				return divisionName;
			}

		}catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return null;
		} finally {//PrepareStatementインスタンス,ResultSetインスタンスのクローズ
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
					return null;
				}
			}
		}
		return divisionName;
	}

	public int getDivisionID(String divisionName) {
		System.out.println("getDivisionID start.");
		int divisionID = 0;

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");

			String sql = "SELECT ID FROM DIVISION WHERE 部署名=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, divisionName);//sqlのパラメータをセット
			rs = pstmt.executeQuery();//SQLを実行し、ResultSetインスタンスに結果を格納

			while (rs.next()) {
				divisionID = rs.getInt("ID");
				return divisionID;
			}

		} catch (SQLException | ClassNotFoundException e) {//①db接続時、SQL実行時に失敗した場合、②JDBCドライバが見つからなかった場合
			e.printStackTrace();
			return ri;
		} finally {//PrepareStatementインスタンス,ResultSetインスタンスのクローズ
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
					return 0;
				}
			}
		}
		return divisionID;

	}
}
