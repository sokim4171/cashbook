package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CategoryService;
import com.gdu.cashbook.vo.Category;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//카테고리 리스트
	@GetMapping("categoryList")
	public String categoryList(HttpSession session,Category category,Model model) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		List<Category> list= categoryService.getCategoryList();
		model.addAttribute("list", list);
		
		return "categoryList";
	}
	
	//카테고리 추가
	@GetMapping("/addCategory")
	public String addCategory(HttpSession session) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		return "addCategory";
	}
	
	@PostMapping("/addCategory")
	public String addCategory(HttpSession session,Category category) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		categoryService.addCategory(category);
		return "redirect:/categoryList";
	}
	
	//카테고리 수정
	@GetMapping("/modifyCategory")
	public String modifyCategory(HttpSession session,Model model ,
			@RequestParam("categoryNo") int categoryNo) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		Category category=categoryService.selectCategoryOne(categoryNo);
		model.addAttribute("category", category);
		
		return "modifyCategory";
	}
	@PostMapping("/modifyCategory")
	public String modifyCategory(HttpSession session,Category category){
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		System.out.println(category);
		categoryService.modifyCategory(category);
		
		return "redirect:/categoryList";
	}
	
	
	
	//카테고리 삭제
	@GetMapping("/removeCategory")
	public String removeCategory(Category category) {
		categoryService.removeCategory(category);
		return "redirect:/categoryList";
	}
	
	
	
	
	
}
