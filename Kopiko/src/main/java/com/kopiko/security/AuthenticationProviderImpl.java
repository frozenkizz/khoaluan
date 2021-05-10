package com.kopiko.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kopiko.entity.Account;
import com.kopiko.service.IAccountService;

@Service
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	IAccountService accountService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		System.out.println("AuthenticationProviderImpl - authenticate - userName : " + userName);

		Account account = accountService.findByUsername(userName);
		if (account != null) {
			List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole().toUpperCase());
			grantList.add(authority);

			String password = bCryptPasswordEncoder.encode(account.getPassword());
			UserDetails userDetails = (UserDetails) new User(userName, password, grantList);

			UsernamePasswordAuthenticationToken obj = new UsernamePasswordAuthenticationToken(userDetails, null,
					grantList);
			return obj;
		} else
			throw new UsernameNotFoundException("User not found!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// Config các loại AuthencationToken sẽ được support.
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
