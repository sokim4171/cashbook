package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.Memberid;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired 
	private MemberidMapper memberidMapper;
	@Autowired
	private JavaMailSender javaMailSender;
	
	//비밀번호 변경
	public int getMemberPw(Member member) {
		UUID uuid=UUID.randomUUID();
		String memberPw=uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row= memberMapper.updateMemberPw(member);
		if(row==1) {
			System.out.println(memberPw+"<--updateMemberPw");
			//메일로 랜덤 패스워드 전송
			//메일 객체 
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	        simpleMailMessage.setTo(member.getMemberEmail());
	        System.out.println(member.getMemberEmail());
	        simpleMailMessage.setFrom("sokim4171@gmail.com");
	        simpleMailMessage.setSubject("cashbook 비밀번호 찾기 메일");
	        simpleMailMessage.setText("변경된 비밀번호는" + memberPw + "입니다");
	         
	        javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	
	//아이디 찾기용
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	
	//회원 정보 수정
	public int modifyMember(LoginMember loginMember) {
		return memberMapper.updateMember(loginMember);
	}
	

	
	//회원 정보 탈퇴
	public void removeMember(LoginMember loginMember) {
		// 1.
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);

		// 2. 
		memberMapper.deleteMember(loginMember);
	}
	
	//memberInfo
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	//중복 체크
	public String memberIdCheck(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); //null, member_id 가 리턴 둘중하나
	}
	
	//로그인용
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입
	public int addMember(MemberForm memberForm) {
		Member member=new Member();
		//memberForm -> member
		//member -> 디스크에 물리적으로 저장
		return memberMapper.insertMember(member);
	}
}
