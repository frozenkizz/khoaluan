/**
 * 
 */
package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kopiko.dto.AccountCustomerDTO;
import com.kopiko.entity.Account;
import com.kopiko.service.IBrandService;
import com.kopiko.service.ICategoryService;

/**
 * @author rianta9
 * @datecreated 26 thg 3, 2021 16:10:31
 */

@Component
public class AccountCustomerConverter {
	@Autowired
	ICategoryService categoryService;
	@Autowired
	IBrandService brandService;

	public Account toEntity(AccountCustomerDTO accountDTO) {
		if(accountDTO == null ) return null;
		Account account = new Account();
		account.setAccountId(accountDTO.getAccountId());
		account.setUsername(accountDTO.getUsername());
		account.setFullName(accountDTO.getFullName());
		account.setAddress(accountDTO.getAddress());
		account.setPhone(accountDTO.getPhone());
		account.setEmail(accountDTO.getEmail());
		account.setAvatar(accountDTO.getAvatar());
		return account;
	}
	
	public AccountCustomerDTO toDTO(Account account) {
		if(account == null) return null;
		AccountCustomerDTO result = new AccountCustomerDTO();
		result.setAccountId(account.getAccountId());
		result.setFullName(account.getFullName());
		result.setUsername(account.getUsername());
		result.setPhone(account.getPhone());
		result.setEmail(account.getEmail());
		result.setAddress(account.getAddress());
		result.setAvatar(account.getAvatar());
		result.setRole(account.getRole());
		return result;
	}
	
	public List<Account> toEntityList(List<AccountCustomerDTO> listDTO){
		if(listDTO == null ) return null;
		List<Account> result = new ArrayList<Account>();
		for(AccountCustomerDTO accountDTO : listDTO) {
			result.add(toEntity(accountDTO));
		}
		return result;
	}
	public List<AccountCustomerDTO> toDTOList(List<Account> listAccount){
		if(listAccount == null) return null;
		List<AccountCustomerDTO> result = new ArrayList<AccountCustomerDTO>();
		for (Account account : listAccount) {
			result.add(toDTO(account));
		}
		return result;
	}
}
