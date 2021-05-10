package com.kopiko.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author rianta9
 * @datecreated 13 thg 3, 2021 11:17:28
 * @lastupdated 2021-03-13 11:17:28.504
 */
@Entity
@Table(name = "brand")
@Data
@NoArgsConstructor
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "brand_id")
	private Long brandId;
	
	@Column(nullable = false, columnDefinition = "nvarchar(50)")
	private String brandName;
	
	@Column(nullable = false)
	private String brandImgUrl;
	private String info;
	
	@Transient
	private MultipartFile imageFile;
}