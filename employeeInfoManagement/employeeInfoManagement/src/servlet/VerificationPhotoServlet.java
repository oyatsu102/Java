package servlet;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VerificationPhotoDAO;

@WebServlet("/VerificationPhoto")
@MultipartConfig(location = "/tmp", maxFileSize = 1048576) //multipart/form-Data形式のリクエストに対応。

public class VerificationPhotoServlet extends HttpServlet {

	//dbから取得した画像を表示させるためのサーブレット
	private static final long serialVersionUID = 1L;

	public VerificationPhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VerificationPhotoServlet start.");
		//boolean exist_pic = false;//登録済みの写真の有無

		try {//dbに登録済みの画像がある場合、EditEmployee.jspで表示させる
			int picID = Integer.parseInt(request.getParameter("picID"));
			VerificationPhotoDAO verDAO = new VerificationPhotoDAO();
			BufferedImage image = verDAO.getPic(picID);

			if (image != null) {
				//exist_pic = true;
				response.setContentType("image/png");
				System.out.println("picをgetしました");
				try (
					OutputStream out = response.getOutputStream();//jspにバイナリデータを返すためのストリーム
				) {
					ImageIO.write(image, "png", out);
					out.flush();//書き込み＝表示
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("写真は登録されているが、空、もしくは壊れている。");
			}
		} catch (NullPointerException e) {
			System.out.println("写真が登録されていません。");
		}

	}
}
