package com.gdu.cashbook.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndSum;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
   @Autowired private CashService cashService;
   
   //월별 수입 지출 및 캘린더 출력 뷰 
   @GetMapping("/getCashListByMonth")
   public String getCashListByMonth(HttpSession session,Model model, 
	         @RequestParam(value="day", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate day) {
	  
	  //session
      if(session.getAttribute("loginMember") == null) {
         return "redirect:/login";
      }
	  
	   
	   /* 0. 오늘 LocalDate타입
	    * 1. 오늘 Calendar타입
	    * 2. 이번달의 마지막 일
	    * 3. 이번달의 1일의 요일
	    */
	   
      System.out.println("day : "+day);
      Calendar cDay= Calendar.getInstance(); //현재 시간을 리턴하는 함수
      System.out.println(cDay.get(Calendar.MONTH)+1); //5
      if(day ==null) { //값이 없으면 
    	  day=LocalDate.now();
    	  System.out.println("[day]"+day);
      } else { //값이 잇으면 
    	  //day를 cDay로 형변환
    	  // LocalDate -> Date -> Calender
    	  // LocalDate -> Calender
    	  cDay.set(day.getYear(),day.getMonthValue()-1,day.getDayOfMonth()); //형변환
    	  
    	  System.out.println(day.getMonthValue()+"<--day.getMonthValue()"); //6
    	  System.out.println(day.getDayOfMonth()+"<--day.getDayOfMonth()"); //20
      }
      
       //cDay 는 캘린더 형식의 현재 시간 
	   // 일별 수입 , 지출 총액
	   String memberId=((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
	   //System.out.println(memberId+"<--memberId");
	   int year=cDay.get(Calendar.YEAR);
	   int month=cDay.get(Calendar.MONTH)+1;
	   //System.out.println("날짜 : " +year+"."+month);
	   List<DayAndSum> dayAndPriceList=cashService.getCashAndPrice(year, month, memberId);
	   for (DayAndSum ds: dayAndPriceList) {
		System.out.println(ds);
	   }
	   
	   
	   model.addAttribute("dayAndPriceList", dayAndPriceList);
	   model.addAttribute("day", day);
	   model.addAttribute("month", cDay.get(Calendar.MONTH)+1); //월
	   model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); //마지막 일
	   
	   //firstDay 
	   Calendar firstDay= cDay;
	   firstDay.set(Calendar.DATE,1); //cDay 에서 일만 1일로 변경 
	   firstDay.get(Calendar.DAY_OF_WEEK); //요일      0->일요일,   1->월요일,   2->화요일    ......   6토요일
	   System.out.println("firstDay.get(Calendar.DAY_OF_WEEK);"+firstDay.get(Calendar.DAY_OF_WEEK));
	   model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK) ); //이번달의 1일이 무슨요일인지 
	   
	   return "getCashListByMonth";
   }
   
   //가계부내용 추가
   @GetMapping("/insertCash")
   public String insertCash(HttpSession session) {
	 //session
      if(session.getAttribute("loginMember") == null) {
         return "redirect:/login";
      }
	   return "modifyCash";
   }
   @PostMapping("/insertCash")
   /*
	public String insertCash() {
	}
    */
	   
   
   //리스트 삭제 
   @GetMapping("/removeCash")
   public String removeCash(@RequestParam("cashNo") String cashNo) {
      cashService.removeCash(cashNo);
	   return "redirect:/getCashListByDate";
   }
   
   
   
   //지출 수입 리스트
   @GetMapping("/getCashListByDate")
   public String getCashListByDate(HttpSession session, Model model, 
         @RequestParam(value="day", required = false) @DateTimeFormat(pattern ="yyyy-MM-dd") LocalDate day) {
         //day값으로 널이 들어올 수도 있음 / 값을 문자열로 가지고 오면 자동적으로 형식 변환 yyyy-MM-dd 
      
      if(day == null) {
         day = LocalDate.now(); 
      }
      
      System.out.println(day +"dayyyyy");
      
      //session
      if(session.getAttribute("loginMember") == null) {
         return "redirect:/login";
      }
      
      //로그인 아이디
      String loginMember = ((LoginMember)(session.getAttribute("loginMember"))).getMemberId();
      
      
      //cash : id + date(yyyy-mm-dd) 값이 들어가야 함
      Cash cash = new Cash();
      cash.setMemberId(loginMember);
      cash.setCashDate(day);
      
      //list
      Map<String, Object> map=cashService.getCashListByDate(cash);
      
      //model
      model.addAttribute("list", map.get("list"));
      model.addAttribute("cashKindSum", map.get("cashKindSum"));
      model.addAttribute("day", day);
      
      //debug
      /*for(Cash c : list) {
         System.out.println(c);
      }*/
      
      return "getCashListByDate";
      
   }
}