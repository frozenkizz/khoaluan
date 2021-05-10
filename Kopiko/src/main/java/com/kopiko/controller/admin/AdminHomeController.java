package com.kopiko.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminHomeController {
	
	@RequestMapping("/admin/home")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String index() {
		return "admin/home";
	}
  
	@RequestMapping("/admin/category")
	public String initCategoryPage() {
		return "admin/category-admin";
	}
	
	@RequestMapping("/admin/login")
	public String loginPage() {
		return "admin/login";
	}
}
