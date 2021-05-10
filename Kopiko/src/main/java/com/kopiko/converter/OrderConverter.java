/**
 * 
 */
package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kopiko.dto.OrderDTO;
import com.kopiko.entity.Account;
import com.kopiko.entity.OrderEntity;
import com.kopiko.entity.OrderStatusEntity;
import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.service.IAccountService;
import com.kopiko.service.IOrderService;
import com.kopiko.service.IOrderStatusService;
import com.kopiko.service.IPaymentMethodService;
import com.kopiko.service.IProductService;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 13:43:32
 */

@Component
public class OrderConverter {
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IPaymentMethodService paymentMethodService;
	
	@Autowired
	private IOrderStatusService orderStatusService;
	
	public OrderEntity toEntity(OrderDTO orderDTO) {
		
		Account account = accountService.findOne(orderDTO.getAccountId());
		OrderStatusEntity orderStatus = orderStatusService.findOne(orderDTO.getOrderStatusId());
		PaymentMethodEntity paymentMethod = paymentMethodService.findPaymentMethodById(orderDTO.getPaymentMethodId());
		
		OrderEntity result = new OrderEntity();
		result.setOrderId(orderDTO.getOrderId());
		result.setDateCreated(orderDTO.getDateCreatedSQL());
		result.setDeliveryInfo(orderDTO.getDeliveryInfo());
		result.setAccount(account);
		result.setPaymentMethod(paymentMethod);
		result.setOrderStatus(orderStatus);
		return result;
	}
	
	public OrderDTO toDTO(OrderEntity order) {
		OrderDTO result = new OrderDTO();
		result.setOrderId(order.getOrderId());
		result.setDateCreated(order.getDateCreatedString());
		result.setDeliveryInfo(order.getDeliveryInfo());
		result.setAccountId(order.getAccount().getAccountId());
		result.setFullName(order.getAccount().getFullName());
		result.setPaymentMethodId(order.getPaymentMethod().getPaymentMethodId());
		result.setPaymentMethodName(order.getPaymentMethod().getPaymentMethodName());
		result.setOrderStatusId(order.getOrderStatus().getOrderStatusId());
		result.setStatusName(order.getOrderStatus().getStatusName());
		result.setName(order.getName());
		result.setTotalPrice(order.getTotalPrice());
		return result;
	}
	
	public List<OrderEntity> toEntityList(List<OrderDTO> listOrderDTO) {
		List<OrderEntity> result = new ArrayList<OrderEntity>();
		for (OrderDTO orderDTO : listOrderDTO) {
			result.add(toEntity(orderDTO));
		}
		return result;
	}
	
	public List<OrderDTO> toDTOList(List<OrderEntity> listOrder) {
		List<OrderDTO> result = new ArrayList<OrderDTO>();
		for (OrderEntity orderEntity : listOrder) {
			result.add(toDTO(orderEntity));
		}
		return result;
	}
}
