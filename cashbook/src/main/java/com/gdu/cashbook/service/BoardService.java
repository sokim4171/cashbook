package com.gdu.cashbook.service;

import java.util.List;

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
	public List<Board> getBoardList(){
		List<Board> list=boardMapper.selectBoardList();
		return list;
	}
}
