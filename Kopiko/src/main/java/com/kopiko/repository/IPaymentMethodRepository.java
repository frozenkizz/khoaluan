/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.PaymentMethodEntity;

@Repository
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long>{

	PaymentMethodEntity findByPaymentMethodId(Long paymentMethodId);
	
	List<PaymentMethodEntity> findByPaymentMethodNameContaining(String paymentMethodName);
	
	PaymentMethodEntity findByPaymentMethodName(String paymentMethodName);
	
	PaymentMethodEntity findByPaymentMethodNameAndPaymentMethodIdNot(String paymentMethodName, Long paymentMethodId);
	
}
