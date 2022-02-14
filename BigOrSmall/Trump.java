package BigOrSmall;

import java.util.ArrayList;
import java.util.Random;

public class Trump {

	private ArrayList<Card> TrumpList = new ArrayList<Card>(); //カードが格納されているリスト（デッキ）

	//Trumpクラスの明示的コンストラクタ
	public Trump() {
		this.createCards();//インスタンス化と同時に実行したい処理
	}

	//setter
	public void setTrumpList() {
		this.TrumpList = TrumpList;
	}

	//getter
	public ArrayList<Card> getTrumpList() {
		return TrumpList;
	}

	//4つのマークを格納するリストを作成
	ArrayList<String> mk = new ArrayList<String>() {
		{
			add("スペード");
			add("ハート");
			add("ダイヤ");
			add("クラブ");
		}
	};

	//52枚のカードのインスタンスを作る
	public void createCards() {
		for (int i = 0; i <= 12; i++) {
			for (int j = 0; j <= 3; j++) {
				Card card = new Card(mk.get(j), i + 1);
				TrumpList.add(card);
			}
		}
	}

	//ランダムにカードを1枚引く(カードが入っているリストのインデックス番号を選ぶ)
	public Card draw() {
		Random random = new Random();
		Card selectedCard;
		int index = random.nextInt(TrumpList.size());//TrumpListの個数の範囲で、ランダムに選択されたインデックス番号
		selectedCard = TrumpList.remove(index);//選んだインデックス番号をCard型変数に格納すると同時にリストから削除する

		return selectedCard;
	}


	//カードデッキを初期状態に戻す(削除して再度作成)
	public void initialization() {
		TrumpList.clear();
		createCards();
	}
}
