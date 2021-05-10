/**
 * 
 */
package com.kopiko.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kopiko.converter.OrderConverter;
import com.kopiko.entity.OrderEntity;
import com.kopiko.service.IOrderService;
import com.kopiko.service.IOrderStatusService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:09:03
 */

@Controller
@RequestMapping("/admin")
public class ControlOrderController {
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IOrderStatusService orderStatus;
	
	@Autowired
	private OrderConverter orderConverter;
	
	@GetMapping("/order/list")
	public String viewListOrder(Model model){
		return "admin/list-order";
	}
	
	@GetMapping("/order/list/status/{id}")
	public String viewListOrderByStatusId(Model model, @PathVariable(name = "id") Long statusId){
		model.addAttribute(statusId);
		return "admin/list-order";
	}
	
	@GetMapping("/order/list/account/{id}")
	public String viewListOrderByAccountId(Model model, @PathVariable(name = "id") Long accountId){
		model.addAttribute(accountId);
		return "admin/list-order";
	}
	
	@GetMapping("/order/{id}/edit")
	public String updateStatus(Model model, @PathVariable(name = "id") Long orderId) {
		OrderEntity orderEntity = orderService.findByOrderId(orderId);
		model.addAttribute("orderDTO", orderConverter.toDTO(orderEntity));
		model.addAttribute("listOrderStatus", orderStatus.findAll());
		return "admin/update-order";
	}
	
	@GetMapping("/order/{id}/view")
	public String viewOrderDetail(Model model, @PathVariable(name = "id") Long orderId) {
		OrderEntity orderEntity = orderService.findByOrderId(orderId);
		model.addAttribute("order", orderEntity);
		model.addAttribute("listOrderStatus", orderStatus.findAll());
		return "admin/order-detail";
	}

}
