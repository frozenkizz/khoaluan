package com.kopiko.controller.admin.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopiko.converter.AccountConverter;
import com.kopiko.dto.AccountDTO;
import com.kopiko.entity.Account;
import com.kopiko.service.impl.AccountService;

@RestController
@RequestMapping("/api/v1/admin")
public class ControlAccountAPI {
	@Autowired
	private AccountConverter accountConverter;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/account/list")
	public List<AccountDTO> getListAccount() {
//		System.out.println("du lieu dc tim kiem dc:" +accountConverter.toDTOList(accountService.findAll()));
		return accountConverter.toDTOList(accountService.findAll()) ;
	}
	
	@PutMapping("/account/{id}")
	public AccountDTO update(@PathVariable(name = "id" )Long accountId, @RequestBody AccountDTO accountDTO) {
		System.out.println(accountDTO.toString());
		Account account = accountConverter.toEntity(accountDTO);
		if(account.getRole() == "ADMIN") account.setAvatar("avatar-admin.jpg");
		else  account.setAvatar("avatar-user.png");
		account = accountService.save(account);
		return accountConverter.toDTO(account); 
	}
	
	@DeleteMapping("/account/{id}")
	public String delete(@PathVariable(name="id") Long accountId) {
		boolean result = accountService.delete(accountId);
		if(result) return "Delete success!";
		return "Delete fail!";
	}
	/*
	 * @DeleteMapping("/product/{id}")
	public String delete(@PathVariable(name="id") Long productId) {
		boolean result = productService.delete(productId);
		if(result) return "Delete success!";
		return "Delete fail!";
	}
	 */
	
	
	
}
