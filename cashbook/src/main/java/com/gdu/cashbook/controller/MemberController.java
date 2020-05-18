package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	//비번찾기용
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember")!=null){ 
			return "redirect:/index";
		}
		return "findMemberPw";
	}
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session,Model model,Member member) {
		if(session.getAttribute("loginMember")!=null){ 
			return "redirect:/index";
		}
		int row =memberService.getMemberPw(member);
		String msg="아이디와 메일을 확인하세요";
		if(row==1) {
			msg="변경한 비밀번호를 입력하였습니다.";
		} 
		model.addAttribute("msg", msg);
		return "redirect:/index";
	}
	
	
	//아이디 찾기용
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember")!=null){ 
			return "redirect:/index";
		}
		return "findMemberId";
	}
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session,Model model,Member member) {
		if(session.getAttribute("loginMember")!=null){ 
			return "redirect:/index";
		}
		String memberIdPart=memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart+"<--memberIdPart");
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}
	
	//회원 정보 수정 뷰
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session,Member member,Model model) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		Member member1=memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member1);
		model.addAttribute("member",member1);
		return "modifyMember";
	}
	//회원 정보 
	@PostMapping("/modifyMember")
	public String modifyMember(LoginMember loginMember,HttpSession session,Member member1) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		memberService.modifyMember(member1);
		System.out.println(member1 + "<--member.MemberController");
		
		return "redirect:/memberInfo";
		
		
	}
	
	//회원 탈퇴 뷰
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}
		return "removeMember"; // input type="password" name="memberPw"
	}
	//회원탈퇴 액셕
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/index";
		}

		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		memberService.removeMember(loginMember);
		session.invalidate();

		return "redirect:/index";
	}
	
	
	//memberInfo 뷰
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		Member member=memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member",member);
		return "memberInfo";
	}
	
	//중복 체크
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
	
	
	
	
	//회원가입 폼
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		//로그인 때
		if(session.getAttribute("loginMember")!=null){
			return "redirect:/index";
		}
		return "addMember";
	}
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(MemberForm memberForm,HttpSession session) { 
		//로그인 때
		if(session.getAttribute("loginMember")!=null){ //이미 로그인이 되어있으면  addMemer가 할 필요가 없으니 인덱스로 돌아가기 
			return "redirect:/index";
		}
		System.out.println(memberForm.toString());
		
		if(memberForm.getMemberPic() !=null) {
			//파일은 png, jpg, gif만 업로드 가능
			if(!memberForm.getMemberPic().getContentType().equals("image/png") && 
				!memberForm.getMemberPic().getContentType().equals("image/jpeg") &&
				!memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember";
			}
		}
		
		memberService.addMember(memberForm);
		// service : memberForm->member+폴더에 파일 저장 
		return "redirect:/index";
	}

	
	
	
	
	
	//로그인 화면
	@GetMapping("/login") 
	public String Login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!=null){
			return "redirect:/index";
		}
		return "login";
	}
	//로그인 액션
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
			return "redirect:/home";
		}
	}
	//로그아웃 기능
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		//로그인이 아닐때
		if(session.getAttribute("loginMember")==null){ // 널일때는 비어있을때니깐 로그인아닐때 
			return "redirect:/index";
		}
		session.invalidate();
		return "redirect:/index";
	}
	
	
	
	

	
	
	
	
	
}
