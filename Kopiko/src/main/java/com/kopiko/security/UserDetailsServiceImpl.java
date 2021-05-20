package com.kopiko.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kopiko.entity.Account;
import com.kopiko.service.IAccountService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired(required = false)
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	IAccountService accountService;

	@Override
	// Cần lấy :
	// + thông tin password để FW so sánh với password đang submit lên
	// + lấy các Quyền để FW check authorization
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		System.out.println("UserDetailsServiceImpl - loadUserByUsername - userName : " + userName);
		Account account = accountService.findByUsername(userName);
		if (account != null) {
			List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole().toUpperCase());
			grantList.add(authority);

			String password = bCryptPasswordEncoder.encode(account.getPassword());
			UserDetails userDetails = (UserDetails) new User(userName, password, grantList);
			return userDetails;
		} else
			throw new UsernameNotFoundException("User not found!");
	}
}
