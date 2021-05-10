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

import com.kopiko.converter.AccountConverter;
import com.kopiko.service.impl.AccountService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:08:40
 */

@Controller
@RequestMapping("/admin/account")
public class ControlAccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountConverter accountConverter;
	
	
	@GetMapping("/list")
	public String viewListAccount() {
		return "admin/list-account" ;
	}
	
//	@GetMapping("/product/{id}/edit")
//	public String update(Model model, @PathVariable(name = "id") Long productId) {
//		model.addAttribute("productId", productId);
//		model.addAttribute("productDTO", productConverter.toDTO(productService.findByProductId(productId)));
//		model.addAttribute("listBrand", brandService.findAll());
//		model.addAttribute("listCategory", categoryService.findAll());
//		model.addAttribute("listStatus", statusService.findAll());
//		return "admin/update-product";
//	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable(name = "id")Long accountId) {
		model.addAttribute("accountDTO",accountConverter.toDTO(accountService.findOne(accountId)));
		return "admin/update-account";
	}
}
