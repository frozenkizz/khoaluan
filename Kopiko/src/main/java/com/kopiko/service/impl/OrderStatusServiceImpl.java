/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.entity.OrderStatusEntity;
import com.kopiko.repository.IOrderStatusRepository;
import com.kopiko.service.IOrderStatusService;

@Service
public class OrderStatusServiceImpl implements IOrderStatusService {

	@Autowired
	private IOrderStatusRepository orderStatusRepository;
	
	@Override
	public OrderStatusEntity findOne(Long orderStatusId) {
		// TODO Auto-generated method stub
		return orderStatusRepository.getOne(orderStatusId);
	}

	@Override
	public List<OrderStatusEntity> findAll() {
		// TODO Auto-generated method stub
		return orderStatusRepository.findAll();
	}

//	@Autowired
//	private IOrderStatusRepository orderStatusRepository;
	
}
