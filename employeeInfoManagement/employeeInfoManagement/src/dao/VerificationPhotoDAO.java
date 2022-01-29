package dao;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

public class VerificationPhotoDAO {
	Connection conn = null;
	ResultSet rs = null;
	int ri = 0;//更新系sqlの更新数をセットする
	PreparedStatement pstmt = null;

	public int addPic(int picID, InputStream inputStream) {//証明写真テーブルに画像ファイルを新規登録、IDを連番でつける
		System.out.println("addPic start.");
		try {
			Class.forName("org.h2.Driver");//JDBCドライバを読み込み
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");//データベースへ接続
			String sql = "INSERT INTO VerificationPhoto(写真ID, 写真データ) VALUES (?,?) ";
			pstmt = conn.prepareStatement(sql);//sqlをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setInt(1, picID);
			pstmt.setBlob(2, inputStream);
			ri = pstmt.executeUpdate();//sql実行
			return ri;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return ri;
		} finally {//PreapareStatementインスタンス、ResultSetインスタンスのクローズ
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

	public BufferedImage getPic(int picID) {//画像IDにひもづく、画像データを取得（社員情報編集画面で表示するため）
		System.out.println("Getpic start.");
		Blob pic = null;

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "SELECT 写真データ FROM VerificationPhoto WHERE 写真ID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, picID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pic = rs.getBlob("写真データ");
			}
			InputStream in = pic.getBinaryStream();
			BufferedImage bimg = ImageIO.read(in);
			return bimg;

		} catch (SQLException | ClassNotFoundException | IOException | NullPointerException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
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
	}

	public int updatePic(int picID, InputStream inputStream) {//画像データの更新
		System.out.println("updatePic start.");

		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "UPDATE VerificationPhoto SET 写真データ=? WHERE 写真ID=?;";
			pstmt = conn.prepareStatement(sql);//sqlをdbに届けるPrepareStatementインスタンスを取得
			pstmt.setBlob(1, inputStream);
			pstmt.setInt(2, picID);
			ri = pstmt.executeUpdate();//sql実行
			return ri;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return ri;
		} finally {//PreapareStatementインスタンス、ResultSetインスタンスのクローズ
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

	public int deletePic(int picID) {//画像データの削除
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:/Users/skpoyo8/CodeCamp/test", "sa", "P@ssword");
			String sql = "DELETE FROM VerificationPhoto WHERE 写真ID=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, picID);
			ri = pstmt.executeUpdate();
			return ri;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return ri;
		} finally {//PreapareStatementインスタンス、ResultSetインスタンスのクローズ
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
