package com.kopiko.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kopiko.entity.ProductImage;

@Service
public interface IProductImageService {
	List<ProductImage> findAll();
	ProductImage insert(ProductImage productImage);
	ProductImage update(ProductImage productImage);
	boolean delete(Long id);
	ProductImage findByProductImageId(Long id);
	List<ProductImage> findAllByProductId(Long productId);
	ProductImage save(ProductImage image);
}
