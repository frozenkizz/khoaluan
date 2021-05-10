package com.kopiko.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.Comment;

@Service
public interface ICommentService {

	List<Comment> findAll();

	Comment insert(Comment comment);

	Comment update(Comment comment);

	boolean delete(Comment comment);

	Comment findOne(Long commentId);

	void save(Comment comment);

	/**
	 * @param s
	 * @return
	 */
	Object search(String s);
}