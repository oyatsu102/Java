package WarGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Trump implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -7100853611278325675L;

	public Trump() {
		this.createCards();//インスタンス化と同時に実行したい処理、中に書かないと中身がからのまま
		this.draw();
	}

	private ArrayList<Card> CardList = new ArrayList<Card>();//全26枚のカードが格納されたリスト
	private ArrayList<Card> PlayerList = new ArrayList<Card>();//プレイヤーの持ち札のリスト
	private ArrayList<Card> CPUList = new ArrayList<Card>();//CPUの持ち札のリスト

	int index;

	public ArrayList<Card> getCardList() {
		return CardList;
	}

	public void setCardList(ArrayList<Card> cardList) {
		CardList = cardList;
	}

	public ArrayList<Card> getPlayerList() {
		return PlayerList;
	}

	public void setPlayerList(ArrayList<Card> playerList) {
		PlayerList = playerList;
	}

	public ArrayList<Card> getCPUList() {
		return CPUList;
	}

	public void setCPUList(ArrayList<Card> cPUList) {
		CPUList = cPUList;
	}

	ArrayList<String> mk = new ArrayList<String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 5197041044528392078L;

		{
			add("スペード");
			add("ダイヤ");
		}
	};

	public void createCards() {
		Card card;
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 2; j++) {
				card = new Card(mk.get(j), i + 1);
				CardList.add(card);
			}
		}
	}

	public void draw() {//各プレイヤーにカードをランダムに配る（交互に）
		//ランダムな数を作ってCardListのindexとして使用する。
		Random random = new Random();
		for (int i = 0; i < 26; i++) {
			index = random.nextInt(CardList.size());
			if (i < 13) {
				PlayerList.add(CardList.get(index));
			} else {
				CPUList.add(CardList.get(index));
			}
			CardList.remove(index);//いずれかのリストに追加したCardListのインデックスを削除する。
		}
	}
	public Card selectCard(ArrayList<Card> CardList) {//引数に渡したリストからランダムにIndexを選び、Cardを選択する。
		Random random = new Random();
		Card selectedCard;
		index = random.nextInt(CardList.size());
		selectedCard = CardList.get(index);
		CardList.remove(index);
		return selectedCard;
	}
}
