package com.kopiko.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.kopiko.util.DateTimeUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "comment_id")
	private Long commentId;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "product_id")
	private Product product;
	
	@Column(nullable = false, columnDefinition = "datetime")
	@CreationTimestamp
	private Date dateCreated;
	
	@Column(name = "title", nullable = false, columnDefinition = "nvarchar(100)")
	private String title;
	
	@Column(name = "[content]", nullable = false, columnDefinition = "ntext")
	private String content;
	
	@Column(columnDefinition = "bigint")
	private Long parentsCommentId;
	
	@OneToMany(mappedBy = "comment")
	private List<CommentImage> listCommentImage;
	
	public String getDateCreatedString() {
		return DateTimeUtil.toStringType(dateCreated);
	}
}
