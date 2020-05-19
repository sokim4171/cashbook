package com.gdu.cashbook.controller;

import java.time.LocalDate;
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
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
   @Autowired private CashService cashService;
   
   
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