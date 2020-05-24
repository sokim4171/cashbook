package com.gdu.cashbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;

@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	//댓글 추가
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
	
	//댓글리스트 출력
	public List<Comment> getCommentByBoard(int boardNo){
		return commentMapper.selectCommentListByBoard(boardNo);
	}
}
