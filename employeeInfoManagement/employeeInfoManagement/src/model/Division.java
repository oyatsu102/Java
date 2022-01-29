package model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommonLogic;

public class Division implements CommonLogic{

	private int divisionID;
	private String divisionName;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	public Division() {//引数なしコンストラクタ
		super();
	}
	public Division(int divisionID, String divisionName) {//引数ありコンストラクタ
		this.divisionID = divisionID;
		this.divisionName = divisionName;
	}

	//getter
	public int getDivisionID() {
		return divisionID;
	}
	public String getDivisionName() {
		return divisionName;
	}


}
