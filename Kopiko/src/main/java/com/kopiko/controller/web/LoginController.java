/**
 * 
 */
package com.kopiko.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.dto.AccountCustomerDTO;
import com.kopiko.entity.Account;
import com.kopiko.service.IAccountService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:22:17
 */

@Controller
public class LoginController {

	@Autowired
	IAccountService accountService;

	@Autowired
	private AccountCustomerConverter accountCustomConverter;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		System.out.println("LoginController - showLoginPage");

		return "web/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, ModelMap model,
			HttpSession session) {
		if (accountService.checkAdmin(username, password)) {
			System.out.println("Login success admin");
			Account data = accountService.findByUsername(username);
			AccountCustomerDTO account = accountCustomConverter.toDTO(data);
			session.setAttribute("account", account);
			return "redirect:/admin/home";
		}

		if (accountService.checkUser(username, password)) {
			System.out.println("Login success user");
			session.setAttribute("username", username);
			Account data = accountService.findByUsername(username);
			AccountCustomerDTO account = accountCustomConverter.toDTO(data);
			session.setAttribute("account", account);
			return "redirect:/home";
		}

		model.put("errorMessage", "Invalid Credentials");

		return "redirect:/login";
	}
}