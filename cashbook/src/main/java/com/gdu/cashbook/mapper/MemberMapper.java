package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	//memberInfo에 사용
	public Member selectMemberOne(LoginMember loginMember); 
	//중복체크
	public String selectMemberId(String memberIdCheck);
	//로그인 멤버 확인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//멤버 추가 (회원가입)
	public int insertMember(Member member);
}
