package WarGame;

import java.io.Serializable;

public class Card implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -1001778954571301827L;
	private String mark;
	private int number;

	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public int getNumber() {
		return number;
	}
	@Override
	public String toString() {
		return mark + number;
	}
	public void setNumber(int number) {
		this.number = number;
	}


	public Card(String mark, int number) {//Cardのコンストラクタ
		this.setMark(mark);
		this.setNumber(number);
	}

}
