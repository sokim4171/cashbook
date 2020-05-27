package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	
	//게시글 수정
	public int updateBoard(Board board);
	//게시글 삭제
	public int deleteBoard(int boardNo);
	//게시글 추가
	public int insertBoard(Board board);
	//게시글 상세보기
	public Board selectBoardOne(int boardNo);
	//게시글 리스트 보기
	public List<Board> selectBoardList(int beginRow, int rowPerPage, String boardTitle);
	
	//게시글 수
	public int totalBoard();
	//게시글 수(검색값)
	public int totalBoardByTitle(String boardTitle);
	//게시글 최소 번호
	public int minBoardOne();
}
