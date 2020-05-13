package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(Model model,@RequestParam("memberIdCheck") String memberIdCheck,HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!=null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		String memberId=memberService.memberIdCheck(memberIdCheck);
		System.out.println(memberId);
		if(memberId!=null) { //멤버아이디가 있으면 아이디를 사용 불가 
			//아이디를 사용 할 수 없습니다
			System.out.println("아이디를 사용할 수 없습니다");
			model.addAttribute("msg", "사용중인 아이디 입니다");
		} else { //멤버아이디에 반환되는 값이 없으면 아이디 사용가능 
			//아이디를 사용할 수 있습니다 
			System.out.println("아이디를 사용할 수 있습니다 ");
			model.addAttribute("confirmMemberId", memberIdCheck);
		}
		return "addMember";
	}
	
	
	
	@GetMapping("/login") 
	public String Login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!=null){
			return "redirect:/index";
		}
		return "login";
	}
	@PostMapping("/login")
	public String Login(LoginMember loginMember,HttpSession session,Model model) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!=null){
			return "redirect:/index";
		}
		System.out.println(loginMember.toString());
		LoginMember returnLoginMember=memberService.login(loginMember);
		System.out.println(returnLoginMember);
		if(returnLoginMember==null) { //login 실패
			model.addAttribute("msg","아이디와 비밀번호를 확인하세요");
			return "redirect:/login";
		} else { //login 성공
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/index";
		}
	}
	
	
	
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		//로그인이 아닐때
		if(session.getAttribute("loginMember")==null){ // 널일때는 비어있을때니깐 로그인아닐때 
			return "redirect:/index";
		}
		session.invalidate();
		return "redirect:/index";
	}
	
	
	
	
	
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		//로그인 때
		if(session.getAttribute("loginMember")!=null){
			return "redirect:/index";
		}
		return "addMember";
	}
	@PostMapping("/addMember")
	public String addMember(Member member,HttpSession session) { 
		//로그인 때
		if(session.getAttribute("loginMember")!=null){ //이미 로그인이 되어있으면  addMemer가 할 필요가 없으니 인덱스로 돌아가기 
			return "redirect:/index";
		}
		System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/addMember";
	}

	
	
	
	
	
}
