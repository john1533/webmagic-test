package me.fourtween.webmagic.marksix;

public class Daily {
	private String date;//开奖时间
	private String id;//期数
	private String extName;//金多宝
	private String num1;//中奖号码
	private String num2;
	private String num3;
	private String num4;
	private String num5;
	private String num6;
	private String num7;
	
	private String totalAmount;
	private String amount1;//中奖金额
	private String amount2;
	private String amount3;
	

	private String count1;//中奖注数
	private String count2;
	private String count3;
	
	private String nextSerial;
	private String nextDate;
	private String tz;
	private String ext;
	private String expect;


	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	
	
	public String getNum1() {
		return num1;
	}
	public void setNum1(String num1) {
		this.num1 = num1;
	}
	public String getNum2() {
		return num2;
	}
	public void setNum2(String num2) {
		this.num2 = num2;
	}
	public String getNum3() {
		return num3;
	}
	public void setNum3(String num3) {
		this.num3 = num3;
	}
	public String getNum4() {
		return num4;
	}
	public void setNum4(String num4) {
		this.num4 = num4;
	}
	public String getNum5() {
		return num5;
	}
	public void setNum5(String num5) {
		this.num5 = num5;
	}
	public String getNum6() {
		return num6;
	}
	public void setNum6(String num6) {
		this.num6 = num6;
	}
	public String getNum7() {
		return num7;
	}
	public void setNum7(String num7) {
		this.num7 = num7;
	}
	
	
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAmount1() {
		return amount1;
	}
	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}
	public String getAmount2() {
		return amount2;
	}
	public void setAmount2(String amount2) {
		this.amount2 = amount2;
	}
	public String getAmount3() {
		return amount3;
	}
	public void setAmount3(String amount3) {
		this.amount3 = amount3;
	}
	
	public String getCount1() {
		return count1;
	}
	public void setCount1(String count1) {
		this.count1 = count1;
	}
	public String getCount2() {
		return count2;
	}
	public void setCount2(String count2) {
		this.count2 = count2;
	}
	public String getCount3() {
		return count3;
	}
	public void setCount3(String count3) {
		this.count3 = count3;
	}
	
	
	public String getNextSerial() {
		return nextSerial;
	}
	public void setNextSerial(String nextSerial) {
		this.nextSerial = nextSerial;
	}
	public String getNextDate() {
		return nextDate;
	}
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}
	public String getTz() {
		return tz;
	}
	public void setTz(String tz) {
		this.tz = tz;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "时间："+ date +"--期数："+ id +"--金多宝："+ extName 
				+"--号码1："+ num1  +"--号码2："+ num2 +"--号码3："+ num3 +"--号码4："+ num4 + "--号码5："+ num5+"--号码6："+ num6 + "--号码7："+ num7 + "--总金额：" +totalAmount
				+"--金额1："+ amount1  +"--金额2："+ amount2 +"--金额3："+ amount3
				+"--注数1："+ count1  +"--注数2："+ count2 +"--注数3："+ count3;
	}	
	
}
