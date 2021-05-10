/**
 * 
 */
package com.kopiko.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 13 thg 3, 2021 19:59:10
 */
@Entity
@Table(name = "comment_image")
@Data
@NoArgsConstructor
public class CommentImage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "comment_image_id")
	private Long commentImageId;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;
}
