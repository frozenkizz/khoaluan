/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.service;

import java.util.List;

import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.model.ResponseModel;

public interface IPaymentMethodService {

	/**
	 * Get list payment method
	 * 
	 * @return ArrayList<PaymentMethodEntity>
	 */
	List<PaymentMethodEntity> getListPaymentMethod();

	/**
	 * Add new payment method
	 * 
	 * @param paymentMethodEntity
	 * @return PaymentMethodEntity
	 */
	ResponseModel addNewPaymentMethod(PaymentMethodEntity paymentMethodEntity);

	/**
	 * Update payment method
	 * 
	 * @param paymentMethodEntity
	 * @return PaymentMethodEntity
	 */
	ResponseModel updatePaymentMethod(PaymentMethodEntity paymentMethodEntity);

	/**
	 * Delete payment method
	 * 
	 * @param paymentMethodId
	 */
	void deletePaymentMethod(Long paymentMethodId);

	/**
	 * Find payment method by payment method Id
	 * 
	 * @param paymentMethodId
	 * @return
	 */
	PaymentMethodEntity findPaymentMethodById(Long paymentMethodId);

	
	List<PaymentMethodEntity> findAllPaymentMethodWithPage(int pageNumber);
	
	/**
	 * Search payment method by name
	 * 
	 * @param paymentMethodName
	 * @return  List<PaymentMethodEntity>
	 * 
	 */
	List<PaymentMethodEntity> findByPaymentMethodNameContaining(String paymentMethodName);
	
	/**
	 * 
	 * Find payment method by payment method name and not payment method id
	 * 
	 * @param paymentMethodName
	 * @param paymentMethodId
	 * 
	 */
	PaymentMethodEntity findByPaymentMethodNameAndPaymentMethodIdNot(String paymentMethodName, Long paymentMethodId);

	
	/**
	 * Test get info for Home page
	 * @return
	 */
	ResponseModel getInfoHomePage();

	/**
	 * @return
	 */
	List<PaymentMethodEntity> findAll();
}
