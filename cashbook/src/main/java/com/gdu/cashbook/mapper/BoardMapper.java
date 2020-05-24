package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	
	//게시글 추가
	public int insertBoard(Board board);
	//게시글 상세보기
	public Board selectBoardOne(int boardNo);
	//게시글 리스트 보기
	public List<Board> selectBoardList();
}
