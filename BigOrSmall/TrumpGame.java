package BigOrSmall;

public class TrumpGame {
	public static void main(String[] args) {

		String continueOrNot = "LoseAndYes";
		//int increasedChip = 0;
		BigOrSmall bos = new BigOrSmall();
		Card selectedCardA;//1枚目に引いたカード
		Card selectedCardB = bos.continueGame();//2枚目のカードを引く(初回)

		while (true) {
			if (continueOrNot.equals("LoseAndYes")) {
				selectedCardA = bos.startGame();//1枚目のカードを引く
				//bos.startGame();//現在のカードを表示
				bos.inputChip();//BET枚数選択
				selectedCardB = bos.continueGame();//2枚目のカードを引く
				bos.selectBigOrSmall(selectedCardA);//BigOrSmall選択
				bos.comparisonMark(selectedCardA);
				bos.comparisonMark(selectedCardB);
				bos.comparisonCard(selectedCardA, selectedCardB);//比較するメソッドとして一つ呼び出す
				continueOrNot = bos.checkGameResult(selectedCardA, selectedCardB);//比較し、勝ち負け判定する。その後どうするかの結果(ContinueOrNot)を返す。
				bos.continueOrNot();

			}else if (continueOrNot.equals("WinAndYes")) {
				selectedCardA = selectedCardB;//比較するカードをずらす
				selectedCardB = bos.continueGame();//新しく2枚目のカードを引く
				bos.selectBigOrSmall(selectedCardA);//BigOrSmall選択
				bos.comparisonMark(selectedCardA);
				bos.comparisonMark(selectedCardB);
				bos.comparisonCard(selectedCardA, selectedCardB);//比較するメソッドとして一つ呼び出す
				continueOrNot = bos.checkGameResult(selectedCardA, selectedCardB);//比較し、勝ち負け判定する。その後どうするかの結果(ContinueOrNot)を返す。
				bos.continueOrNot();

			} else if (continueOrNot.equals("No")) {
				break;
			} else {
				break;
			}
	}

}
}