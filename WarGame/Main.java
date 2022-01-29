package WarGame;

public class Main {

	public static void main(String[] args) {
		boolean continueOrNot = true;
		boolean retry = true;

		WarGame wg = new WarGame();
		WarGame wg_restart;
		SaveGame sg = new SaveGame();
		int gameCount = 0;

		boolean restartOrNot = sg.restartOrNot();//中断結果ファイルの有無を確認
		if(restartOrNot) {
			retry = wg.selectRestart(restartOrNot, sg);//途中から始めるかどうか選択する
			if (retry == true) {//途中ファイルがあり、途中からスタート
				wg_restart = sg.Restart();
				wg = wg_restart;//保存されているゲーム結果のオブジェクト
			}else {//途中ファイルはあるが、新規スタートする
				retry = false;
			}
		}else {//途中ファイルがない場合
				retry = false;
			}

		while (continueOrNot) {
			gameCount = gameCount + 1;

			if(gameCount == 1 && retry==true) {//初回かつ途中開始の場合はretryにfalseを入れてカウントアップさせる
				retry = true;
			}else {
				retry = false;
			}

			continueOrNot = wg.showCardsNumber(sg,retry,restartOrNot);
			if (continueOrNot == true) {
				wg.showResult();
			} else {
				sg.Save(wg);//データのセーブ

				if(wg.finalGame == true) {
					sg.deleteFile();
				}
				break;
			}
		}
	}
}

