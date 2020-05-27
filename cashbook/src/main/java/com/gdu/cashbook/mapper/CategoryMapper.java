package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Category;
@Mapper
public interface CategoryMapper {
	
	//카테고리 추가
	public int insertCategory(Category category);
	
	//가계부 내용 추가 내에 카테고리 드롭다운용
	public List<Category> selectCashCategoryList();
	
	//카테고리 수정
	public int updateCategory(Category category);
	
	//하나만 가꼬오기
	public Category selectCategoryOne(int categoryNo);
	
	//카테고리 삭제
	public int deleteCategory(Category category);
	
}
