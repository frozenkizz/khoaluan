package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//
//import com.kopiko.entity.Account;
import com.kopiko.entity.Comment;
import com.kopiko.repository.ICommentRepository;
import com.kopiko.service.ICommentService;

@Service
public class CommentService implements ICommentService{

	
	@Autowired
	private ICommentRepository commentRespository;

	
	
	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment insert(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment update(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Comment comment) {
		
		return false;
	}


	public Comment search(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void  save(Comment comment) {
		 commentRespository.save(comment);
	}
	
	
	@Override
	
	public Comment findOne(Long commentId) {
		
		return commentRespository.getOne( commentId);
	}

}
