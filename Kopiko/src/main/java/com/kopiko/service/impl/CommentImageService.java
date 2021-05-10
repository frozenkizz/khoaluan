/**
 * 
 */
package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.entity.Comment;
import com.kopiko.entity.CommentImage;
import com.kopiko.repository.ICommentImageRepository;
import com.kopiko.service.ICommentImageService;

/**
 * @author rianta9
 * @datecreated 13 thg 3, 2021 20:48:30
 */
@Service
public class CommentImageService implements ICommentImageService{
	@Autowired
	private ICommentImageRepository commentImageService;
	@Override
	public CommentImage insert(CommentImage commentImage) {
		return commentImageService.saveAndFlush(commentImage);
	}

	@Override
	public CommentImage update(CommentImage commentImage) {
		return commentImageService.saveAndFlush(commentImage);
	}

	
	@Override
	public boolean delete(CommentImage commentImage) {
		commentImageService.delete(commentImage);
		return true;
	}


	@Override
	public void  save(CommentImage commentImage) {
		 commentImageService.save(commentImage);
	}
	

	
	
//	@Override
//	
//	public CommentImage findOne(Long commentImageId) {
//		
//		return commentImageRespository.getOne( commentImageId);
//	}

	
	
	@Override
	public List<CommentImage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentImage findOne(Long commentImageId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object search(String a) {
		// TODO Auto-generated method stub
		return null;
	}






}
