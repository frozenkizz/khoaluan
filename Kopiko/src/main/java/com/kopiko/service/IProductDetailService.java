package com.kopiko.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.ProductDetail;

@Service
public interface IProductDetailService {
	List<ProductDetail> findAll();
	ProductDetail insert(ProductDetail productDetail);
	boolean delete(Long Id);
	ProductDetail findByProductDetailId(Long Id);
	List<ProductDetail> findAllBySize(String size);
	List<ProductDetail> findByProductProductId(Long Id);
	ProductDetail findByProductIdAndSize(Long poductId, String size);
	/**
	 * @param productDetail
	 * @return
	 */
	ProductDetail save(ProductDetail productDetail);
}
