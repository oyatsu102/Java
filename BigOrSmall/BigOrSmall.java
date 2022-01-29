package BigOrSmall;

import java.util.InputMismatchException;

public class BigOrSmall {

	String continueOrNot = "Yes";//ゲームを続けるかどうか
	//boolean judgeInputChip = true;//BETされるチップの数を判定
	int inputChip = 0;//BETするチップ
	int inputBigOrSmall = 0;//BigかSmallの選択
	int inputYesOrNo = 0;//YesかNoの選択
	int mark = 0;//比較のためにマークを数値化した値
	int rst = 0;//比較の結果（0か1）
	boolean winOrLose = true;//winかloseかで以後の動作を分ける
	int increasedChip = 0;//勝利してゲットしたチップの数

	Trump tp1 = new Trump();
	Chip cp1 = new Chip(0, 10);

	Card selectedCardA;//1回目に引くカード
	Card selectedCardB;//2回目に引くカード

	public Card startGame() {
		tp1.getTrumpList();
		selectedCardA = tp1.draw();

		System.out.println("*******チップ枚数とカード******");
		System.out.println("現在のカード：" + selectedCardA);//toStringメソッドを呼ぶ、チップも同様に
		cp1.displayChip();
		System.out.println("****************************");
		System.out.println();
		return selectedCardA;//ここでreturnする場合としない場合の違い②
	}

	public void inputChip() {
		//無限ループにして（条件をtrueだけにする）条件が合致した時だけbreak
		while (true) {
			System.out.println("■BET枚数選択");
			System.out.print("BETするチップ数を入力してください（最低1～最大20）> ");
			try {
				inputChip = new java.util.Scanner(System.in).nextInt();
				System.out.println();
				if (!(inputChip >= 1 && inputChip <= 20)) {
					System.out.println("チップポイントは半角数字の1～20を入力してください");
					System.out.println();
				} else if (inputChip > cp1.amtChip) {
					System.out.println("BETするチップポイントは総計のチップポイント以下で入力してください");
					System.out.println();
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.print("チップポイントは半角数字の1～20を入力してください");
			}
			System.out.println();
		}
	}

	public void selectBigOrSmall(Card selectedCardA) {
		while (true) {
			System.out.println("■Big or Small選択");
			System.out.println("現在のカード：" + selectedCardA);
			System.out.print("[Big or Small]: 0:Big 1:Small > ");
			try {
				inputBigOrSmall = new java.util.Scanner(System.in).nextInt();
				if (!(inputBigOrSmall == 0 || inputBigOrSmall == 1)) {
					System.out.println("半角数字の0あるいは1のみ入力してください");
					System.out.println();
				} else {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("半角数字の0あるいは1のみ入力してください");
			}
		}
		System.out.println();
	}

	public Card continueGame() {//2枚目のカードを引く
		Card selectedCardB = tp1.draw();
		return selectedCardB;
	}

	//マークに対応して数字を決める（大きさを比較する為）
	public void comparisonMark(Card selectedCard) {
		if (selectedCard.getMark().equals("スペード")) {
			mark = 4;
		} else if (selectedCard.getMark().equals("ハート")) {
			mark = 3;
		} else if (selectedCard.getMark().equals("ダイヤ")) {
			mark = 2;
		} else if (selectedCard.getMark().equals("クラブ")) {
			mark = 1;
		}
	}

	public void comparisonCard(Card selectedCardA, Card selectedCardB) {
		int markA = selectedCardA.markToNumber;
		int markB = selectedCardB.markToNumber;

		if (selectedCardA.getNumber() > selectedCardB.getNumber()) {
			rst = 1;// "Small"
		} else if (selectedCardA.getNumber() < selectedCardB.getNumber()) {
			rst = 0;// "Big"
		} else if (selectedCardA.getNumber() == selectedCardB.getNumber()) {
			if (markA > markB) {
				rst = 1;
			} else if (markA < markB) {
				rst = 0;
			}
		}
	}

	public String checkGameResult(Card selectedCardA, Card selectedCardB) {
		String cmp;

		System.out.println("********Big or Small********");
		System.out.println("BET数：" + inputChip);
		System.out.println("あなたの選択：" + inputBigOrSmall);
		System.out.println("現在のカード：" + selectedCardA);
		System.out.println("引いたカード：" + selectedCardB);

		System.out.println("*****************************");
		if (rst == inputBigOrSmall) {//勝ち、チップを増やす
			if (inputBigOrSmall == 0) {
				cmp = "Big";
			} else {
				cmp = "small";
			}
			System.out.println(
					selectedCardB.getMark() + selectedCardB.getNumber() + "は" + selectedCardA.getMark()
							+ selectedCardA.getNumber() + "より" + cmp);
			System.out.println("Win!!");
			inputChip = inputChip * 2;
			System.out.println("チップ" + inputChip + "枚を獲得しました");
			System.out.println();
			cp1.addChip(inputChip);
			while (true) {
				System.out.print("[獲得したチップ" + inputChip + "枚でBig or Smallを続けますか？]: 0:Yes 1:No > ");
				try {
					inputYesOrNo = new java.util.Scanner(System.in).nextInt();
					if (inputYesOrNo == 0) {
						continueOrNot = "WinAndYes";
						break;
					} else if (inputYesOrNo == 1) {
						while (true) {
							System.out.print("[ゲームを続けますか？]: 0:Yes 1:No > ");
							try {
								inputYesOrNo = new java.util.Scanner(System.in).nextInt();
								if (inputYesOrNo == 0) {
									continueOrNot = "LoseAndYes";
									break;
								} else if (inputYesOrNo == 1) {
									continueOrNot = "No";
									break;
								} else {
									System.out.println("半角数字の0あるいは1のみ入力してください");
								}
							} catch (InputMismatchException e) {
								System.out.println("半角数字の0あるいは1のみ入力してください");
							}
						}break;
					}
				} catch (InputMismatchException e) {
					System.out.println("半角数字の0あるいは1のみ入力してください");
				}
			}
			System.out.println();
		} else {//負け、チップを減らす
			if (inputBigOrSmall == 0) {
				cmp = "small";
			} else {
				cmp = "Big";
			}
			System.out.println(
					selectedCardB.getMark() + selectedCardB.getNumber() + "は" + selectedCardA.getMark()
							+ selectedCardA.getNumber() + "より" + cmp);

			System.out.println("Lose...");
			System.out.println();
			cp1.subChip(inputChip);//チップの減算処理
			System.out.println("********現在のチップ枚数********");
			System.out.println("現在のカード：" + selectedCardA);
			cp1.displayChip();
			System.out.println("******************************");

			if (cp1.amtChip == 0) {
				continueOrNot = "No";
				System.out.println("END");
				System.exit(0);//プログラムの終了
			}

			while (true) {
				System.out.print("[ゲームを続けますか？]: 0:Yes 1:No > ");
				try {
					inputYesOrNo = new java.util.Scanner(System.in).nextInt();
					if (inputYesOrNo == 0) {
						continueOrNot = "LoseAndYes";
						break;
					} else if (inputYesOrNo == 1) {
						continueOrNot = "No";
						break;
					} else {
						System.out.println("半角数字の0あるいは1のみ入力してください");
					}
				} catch (InputMismatchException e) {
					System.out.println("半角数字の0あるいは1のみ入力してください");
				}
			}
			System.out.println();
		}
		return continueOrNot;
	}

	public void continueOrNot() {
		if (continueOrNot.equals("No")) {
			System.out.println("END");//終了
			System.exit(0);//プログラムの終了
		} else if (continueOrNot.equals("LoseAndYes")) {
			tp1.initialization();//チップはそのままにカードを初期化
		}
	}

}
