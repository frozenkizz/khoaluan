package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kopiko.dto.AccountDTO;
import com.kopiko.entity.Account;
import com.kopiko.service.IBrandService;
import com.kopiko.service.ICategoryService;

@Component
public class AccountConverter {
	@Autowired
	ICategoryService categoryService;
	@Autowired
	IBrandService brandService;

	public Account toEntity(AccountDTO accountDTO) {
		if(accountDTO == null ) return null;
		Account account = new Account();
		account.setAccountId(accountDTO.getAccountId());
		account.setUsername(accountDTO.getUsername());
		account.setFullName(accountDTO.getFullName());
		account.setEmail(accountDTO.getEmail());
		account.setAddress(accountDTO.getAddress());
		account.setPhone(accountDTO.getPhone());
		account.setRole(accountDTO.getRole());
		account.setAvatar(accountDTO.getAvatar());
		account.setPassword(accountDTO.getPassword());
		account.setStatus(accountDTO.getStatus());
		return account;
	}
	
	public AccountDTO toDTO(Account account) {
		if(account == null) return null;
		AccountDTO result = new AccountDTO();
		result.setAccountId(account.getAccountId());
		result.setFullName(account.getFullName());
		result.setUsername(account.getUsername());
		result.setPhone(account.getPhone());
		result.setRole(account.getRole());
		result.setAddress(account.getAddress());
		result.setStatus(account.getStatus());
		result.setAvatar(account.getAvatar());
		result.setPassword(account.getPassword());
		result.setDateCreated(account.getDateCreated());
		result.setEmail(account.getEmail());
		return result;
	}
	
	public List<Account> toEntityList(List<AccountDTO> listDTO){
		if(listDTO == null ) return null;
		List<Account> result = new ArrayList<Account>();
		for(AccountDTO accountDTO : listDTO) {
			result.add(toEntity(accountDTO));
		}
		return result;
	}
	public List<AccountDTO> toDTOList(List<Account> listAccount){
		if(listAccount == null) return null;
		List<AccountDTO> result = new ArrayList<AccountDTO>();
		for (Account account : listAccount) {
			result.add(toDTO(account));
		}
		return result;
	}
	
}
