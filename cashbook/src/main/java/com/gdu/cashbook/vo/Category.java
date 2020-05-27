package com.gdu.cashbook.vo;

public class Category {
	private String categoryName;
	private int categoryNo;

	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", categoryNo=" + categoryNo + "]";
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
}
