/**
 * 
 */
package com.kopiko.statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 31 thg 3, 2021 00:55:11
 */
@Data
@NoArgsConstructor
public class OrderStatistic implements IOrderStatistic{
	private Integer month;
	private Integer quantityOfOrder;
}
