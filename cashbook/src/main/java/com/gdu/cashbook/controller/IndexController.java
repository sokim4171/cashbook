package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class IndexController {
	@GetMapping({"/","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session) {
		if (session.getAttribute("loginMember")==null) { //로그인 되어있지 않으면 로그인 폼으로 리다이렉트 
			return "redirect:/login";
		} else {
			return "home";
		}
	}
}
