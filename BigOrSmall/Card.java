package BigOrSmall;

public class Card {
	protected String mark;
	protected int markToNumber;
	protected int number;

	//コンストラクタ
	public Card(String mark, int number) {
		this.setMark(mark);
		this.setNumber(number);
	}

	//setterメソッド：インスタンス化する際の引数を判断
	private void setMark(String mark) {
		if (mark.equals("スペード") || mark.equals("ハート") || mark.equals("ダイヤ") || mark.equals("クラブ")) {
			this.mark = mark;
		}
	}

	private void setNumber(int number) {
		if (number > 0 && number <= 13) {
			this.number = number;
		}
	}

	//getterメソッド
	public String getMark() {
		return mark;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return  mark + number;
	}

}
