/**
 * 
 */
package com.kopiko.dto;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.kopiko.util.DateTimeUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 13:40:41
 * Sử dụng để truy xuất thông tin order
 */

@Data
@NoArgsConstructor
public class OrderDTO {
	private Long orderId;
	private String dateCreated;
	private String deliveryInfo;
	private Long accountId;
	private String fullName;
	private Long orderStatusId;
	private String statusName;
	private Long paymentMethodId;
	private String paymentMethodName;
	/**
	 * Tên hiển thị của order
	 */
	private String name;
	
	private Long totalPrice;
	
	public Date getDateCreatedSQL() {
		return DateTimeUtil.toDateSQL(dateCreated);
	}
}
