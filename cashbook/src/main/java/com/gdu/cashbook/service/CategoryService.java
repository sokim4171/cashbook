package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	//카테고리 추가
	public int addCategory(Category category) {
		return categoryMapper.insertCategory(category);
	}
	
	// 카테고리 리스트 출력 -> 가계부 드롭다운
	public List<Category> getCategoryList() {
		List<Category> categorylist = categoryMapper.selectCashCategoryList();
		return categorylist;
	}
	
	//카테고리 수정
	public int modifyCategory(Category category) {
		return categoryMapper.updateCategory(category);
	}
	
	//하나만 가꼬오기
	public Category selectCategoryOne(int categoryNo) {
		return categoryMapper.selectCategoryOne(categoryNo);
	}
	
	//카테고리 삭제
	public int removeCategory(Category category) {
		return categoryMapper.deleteCategory(category);
	}
}
