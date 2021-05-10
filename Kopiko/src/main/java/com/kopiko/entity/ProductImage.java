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

@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
public class ProductImage {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "product_image_id")
	private Long productImageId;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "product_id")
	private Product product;
	
	@Column(nullable = false)
	private String imageUrl;
	

}