/**
 * 
 * @author ADMIN
 * @date Mar 17, 2021
 */

package com.kopiko.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kopiko.entity.CategoryEntity;
import com.kopiko.entity.PaymentMethodEntity;
import com.kopiko.model.ResponseModel;
import com.kopiko.repository.ICategoryRepository;
import com.kopiko.repository.IPaymentMethodRepository;

public class HomeImpl {

	@Autowired
	private IPaymentMethodRepository paymentMethodRepository;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	public ResponseModel getInfoHomePage() {
		Map<String, Object> object = new HashMap<>();
		List<PaymentMethodEntity> paymentMethods =  paymentMethodRepository.findAll();
		List<CategoryEntity> categories = categoryRepository.findAll();
		object.put("paymentMethodList", paymentMethods);
		object.put("categoryList", categories);
		return new ResponseModel(object, 100);
	}
	
}
