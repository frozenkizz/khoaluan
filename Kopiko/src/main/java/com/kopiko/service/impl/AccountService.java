package com.kopiko.service.impl;

import java.util.List;
//
//import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.common.constant.Constants;
import com.kopiko.entity.Account;
import com.kopiko.repository.IAccountRepository;
import com.kopiko.service.IAccountService;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository accountRepository;

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return accountRepository.findAll();
	}

	@Override
	public Account insert(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long accountId) {
		accountRepository.deleteById(accountId);
		return true;
	}

	/*
	 * Product data; if(product.getProductId() != null) { //update data =
	 * productRepository.getOne(product.getProductId()); if(product.getBrand() !=
	 * null) data.setBrand(product.getBrand()); if(product.getCategory() != null)
	 * data.setCategory(product.getCategory()); if(product.getProductName() != null)
	 * data.setProductName(product.getProductName()); if(product.getDescription() !=
	 * null) data.setDescription(product.getDescription()); if(product.getStatus()
	 * != null) data.setStatus(product.getStatus()); if(product.getListComment() !=
	 * null) data.setListComment(product.getListComment());
	 * if(product.getListProductDetail() != null)
	 * data.setListProductDetail(product.getListProductDetail());
	 * if(product.getListProductImage() != null)
	 * data.setListProductImage(product.getListProductImage()); } else data =
	 * product; return productRepository.saveAndFlush(data); }
	 */

	@Override
	public Account save(Account account) {
		Account data;
		if (account.getAccountId() == null) { // insert data
			if (checkIdentity(account.getUsername(), account.getEmail(), account.getPhone()) == false)
				return null;
			data = account;
		} else { // update data
			data = accountRepository.findByAccountId(account.getAccountId());
			if (account.getAddress() != null)
				data.setAddress(account.getAddress());
			if (account.getAvatar() != null)
				data.setAvatar(account.getAvatar());
			if (account.getEmail() != null
					&& accountRepository.findByEmailAndAccountIdNot(account.getEmail(), account.getAccountId()) == null)
				data.setEmail(account.getEmail());
			if (account.getFullName() != null)
				data.setFullName(account.getFullName());
			if (account.getPhone() != null
					&& accountRepository.findByPhoneAndAccountIdNot(account.getPhone(), account.getAccountId()) == null)
				data.setPhone(account.getPhone());
			if (account.getPassword() != null)
				data.setPassword(account.getPassword());
			if (account.getRole() != null)
				data.setRole(account.getRole());
			if (account.getStatus() != null)
				data.setStatus(account.getStatus());
		}
		return accountRepository.saveAndFlush(data);
	}

	@Override
	public Account findOne(Long accountId) {

		return accountRepository.getOne(accountId);
	}

	@Override
	public List<Account> search(String A) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account findByUsername(String userName) {
		// TODO Auto-generated method stub
		return accountRepository.findByUsername(userName);
	}

	@Override
	public boolean checkLogin(String username, String password) {
		Account result = accountRepository.findByUsernameAndPassword(username, password);
		if (result != null && result.getStatus() == Constants.Account.ACTIVE_STATUS)
			return true;
		return false;
	}

	@Override
	public boolean checkAdmin(String username, String password) {
		Account result = accountRepository.findByUsernameAndPassword(username, password);
		if (result != null && result.getRole().equalsIgnoreCase("admin")
				&& result.getStatus() == Constants.Account.ACTIVE_STATUS)
			return true;
		return false;
	}

	@Override
	public boolean checkUser(String username, String password) {
		Account result = accountRepository.findByUsernameAndPassword(username, password);
		if (result != null && result.getRole().equalsIgnoreCase("user")
				&& result.getStatus() == Constants.Account.ACTIVE_STATUS)
			return true;
		return false;
	}

	@Override
	public boolean delete(Account account) {
		return false;
	}

	public boolean checkIdentity(String username, String email, String phone) {
		if (username != null && accountRepository.findByUsername(username) != null)
			return false;
		if (email != null && accountRepository.findByEmail(email) != null)
			return false;
		if (phone != null && accountRepository.findByPhone(phone) != null)
			return false;
		return true;
	}

}
