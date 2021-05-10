/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.service;

import java.util.List;

import com.kopiko.entity.OrderEntity;
import com.kopiko.statistic.IOrderStatistic;
import com.kopiko.statistic.IRevenueStatistic;

public interface IOrderService {

	/**
	 * @return
	 */
	List<OrderEntity> findAll();
	List<OrderEntity> findAllByOrderStatusOrderStatusId(Long orderStatusId);
	List<OrderEntity> findAllByAccountAccountId(Long accountId);
	OrderEntity findByOrderId(Long id);
	OrderEntity updateStatus(Long orderId, Long statusId);
	/**
	 * @param order
	 * @return
	 */
	OrderEntity save(OrderEntity order);
	/**
	 * @param username
	 * @return
	 */
	List<OrderEntity> findAllByUsername(String username);
	/**
	 * Tìm kiếm xem orderId có thuộc tài khoản accountId ko...Nếu có trả về order.
	 * @param orderId
	 * @param accountId
	 * @return
	 */
	OrderEntity findByOrderIdAndAccountId(Long orderId, Long accountId);
	/**
	 * Thống kê số lượng order theo mỗi tháng của năm(12 tháng)
	 * @param year
	 * @return
	 */
	List<IOrderStatistic> getListQuantityOfOrderByYear(Integer year);
	
	/**
	 * Thống kế tổng doanh thu theo mỗi tháng(12 tháng) của một năm
	 * @param year
	 * @return
	 */
	List<IRevenueStatistic> getListRevenueOfOrderByYear(Integer year);
}
