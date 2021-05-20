/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.service;

import java.util.List;

import com.kopiko.entity.OrderDetailEntity;

public interface IOrderDetailService {

	/**
	 * @return
	 */
	List<OrderDetailEntity> findAll();
	
	List<OrderDetailEntity> findAllByOrderId(Long orderId);

	/**
	 * @param order
	 * @return
	 */
	OrderDetailEntity save(OrderDetailEntity orderDetail);

}
