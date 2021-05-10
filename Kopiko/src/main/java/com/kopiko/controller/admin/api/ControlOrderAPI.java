/**
 * 
 */
package com.kopiko.controller.admin.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopiko.converter.OrderConverter;
import com.kopiko.dto.OrderDTO;
import com.kopiko.entity.OrderEntity;
import com.kopiko.service.IOrderService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:09:03
 */

@RestController
@RequestMapping("api/v1/admin")
public class ControlOrderAPI {
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private OrderConverter orderConverter;
	
	@GetMapping("/order/list")
	public List<OrderDTO> getListOrder(){
		List<OrderEntity> listEntity = orderService.findAll();
		if(listEntity != null) return orderConverter.toDTOList(listEntity);
		return null;
	}
	
	@GetMapping("/order/list/status/{id}")
	public List<OrderDTO> getListOrderByStatusId(@PathVariable(name = "id") Long statusId){
		List<OrderEntity> listEntity =  orderService.findAllByOrderStatusOrderStatusId(statusId);
		if(listEntity != null) return orderConverter.toDTOList(listEntity);
		return null;
	}
	
	@GetMapping("/order/list/account/{id}")
	public List<OrderDTO> getListOrderByAccountId(@PathVariable(name = "id") Long accountId){
		List<OrderEntity> listEntity = orderService.findAllByAccountAccountId(accountId);
		if(listEntity != null) return orderConverter.toDTOList(listEntity);
		return null;
	}
	
	@GetMapping("/order/{id}")
	public OrderDTO getOrderByOrderId(Long orderId) {
		OrderEntity orderEntity = orderService.findByOrderId(orderId);
		if(orderEntity != null ) return orderConverter.toDTO(orderEntity);
		return null;
	}
	
	@PutMapping("/order/{orderid}/status/{statusid}")
	public OrderDTO updateOrderStatus(@PathVariable("orderid") Long orderId, @PathVariable("statusid") Long statusId) {
		OrderEntity orderEntity = orderService.updateStatus(orderId, statusId);
		if(orderEntity != null) return orderConverter.toDTO(orderEntity);
		return null;
	}
	
}
