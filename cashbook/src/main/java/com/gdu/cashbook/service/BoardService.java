package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired 
	private BoardMapper boardMapper;
	
	//게시글 수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	
	//게시글 삭제
	public int removeBoard(int boardNo) {
		return boardMapper.deleteBoard(boardNo);
	}
	
	//게시글 추가
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	
	//게시판 상세 출력
	public Board getBoardOne(int boardNo) {
		return boardMapper.selectBoardOne(boardNo);
	}
	
	
	//게시판 출력
	public Map<String, Object> getBoardList(int beginRow, int rowPerPage, String boardTitle){
		System.out.println(boardTitle+"<---검색값");
		Map<String, Object> map1 = new HashMap<>();

		map1.put("beginRow", beginRow);
		map1.put("rowPerPage", rowPerPage);
		map1.put("boardTitle", boardTitle);

		Map<String, Object> map2 = new HashMap<>();
		int totalRow = 0;
		if(boardTitle.equals("")) {
			totalRow = this.boardMapper.totalBoard();
		}else {
			totalRow = this.boardMapper.totalBoardByTitle(boardTitle);
		}
		System.out.println(totalRow +"<----totalRow");
		int lastPage = totalRow/rowPerPage;
		if(totalRow%rowPerPage != 0) {
			lastPage+=1;
		}
		System.out.println(lastPage);
		List<Board> list = this.boardMapper.selectBoardList(beginRow, rowPerPage, boardTitle);
		map2.put("list", list);
		map2.put("lastPage", lastPage);

		return map2;
	}
}
