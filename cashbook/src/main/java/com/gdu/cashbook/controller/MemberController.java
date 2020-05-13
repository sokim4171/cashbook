package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login") 
	public String Login() {
		return "Login";
	}
	
	@PostMapping("/login")
	public String Login(LoginMember loginMember,HttpSession session) {
		System.out.println(loginMember.toString());
		LoginMember returnLoginMember=memberService.login(loginMember);
		System.out.println(returnLoginMember);
		if(returnLoginMember==null) { //login fail
			return "redirect:/login";
		} else { //login success
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index";
	}
	
	@GetMapping("/addMember")
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) { 
		System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/addMember";
	}
	
}
