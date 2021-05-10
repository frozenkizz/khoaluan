/**
 * 
 */
package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.CommentImage;

/**
 * @author rianta9
 * @datecreated 13 thg 3, 2021 20:43:13
 */
@Repository
public interface ICommentImageRepository extends JpaRepository<CommentImage, Long>{
	CommentImage findByCommentImageId(Long id);
	List<CommentImage> findAllByCommentCommentId(Long commentId);
}
