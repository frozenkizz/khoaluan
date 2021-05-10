package com.kopiko.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Top12ProductSelling implements ITop12ProductSelling{	
	private Long productId;
	private String productName;
	private Integer quantityOfProduct;
	
	
	

}
