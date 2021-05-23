/**
 * 
 */
package com.kopiko.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kopiko.common.constant.Constants;
import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.converter.OrderConverter;
import com.kopiko.dto.OrderDTO;
import com.kopiko.entity.Account;
import com.kopiko.entity.OrderEntity;
import com.kopiko.service.IAccountService;
import com.kopiko.service.IOrderService;

/**
 *  Quản lý các order của mỗi người dùng
 */

@Controller
@RequestMapping("/account")
public class OrderController {
	@Autowired
	private IOrderService orderService;

	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private AccountCustomerConverter accountCustomerConverter;

	@GetMapping("/order")
	public String viewListOrder(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if (username == null)
			return "redirect:/login";
		else {
			Account account = accountService.findByUsername(username);
			if (account != null && account.getStatus() == Constants.Account.ACTIVE_STATUS) {
				model.addAttribute("account", accountCustomerConverter.toDTO(account));
				
				List<OrderEntity> listData = orderService.findAllByUsername(username);
				if(listData != null) System.out.println("number of order for account " + username + ": " + listData.size());
				else System.out.println("List order null!");
				List<OrderDTO> listOrder = orderConverter.toDTOList(listData);
				model.addAttribute("listOrder", listOrder);
			}
		}
		return "web/control-order";
	}
	
	@GetMapping("/order/{orderId}")
	public String viewOrderDetail(@PathVariable Long orderId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if (username == null)
			return "redirect:/login";
		else {
			Account account = accountService.findByUsername(username);
			if (account != null && account.getStatus() == Constants.Account.ACTIVE_STATUS) {
				model.addAttribute("account", accountCustomerConverter.toDTO(account));
				OrderEntity order = orderService.findByOrderIdAndAccountId(orderId, account.getAccountId());
				if(order != null) {
					model.addAttribute("order", order);
				}
				else return "web/control-order-detail?message=error";
			}
			else return "redirect:/login";
		}
		return "web/control-order-detail";
	}
}
