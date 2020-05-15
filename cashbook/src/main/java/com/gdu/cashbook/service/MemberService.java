package com.gdu.cashbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired 
	private MemberidMapper memberidMapper;
	
	//아이디 찾기용
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	
	//회원 정보 수정
	public int modifyMember(Member member) {
		return memberMapper.updateMember(member);
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
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	}
}
