package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;

@Mapper
public interface BoardMapper {
	
	public Board selectBoardOne(int boardNo);
	public List<Board> selectBoardList();
}
