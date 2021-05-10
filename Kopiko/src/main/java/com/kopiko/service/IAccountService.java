package com.kopiko.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.Account;

@Service
public interface IAccountService {

List<Account>findAll();
Account findOne(Long accountId);
Account insert(Account account);
Account update(Account account);
List<Account> search(String A);
boolean delete(Long accountId);
boolean delete(Account account);
Account save(Account account);
/**
 * @param userName
 * @return
 */
Account findByUsername(String userName);
/**
 * @param username
 * @param password
 * @return
 */
boolean checkLogin(String username, String password);
/**
 * @param username
 * @param password
 * @return
 */
boolean checkAdmin(String username, String password);
/**
 * @param username
 * @param password
 * @return
 */
boolean checkUser(String username, String password);
/**
 * @param username
 * @param email
 * @param phone
 * @return
 */
boolean checkIdentity(String username, String email, String phone);	
}


