package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	
	//게시글 수정
	@GetMapping("/modifyBoard")
	public String modifyBoard(HttpSession session,Model model,@RequestParam("boardNo") int boardNo) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		Board board=boardService.getBoardOne(boardNo);
		model.addAttribute("board", board);
		return "modifyBoard";
	}
	//게시글 수정 액션
	@PostMapping("/modifyBoard")
	public String modifyBoard(HttpSession session,Model model,Board board) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		boardService.modifyBoard(board);
		
		return "redirect:/boardList";
	}
	
	//게시글 삭제
	@GetMapping("/removeBoard")
	public String removeBoard(@RequestParam("boardNo") int boardNo) {
		boardService.removeBoard(boardNo);
		return "redirect:/boardList";
	}
	
	
	//댓글 추가
	@GetMapping("/addComment")
	public String addComment(HttpSession session,Comment comment, 
			@RequestParam("boardNo") int boardNo) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		commentService.addComment(comment);
		return "redirect:/boardListDetail?boardNo="+boardNo;
	}
	
	//게시글 추가
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session,Model model ) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		
		return "addBoard";
	}
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session,Board board) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		boardService.addBoard(board);
		return "redirect:/boardList";
	}
	
	//게시글 상세보기
	@GetMapping("/boardListDetail")
	public String boardListDetail(Model model,HttpSession session,
			@RequestParam("boardNo") int boardNo) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		Board board=boardService.getBoardOne(boardNo);
		model.addAttribute("board", board);
		
		List<Comment> commentList=commentService.getCommentByBoard(boardNo);
		model.addAttribute("commentList", commentList);
		
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		
		return "boardListDetail";
	}
	
	
	//게시글 리스트 보기
	@GetMapping("/boardList")
	public String boardList(Model model,HttpSession session) {
		//로그인 안했을때 
		if(session.getAttribute("loginMember")==null){ //로그인 해있으면 못하게 막기 
			return "redirect:/index";
		}
		
		List<Board> boardList=boardService.getBoardList();
		model.addAttribute("bList", boardList);
		
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		model.addAttribute("memberId", memberId);
		
		return "boardList";
	}
}
