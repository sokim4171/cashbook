package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Category;
@Mapper
public interface CategoryMapper {
	//가계부 내용 추가 내에 카테고리 드롭다운용
	public List<Category> selectCashCategoryList();
}
