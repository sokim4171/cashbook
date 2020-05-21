package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndSum;

@Mapper
public interface CashMapper {
	
	//수정
	public int updateCash(Cash cash);
	//수정 뷰
	public Cash selectCashOne(int cashNo);
	
	//월별 수입 지출 
	public List<DayAndSum> selecetDayAndPriceList(Map<String,Object> map);
	
	
	
	//가계부내용 추가
	public int insertCash(Cash cash);
	
	
	//리스트 삭제
	public int deleteCash(String cashNo);
	
	
	
	//로그인 사용자의 오늘 날짜 cash 목록 나타내기
	public List<Cash> selectCashListByDate(Cash cash);
	//로그인 사용자의 오늘 날짜  cash합계
	public int selectCashKindSum(Cash cash);
}