package WarGame;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/*インスタンスの状態(オブジェクト)をファイルに書き込み、保存する。
バイナリファイルに書き込み(シリアライズ)、このファイルをデシリアライズ化してオブジェクトにする。*/

public class SaveGame implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 912725257736758369L;

	WarGame wg;//ここでインスタンス化するとオーバーフローする

	private static final String SaveGameFile = "/Users/skpoyo8/Desktop/CodeCamp/SaveGame.dat";

	public void Save(WarGame wg) {//ゲーム結果を保存
		try (//シリアライズ形式でファイルを書き込み
			ObjectOutputStream os = new ObjectOutputStream(Files.newOutputStream(Paths.get(SaveGameFile)));) {
			os.writeObject(wg);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public WarGame Restart() {//保存したゲーム結果からスタート
		try (
			ObjectInputStream is = new ObjectInputStream(Files.newInputStream(Paths.get(SaveGameFile)));) {
			wg = (WarGame)is.readObject();//ファイルから読み込んだオブジェクトをJavaオブジェクトに出力している
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return wg;
	}

	public boolean restartOrNot() {//中断結果のファイルが存在するかどうか
		File file = new File(SaveGameFile);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteFile() {
		File file = new File(SaveGameFile);
		file.delete();//　試合を最後まで終了した場合、結果の記録ファイルを削除する
	}

}
