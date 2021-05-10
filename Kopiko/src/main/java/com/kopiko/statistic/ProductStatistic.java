/**
 * 
 */
package com.kopiko.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 30 thg 3, 2021 21:49:28
 */

@Data
@NoArgsConstructor
public class ProductStatistic implements IProductStatistic{
	private Long productId;
	private String productName;
	private Integer quantityOfProduct;
	
}
