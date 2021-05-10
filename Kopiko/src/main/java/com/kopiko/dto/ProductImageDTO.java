package com.kopiko.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {
	private Long productId;
	private String productName;
	private Long categoryId;
	private Long brandId;
	private BigDecimal price;
	private BigDecimal salePrice;
	private Date dateCreated;
	private String description;
	private Integer status;
	private List<MultipartFile> imagesUrl;
	private List<String> listImgUrl;
	
}
