package WarGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WarGame implements Serializable {

	private static final long serialVersionUID = 1844247791822861705L;

	Trump tp = new Trump();

	ArrayList<Card> PlayerList = tp.getPlayerList();
	ArrayList<Card> CPUList = tp.getCPUList();
	ArrayList<Object> SaveDataList = new ArrayList<Object>();

	int roundNumber = 0;//試合回数
	int cardsNumber = 0;//場に積まれたカードの枚数
	int playerCardsNumber;//あなたの持ち札
	int CPUCardsNumber;//CPUの持ち札
	int playerGetCardsNumber = 0; //あなたの獲得した札の数
	int CPUGetCardsNumber = 0;//CPUの獲得した札の数
	String input;
	Boolean continueOrNot = true;
	Boolean retry = true;
	boolean restartOrNot;
	boolean finalGame = false;

	Card CPUCard;
	Card playerCard;
	String winner;
	SaveGame sg;
	WarGame wg;
	SaveData sd = new SaveData();

	public boolean selectRestart(boolean restartOrNot, SaveGame sg) {
		Scanner scanner = new Scanner(System.in);
		if (restartOrNot) {
			while (true) {
				try {
					System.out.print("中断したゲームを再開しますか？[y/n]>");
					input = scanner.nextLine();
					if (input.equals("y")) {
						System.out.println("中断したゲームを再開します。");
						System.out.println("");
						retry = true;
						break;
					} else if (input.equals("n")) {
						retry = false;
						break;
					} else {
						System.out.println("yまたはnの文字を入力してください");
					}
				} catch (InputMismatchException e) {
					System.out.println("yまたはnの文字を入力してください");
				}
			}
		}
		return retry;
	}

	public boolean showCardsNumber(SaveGame sg, boolean retry, boolean restartOrNot) {//持ち札の表示
		if(restartOrNot = false) {//途中結果があるが、新規スタートする場合
			retry = false;
		}

		if (retry == false) {
			roundNumber = roundNumber + 1;//何回戦目かをカウント
		}
		playerCardsNumber = PlayerList.size();
		CPUCardsNumber = CPUList.size();
		Scanner scanner = new Scanner(System.in);

		if (CPUCardsNumber == 0 || playerCardsNumber == 0) {
			continueOrNot = false;
			showFinalResult(sg);//最終結果を表示
		} else {
			System.out.println("### 第" + roundNumber + "回戦 ###");
			System.out.println("場に積まれた札：" + cardsNumber + "枚");
			System.out.println("CPUの持ち札：" + CPUCardsNumber + "枚, " + "獲得した札：" + CPUGetCardsNumber + "枚");
			System.out.println("あなたの持ち札：" + playerCardsNumber + "枚, " + "獲得した札：" + playerGetCardsNumber + "枚");
			while (true) {
				System.out.print("札を切りますか？（d:札を切る, q:中断）>");
				try {
					input = scanner.nextLine();
					if (input.equals("d")) {
						playerCard = tp.selectCard(PlayerList);
						CPUCard = tp.selectCard(CPUList);
						cardsNumber = cardsNumber + 2;
						continueOrNot = true;
						break;//結果の表示へ
					} else if (input.equals("q")) {
						continueOrNot = false;
						System.out.println("ゲームを中断します");
						break;//終了処理へ
					} else {
						System.out.println("dまたはqの文字を入力してください");
					}
				} catch (InputMismatchException e) {
					System.out.println("dまたはqの文字を入力してください");
				}
			}
		}
		System.out.println("");
		return continueOrNot;
	}

	public void showResult() {//結果の表示
		System.out.println("CPUが切った札：[" + CPUCard + "]");
		System.out.println("あなたが切った札：[" + playerCard + "]");
		if (CPUCard.getNumber() > playerCard.getNumber() || CPUCard.getNumber() == 1) {
			//winner = "CPU";
			System.out.println("CPUが獲得しました！");
			CPUGetCardsNumber = CPUGetCardsNumber + cardsNumber;
			cardsNumber = 0;
		} else if (playerCard.getNumber() > CPUCard.getNumber() || playerCard.getNumber() == 1) {
			//winner = "あなた";
			System.out.println("あなたが獲得しました！");
			playerGetCardsNumber = playerGetCardsNumber + cardsNumber;
			cardsNumber = 0;
		} else if (playerCard.getNumber() == CPUCard.getNumber()) {
			System.out.println("引き分けです。");
			//積まれた札は次の試合へ持ち越す
		}
		System.out.println("");
	}

	public void showFinalResult(SaveGame sg) {
		boolean yourWin = true;
		
		System.out.println("### 最終結果 ###");
		System.out.println("CPUが獲得した札：" + CPUGetCardsNumber + "枚");
		System.out.println("あなたが獲得した札：" + playerGetCardsNumber + "枚");
		if (CPUGetCardsNumber > playerGetCardsNumber) {
			System.out.println("CPUが勝ちました");
			yourWin = false;
		} else {
			System.out.println("あなたが勝ちました、おめでとう！");
			yourWin = true;
		}
		sd.WriteGameResult(yourWin, playerGetCardsNumber);
		finalGame = true;
	}

}
