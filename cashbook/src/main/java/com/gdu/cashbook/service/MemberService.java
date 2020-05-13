package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	
	//중복 체크
	public String memberIdCheck(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); //null, member_id 가 리턴 둘중하나
	}
	
	//로그인용
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	
	//회원가입
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
