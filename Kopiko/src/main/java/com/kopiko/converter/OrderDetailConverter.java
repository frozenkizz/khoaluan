/**
 * 
 */
package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kopiko.dto.OrderDetailDTO;
import com.kopiko.entity.OrderDetailEntity;

/**
 * @author rianta9
 * @datecreated 23 thg 3, 2021 16:22:46
 */
@Component
public class OrderDetailConverter {
//	public OrderDetailEntity toEntity(OrderDetailDTO orderDetailDTO) {
//		OrderDetailEntity result = new OrderDetailEntity();
//		result.setOrderDetailId(orderDetailDTO.getOrderDetailId());
//		
//		return result;
//	}
	
	public OrderDetailDTO toDTO(OrderDetailEntity orderDetail) {
		OrderDetailDTO result = new OrderDetailDTO();
		result.setOrderDetailId(orderDetail.getOrderDetailId());
		result.setOrderId(orderDetail.getOrder().getOrderId());
		result.setProductId(orderDetail.getProductDetail().getProduct().getProductId());
		result.setProductName(orderDetail.getProductDetail().getProduct().getProductName());
		result.setProductDetailId(orderDetail.getProductDetail().getProductDetailId());
		result.setSize(orderDetail.getProductDetail().getSize());
		result.setQuantity(orderDetail.getQuantity());
		result.setSalePrice(orderDetail.getSalePrice());
		return result;
	}
	
	public List<OrderDetailDTO> toDTOList(List<OrderDetailEntity> listEntity){
		List<OrderDetailDTO> result = new ArrayList<OrderDetailDTO>();
		
		for (OrderDetailEntity orderDetailEntity : listEntity) {
			result.add(toDTO(orderDetailEntity));
		}
		
		return result;
	}
}
