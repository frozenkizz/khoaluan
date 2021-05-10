/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.common.constant.Constants;
import com.kopiko.entity.CategoryEntity;
import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.model.ResponseModel;
import com.kopiko.repository.ICategoryRepository;
import com.kopiko.repository.IPaymentMethodRepository;
import com.kopiko.service.IPaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService {

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;

	@Override
	public ResponseModel addNewPaymentMethod(PaymentMethodEntity paymentMethodEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		try {
			if(paymentMethodRepository.findByPaymentMethodName(paymentMethodEntity.getPaymentMethodName()) != null) {
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				paymentMethodRepository.saveAndFlush(paymentMethodEntity);
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("Add new payment method " + e.getMessage());
		}
		return new ResponseModel(responseCode);
	}

	@Override
	public ResponseModel updatePaymentMethod(PaymentMethodEntity paymentMethodEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		try {
			if(findByPaymentMethodNameAndPaymentMethodIdNot(paymentMethodEntity.getPaymentMethodName(), paymentMethodEntity.getPaymentMethodId()) != null) {
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				paymentMethodRepository.saveAndFlush(paymentMethodEntity);
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("Update new payment method " + e.getMessage());
		}
		return new ResponseModel(responseCode);
	}

	@Override
	public List<PaymentMethodEntity> getListPaymentMethod() {
		return paymentMethodRepository.findAll();
	}

	@Override
	public void deletePaymentMethod(Long paymentMethodId) {
		paymentMethodRepository.deleteById(paymentMethodId);
	}

	@Override
	public PaymentMethodEntity findPaymentMethodById(Long paymentMethodId) {
		return paymentMethodRepository.findByPaymentMethodId(paymentMethodId);
	}

	@Override
	public List<PaymentMethodEntity> findAllPaymentMethodWithPage(int pageNumber) {
		List<PaymentMethodEntity> listPayment = new ArrayList<PaymentMethodEntity>();
		return listPayment;
	}

	@Override
	public List<PaymentMethodEntity> findByPaymentMethodNameContaining(String paymentMethodName) {
		return paymentMethodRepository.findByPaymentMethodNameContaining(paymentMethodName);
	}

	/**
	 * Test get info Object for home page
	 * 
	 * @return
	 */
	@Autowired
	private ICategoryRepository categoryRepository;
	@Override
	public ResponseModel getInfoHomePage() {
		Map<String, Object> responseMap = new HashMap<>();
		List<PaymentMethodEntity> paymentMethods = paymentMethodRepository.findAll();
		List<CategoryEntity> categories = categoryRepository.findAll();
		responseMap.put("paymentMethodList", paymentMethods);
		responseMap.put("categoryList", categories);
		return new ResponseModel(responseMap, 100);
	}

	@Override
	public PaymentMethodEntity findByPaymentMethodNameAndPaymentMethodIdNot(String paymentMethodName,
			Long paymentMethodId) {
		return paymentMethodRepository.findByPaymentMethodNameAndPaymentMethodIdNot(paymentMethodName, paymentMethodId);
  }
  
  @Override
	public List<PaymentMethodEntity> findAll() {
		return paymentMethodRepository.findAll();
	}

}
