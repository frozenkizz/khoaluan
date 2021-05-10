/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.entity.OrderDetailEntity;
import com.kopiko.entity.OrderEntity;
import com.kopiko.repository.IOrderDetailRepository;
import com.kopiko.service.IOrderDetailService;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public List<OrderDetailEntity> findAll() {
		// TODO Auto-generated method stub
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetailEntity> findAllByOrderId(Long orderId) {
		List<OrderDetailEntity> list = orderDetailRepository.findAllByOrderOrderId(orderId);
		return list;
	}

	@Override
	public OrderDetailEntity save(OrderDetailEntity orderDetail) {
		if (orderDetail != null) {
			OrderDetailEntity data;
			if (orderDetail.getOrderDetailId() == null) {
				if (orderDetail.getOrder() == null || orderDetail.getProductDetail() == null
						|| orderDetail.getQuantity() == null || orderDetail.getSalePrice() == null)
					return null;
				else
					data = orderDetail;
			} else { // update
				data = orderDetailRepository.getOne(orderDetail.getOrderDetailId());
				if (orderDetail.getProductDetail() != null)
					data.setProductDetail(orderDetail.getProductDetail());
				if (orderDetail.getOrder() != null)
					data.setOrder(orderDetail.getOrder());
				if (orderDetail.getQuantity() != null)
					data.setQuantity(orderDetail.getQuantity());
				if (orderDetail.getSalePrice() != null)
					data.setSalePrice(orderDetail.getSalePrice());
			}
			return orderDetailRepository.saveAndFlush(data);
		}
		return null;
	}
}
