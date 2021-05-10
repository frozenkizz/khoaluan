/**
 * 
 */
package com.kopiko.model;

import com.kopiko.dto.ProductShowListDTO;
import com.kopiko.entity.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 25 thg 3, 2021 01:25:53
 */

@Data
@NoArgsConstructor
public class Cart {
	private ProductShowListDTO product;
	private Long productDetailId;
	private String size;
	private Integer quantity;
}
