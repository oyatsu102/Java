package WarGame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
//シリアライズ化するクラスはSerializableインターフェースを実装する
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {
	int roundNumber;
	int cardsNumber;
	int CPUCardsNumber;
	int CPUGetCardsNumber;
	int playerCardsNumber;
	int playerGetCardsNumber;

	int playNumber;
	int winNumber;
	private static final String OUTPUT_FILE = "/Users/skpoyo8/Desktop/CodeCamp/game_result.csv";

	List<String> List_GameResult = new ArrayList<String>();//既にゲーム結果の記録があった場合に、中身を読み込んで配列に入れる。

	private static final long serialVersionUID = 1L;

	public void WriteGameResult(boolean yourWin, int max_getCardsNumber) {//ゲームが終わるごとに、プレイヤーの勝利回数、プレイヤーの最大獲得カード枚数を記録(更新)していく。
		File file = new File(OUTPUT_FILE);

		if (file.exists()) {//結果のファイルが既に存在した場合、読み込み、書き込むデータの調整を行う
			//⭐︎ファイルがある＝一度は書き込みがされているとする。（項目名だけの場合は考えない。）
			try (//クローズすべきリソースを書く
					BufferedReader in = new BufferedReader(new FileReader(OUTPUT_FILE));) {
				while (true) {
					String line = in.readLine();//すでにあるファイルの読み込み
					if (line == null) {//.readLineメソッド実行するたびに次を読み込むので、whileの条件にすると、変数に入れる操作ができない
						break;
					}
					for (int i = 0; i < 3; i++) {//１行ずつなので、要素数は３
						List_GameResult.add((line.split(",")[i]).trim());//取り出した値をカンマで区切り、前後空白を削除してリストに入れる
					}
				}
				playNumber = Integer.parseInt(List_GameResult.get(3)) + 1;//ゲーム回数を増やす

				if (yourWin) {//勝利回数：今回勝利した場合は＋1、そうでなければ既存の値を使用
					winNumber = Integer.parseInt(List_GameResult.get(4)) + 1;
				} else {
					winNumber = Integer.parseInt(List_GameResult.get(4));
				}
				if (yourWin) {//最大カード獲得枚数：勝利した時かつ、既存の値より大きい場合、上書きする
					if (max_getCardsNumber >= Integer.parseInt(List_GameResult.get(5))) {
						//max_getCardsNumberの値はそのまま使用する
					} else {
						max_getCardsNumber = Integer.parseInt(List_GameResult.get(5));
					}
				} else {//負けた場合は、max_getCardsNumberに、既存の値をそのまま入れる
					max_getCardsNumber = Integer.parseInt(List_GameResult.get(5));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//更新した値で書き込み
			try (
					FileWriter fw = new FileWriter(OUTPUT_FILE, false); //falseを渡すと上書きされる
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);) {
				out.print("ゲーム回数,勝利回数,最大獲得カード枚数" + "\r\n");
				out.print(playNumber + ", " + winNumber + ", " + max_getCardsNumber);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {//ファイルを新規作成する場合
			if (yourWin) {
				winNumber = 1;
			} else {
				winNumber = 0;
				max_getCardsNumber = 0;
			}
			try (
					FileWriter fw = new FileWriter(OUTPUT_FILE, true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);) {
				out.print("ゲーム回数,勝利回数,最大獲得カード枚数" + "\r\n");
				out.print(1 + ", " + winNumber + ", " + max_getCardsNumber);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}