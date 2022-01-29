package model;

import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommonLogic;

public class Employee implements CommonLogic{

	private int empID;
	private String empName;
	private int age;
	private String sex;
	private int picID;
	private Blob pic;//画像データ
	private String postNumber;
	private String pref;
	private String address;
	private int divisionID;
	private String enteredDate;
	private String quittingDate;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

	public Employee() {//引数なしコンストラクタ
			super();
	}

	public Employee(int empID, String empName) {//searchEmployeeで使用
			this.empID = empID;
			this.empName = empName;
	}

	public Employee(int empID, String empName, int age, String sex, int picID, String postNumber, String pref, String address, int divisionID, String enteredDate, String quittingDate) {
		//社員ID,名前,年齢,性別,写真ID,郵便番号,都道府県,住所,部署ID
		this.empID = empID;
		this.empName = empName;
		this.age = age;
		this.sex = sex;
		this.picID = picID;
		this.postNumber = postNumber;
		this.pref = pref;
		this.address = address;
		this.divisionID = divisionID;
		this.enteredDate = enteredDate;
		this.quittingDate = quittingDate;
	}

	public Employee(int empID, String empName, int age, String sex, Blob pic, String postNumber, String pref, String address, int divisionID, String enteredDate, String quittingDate) {
		//社員ID,名前,年齢,性別,証明写真データ,郵便番号,都道府県,住所,部署ID
		//引数ありコンストラクタ、証明写真データあり
		this.empID = empID;
		this.empName = empName;
		this.age = age;
		this.sex = sex;
		this.pic = pic;
		this.postNumber = postNumber;
		this.pref = pref;
		this.address = address;
		this.divisionID = divisionID;
		this.enteredDate = enteredDate;
		this.quittingDate = quittingDate;
	}


	//getter
	public String getEnteredDate() {
		return enteredDate;
	}

	public String getQuittingDate() {
		return quittingDate;
	}

	public int getEmpID() {
		return empID;
	}

	public String getEmpName() {
		return empName;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public int getPicID() {
		return picID;
	}

	public String getPostNumber() {
		return postNumber;
	}

	public String getPref() {
		return pref;
	}

	public String getAddress() {
		return address;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public Blob getPic() {
		return pic;
	}
}
