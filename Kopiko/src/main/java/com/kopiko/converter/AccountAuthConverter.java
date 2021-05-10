/**
 * 
 */
package com.kopiko.converter;

import org.springframework.stereotype.Component;

import com.kopiko.dto.AccountAuth;
import com.kopiko.entity.Account;

/**
 * @author rianta9
 * @datecreated 21 thg 3, 2021 22:33:46
 */
@Component
public class AccountAuthConverter {
	public Account toEntity(AccountAuth accountAuth) {
		return null;
	}
	
	public AccountAuth toDTO(Account account) {
		AccountAuth result = new AccountAuth();
		result.setAccountId(account.getAccountId());
		result.setPassword(account.getPassword());
		result.setUsername(account.getUsername());
		result.setRoles(new String[] {account.getRole()});
		
		return result;
	}
}
