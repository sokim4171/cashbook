package com.gdu.cashbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;

@Mapper
public interface CommentMapper {
	
	//댓글 추가
	public int insertComment(Comment comment);
	
	//댓글리스트 출력
	public List<Comment> selectCommentListByBoard(int boardNo);
	
}
