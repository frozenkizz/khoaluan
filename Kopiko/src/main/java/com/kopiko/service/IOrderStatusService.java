/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.service;

import java.util.List;

import com.kopiko.entity.OrderStatusEntity;

public interface IOrderStatusService {

	/**
	 * @param orderStatusId
	 * @return
	 */
	OrderStatusEntity findOne(Long orderStatusId);

	/**
	 * @return
	 */
	List<OrderStatusEntity> findAll();

}
