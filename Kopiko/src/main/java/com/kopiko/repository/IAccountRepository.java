package com.kopiko.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kopiko.entity.Account;

public interface IAccountRepository extends JpaRepository<Account , Long>{

	Account findByAccountId(Long id);
//	   List<Employee> findByNameContaining(String A);

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
	@Query(value="select * from account as a where a.username=?1 and a.password=?2", nativeQuery=true)
	Account findByUsernameAndPassword(String username, String password);

	/**
	 * @param email
	 * @return
	 */
	Account findByEmail(String email);

	/**
	 * @param phone
	 * @return
	 */
	Account findByPhone(String phone);

	/**
	 * @param email
	 * @param accountId
	 * @return
	 */
	Account findByEmailAndAccountIdNot(String email, Long accountId);

	/**
	 * @param phone
	 * @param accountId
	 * @return
	 */
	Account findByPhoneAndAccountIdNot(String phone, Long accountId);
}