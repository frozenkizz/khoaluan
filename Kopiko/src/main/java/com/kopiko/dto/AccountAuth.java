/**
 * 
 */
package com.kopiko.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author rianta9
 * @datecreated 21 thg 3, 2021 22:33:21
 * Dùng trong spring security thôi...sử dụng để đăng nhập hệ thống
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "roles", "authorities" })
@Data
@NoArgsConstructor
public class AccountAuth {
	private Long accountId;
	private String username;
	private String password;
	private String[] roles;

	public AccountAuth(Long accountId, String username, String password) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
		}
		return authorities;
	}
}
