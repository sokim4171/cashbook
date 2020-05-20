package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.mapper.CategoryMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.Category;
import com.gdu.cashbook.vo.DayAndSum;

@Service
@Transactional
public class CashService {
   @Autowired private CashMapper cashMapper;
   @Autowired private CategoryMapper categoryMapper;
	
	//카테고리 리스트 출력 -> 가계부 드롭다운 
	public List<Category> selectCategoryList(){
		List<Category> categorylist=categoryMapper.selectCashCategoryList();
		return categorylist;
	}
   
   
   //월별 수입 지출 
   public List<DayAndSum> getCashAndPrice(int year, int month,String memberId) {
	   Map<String , Object> map= new HashMap<>();
	   map.put("memberId", memberId);
	   map.put("year", year);
	   map.put("month", month);
	   return cashMapper.selecetDayAndPriceList(map);
   }
   
   
   
   
   //리스트 추가 
   public int addCashList(Cash cash){
	   return cashMapper.insertCash(cash);
   }
   
   //리스트 삭제
   public int removeCash(String cashNo) {
	   return cashMapper.deleteCash(cashNo);
   }
   
   //금일 지출 수입 리스트
   public Map<String, Object> getCashListByDate(Cash cash){
      List<Cash> list=cashMapper.selectCashListByDate(cash);
      int cashKindSum=cashMapper.selectCashKindSum(cash);
      Map<String, Object> map=new HashMap<String, Object>();
      map.put("list", list);
      map.put("cashKindSum", cashKindSum);
      return map;
   }
}