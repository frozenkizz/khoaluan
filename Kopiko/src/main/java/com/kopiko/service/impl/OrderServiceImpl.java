/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.common.constant.Constants;
import com.kopiko.entity.OrderEntity;
import com.kopiko.entity.OrderStatusEntity;
import com.kopiko.repository.IOrderRepository;
import com.kopiko.repository.IOrderStatusRepository;
import com.kopiko.service.IOrderService;
import com.kopiko.statistic.IOrderStatistic;
import com.kopiko.statistic.IRevenueStatistic;

@Service
public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired 
	private IOrderStatusRepository orderStatusRepository;
	
	
	@Override
	public List<OrderEntity> findAll() {
		return orderRepository.findAll();
	}


	@Override
	public List<OrderEntity> findAllByOrderStatusOrderStatusId(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByOrderStatusOrderStatusId(id);
	}


	@Override
	public List<OrderEntity> findAllByAccountAccountId(Long accountId) {
		// TODO Auto-generated method stub
		return orderRepository.findAllByAccountAccountId(accountId);
	}


	@Override
	public OrderEntity findByOrderId(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getOne(id);
	}


	@Override
	public OrderEntity updateStatus(Long orderId, Long statusId) {
		OrderEntity order = orderRepository.getOne(orderId);
		if(order != null) {
			OrderStatusEntity orderStatus = orderStatusRepository.getOne(statusId);
			if(orderStatus != null) {
				order.setOrderStatus(orderStatus);
				orderRepository.saveAndFlush(order);
			}
			return order;
		}
		return null;
	}


	@Override
	public OrderEntity save(OrderEntity order) {
		if(order == null) return null;
		OrderEntity data;
		if(order.getOrderId() == null) {
			if(order.getAccount() == null || order.getDeliveryInfo() == null || order.getPaymentMethod() == null) {
				System.out.println("Info in order null");
				return null;
			}
			else {
				data = order;
				OrderStatusEntity orderStatus = orderStatusRepository.getOne(Constants.OrderStatus.WAITING_CONFIRM_STATUS);
				if(orderStatus != null) {
					order.setOrderStatus(orderStatus);
					orderRepository.saveAndFlush(order);
				}
				data.setOrderStatus(orderStatus);
			}
		}
		else { // update order
			data = orderRepository.getOne(order.getOrderId());
			if(data == null) return null;
			
			if(order.getAccount() != null)  data.setAccount(order.getAccount());
			if(order.getDeliveryInfo() != null) data.setDeliveryInfo(order.getDeliveryInfo());
			if(order.getShippingFee() != null) data.setShippingFee(order.getShippingFee());
			if(order.getPaymentMethod() != null) data.setPaymentMethod(order.getPaymentMethod());
			if(order.getOrderStatus() != null) data.setOrderStatus(order.getOrderStatus());
			System.out.println("Check order condition: valid!");
		}
		return orderRepository.saveAndFlush(data);
	}


	@Override
	public List<OrderEntity> findAllByUsername(String username) {
		if(username == null) return null;
		return orderRepository.findAllByUsername(username);
	}


	@Override
	public OrderEntity findByOrderIdAndAccountId(Long orderId, Long accountId) {
		if(orderId == null || accountId == null) return null;
		return orderRepository.findByOrderIdAndAccountAccountId(orderId, accountId);
	}


	@Override
	public List<IOrderStatistic> getListQuantityOfOrderByYear(Integer year) {
		return orderRepository.getListQuantityOfOrderByYear(year);
	}


	@Override
	public List<IRevenueStatistic> getListRevenueOfOrderByYear(Integer year) {
		return orderRepository.getListRevenueOfOrderByYear(year);
	}

}
