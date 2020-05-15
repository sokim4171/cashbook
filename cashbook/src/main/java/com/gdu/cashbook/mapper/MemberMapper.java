package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Mapper
public interface MemberMapper {
	
	//아이디 찾기용
	public String selectMemberIdByMember(Member member);
	//회원 정보 수정
	public int updateMember(Member member);
	//회원 정보 탈퇴 용 삭제
	public int deleteMember(LoginMember loginMember);
	//memberInfo에 사용
	public Member selectMemberOne(LoginMember loginMember); 
	//중복체크
	public String selectMemberId(String memberIdCheck);
	//로그인 멤버 확인
	public LoginMember selectLoginMember(LoginMember loginMember);
	//멤버 추가 (회원가입)
	public int insertMember(Member member);
}
