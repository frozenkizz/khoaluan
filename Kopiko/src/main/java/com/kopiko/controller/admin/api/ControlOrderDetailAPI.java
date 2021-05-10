/**
 * 
 */
package com.kopiko.controller.admin.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopiko.converter.OrderDetailConverter;
import com.kopiko.dto.OrderDetailDTO;
import com.kopiko.entity.OrderDetailEntity;
import com.kopiko.service.IOrderDetailService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:17:51
 */

@RestController
@RequestMapping("/api/v1/admin")
public class ControlOrderDetailAPI {
	@Autowired
	private OrderDetailConverter orderDetailConverter;
	
	@Autowired
	private IOrderDetailService orderDetailService;
	
	@GetMapping("/order/{orderId}/detail")
	public List<OrderDetailDTO> getListOrderDetailByOrderId(@PathVariable Long orderId){
		List<OrderDetailEntity> listEntity = orderDetailService.findAllByOrderId(orderId);
		if(listEntity != null) return orderDetailConverter.toDTOList(listEntity);
		return null;
	}
}
