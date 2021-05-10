package com.kopiko.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kopiko.common.constant.Constants;
import com.kopiko.converter.AccountCustomerConverter;
import com.kopiko.dto.AccountCustomerDTO;
import com.kopiko.entity.Account;
import com.kopiko.service.IAccountService;
import com.kopiko.util.FileUpload;

@Controller
public class MyAccountController {
	
	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private AccountCustomerConverter accountCustomerConverter;
	
	@GetMapping("/account/profile")
	public String viewAccount(Model model) {
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
			}
		}
		return "web/control-account";
	}
	
	@PostMapping("/account/profile")
	public String updateAccount(Model model, @ModelAttribute AccountCustomerDTO accountCustomerDTO, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if (username == null)
			return "redirect:/login";
		else {
			Account account = accountService.findByUsername(username);
			if (account != null && account.getStatus() == Constants.Account.ACTIVE_STATUS) {
				// update data
				if(accountCustomerDTO == null) return "redirect:/account/profile?message=error";
				MultipartFile file = accountCustomerDTO.getAvatarFile();
				String avatarUrl = account.getAvatar();
				if(file != null) avatarUrl = FileUpload.uploadImage(file, request);
				account.setAvatar(avatarUrl);
				account.setAddress(accountCustomerDTO.getAddress().trim());
				account.setEmail(accountCustomerDTO.getEmail().trim());
				account.setPhone(accountCustomerDTO.getPhone().trim());
				account.setFullName(accountCustomerDTO.getFullName().trim());
				account = accountService.save(account);
				if(account != null) return "redirect:/account/profile";
				else return "redirect:/account/profile?message=error";
			}
		}
		return "web/control-account";
	}
	
	@PostMapping("/account/change-password")
	public String updatePassword(Model model, @PathVariable String newPassword, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		// Lấy username ra
		System.out.println("username get from authentication:" + username);
		if (username == null)
			return "redirect:/login";
		else {
			Account account = accountService.findByUsername(username);
			if (account != null && account.getStatus() == Constants.Account.ACTIVE_STATUS) {
				// update data
				if(newPassword == null) return "redirect:/account/profile?message=error";
				account.setPassword(newPassword);
				account = accountService.save(account);
				if(account != null) return "redirect:/account/profile";
				else return "redirect:/account/profile?message=error";
			}
		}
		return "web/update-password";
	}
}
