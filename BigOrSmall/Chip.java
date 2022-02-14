package BigOrSmall;

public class Chip {
	private int smallerChip;
	private int biggerChip;
	public int amtChip;

	public void addChip (int chip) {
		amtChip = amtChip - chip/2;//BETした分をマイナス
		amtChip += chip;//BETした分の倍のchipを加算していく
		biggerChip = amtChip / 10;
		smallerChip = amtChip % 10;
	}
	public void subChip (int chip) {
		amtChip -= chip;//chip減
		biggerChip = amtChip / 10;
		smallerChip = amtChip % 10;
	}

	//コンストラクタ
	public Chip() {//引数なしver.
		amtChip = 0;
		biggerChip = 0;
		smallerChip = 0;
	}

	public Chip(int smallerChip, int biggerChip) {//引数ありver.
		this.smallerChip = smallerChip;
		this.biggerChip = biggerChip;
		amtChip = biggerChip * 10 + smallerChip;
	}

	public void displayChip() {
		System.out.println("保有点" + amtChip + "[10]：" + biggerChip + "枚, [1]：" + smallerChip + "枚");
	}


}
