package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.common.constant.Constants;
import com.kopiko.entity.Brand;
import com.kopiko.model.ResponseModel;
import com.kopiko.repository.IBrandRepository;
import com.kopiko.service.IBrandService;

@Service
public class BrandService implements IBrandService{

	@Autowired
	private IBrandRepository brandRepository;
	
	@Override
	public List<Brand> findAllBrand() {
		return brandRepository.findAll();
	}

	@Override
	public ResponseModel addNewBrand(Brand brandEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		try {
			if(brandRepository.findByBrandName(brandEntity.getBrandName()) != null) {
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				
				brandRepository.saveAndFlush(brandEntity);
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("Add new brand error! " + e.getMessage());
		}
		return new ResponseModel(responseCode);
	}

	@Override
	public ResponseModel updateBrand(Brand brandEntity) {
		int responseCode = Constants.RESULT_CD_FAIL;
		try {
			if(brandRepository.findByBrandNameAndBrandIdNot(brandEntity.getBrandName(), brandEntity.getBrandId()) != null) {
				responseCode = Constants.RESULT_CD_DUPL;
			} else {
				brandRepository.saveAndFlush(brandEntity);
				responseCode = Constants.RESULT_CD_SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("Update brand failed " + e.getMessage());
		}
		return new ResponseModel(responseCode);
	}
  
	public Brand findByBrandId(Long brandId) {
		return brandRepository.findByBrandId(brandId);
	}

	@Override
	public ResponseModel deleteBrandById(Long brandId) {
		int responseCode = Constants.RESULT_CD_FAIL;
		Brand brandEntity = brandRepository.findByBrandId(brandId);
		try {
			if(brandEntity != null) {
				brandRepository.deleteById(brandEntity.getBrandId());
				responseCode = Constants.RESULT_CD_SUCCESS;
			} else {
				System.out.println("Brand is not exist!");
			}
		} catch (Exception e) {
			System.out.println("Delete brand failed!" + e.getMessage());
		}
		return new ResponseModel(responseCode);
	}

	@Override
	public Brand findByBrandName(String brandName) {
		return brandRepository.findByBrandName(brandName);
	}

	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}
	
}
