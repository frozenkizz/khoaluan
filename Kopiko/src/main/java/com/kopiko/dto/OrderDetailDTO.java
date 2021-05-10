/**
 * 
 */
package com.kopiko.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 16:19:33
 * Sử dụng để truy xuất thông tin chi tiết order
 */

@Data
@NoArgsConstructor
public class OrderDetailDTO {
	private Long orderDetailId;
	private Long orderId;
	private Long productId;
	private String productName;
	private Long productDetailId;
	private String size;
	private Integer quantity;
	private BigDecimal salePrice;
}
