package com.gdu.cashbook.vo;

public class DayAndSum {
	private int day;
	private int price;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "DayAndSum [day=" + day + ", price=" + price + "]";
	}
	
}
