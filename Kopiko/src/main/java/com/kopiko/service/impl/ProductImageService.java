package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.entity.ProductImage;
import com.kopiko.repository.IProductImageRepository;
import com.kopiko.service.IProductImageService;

@Service
public class ProductImageService implements IProductImageService{
	@Autowired
	private IProductImageRepository productImageRepository;

	@Override
	public List<ProductImage> findAll() {
		return (List<ProductImage>) productImageRepository.findAll();
	}

	@Override
	public ProductImage insert(ProductImage productImage) {
		return productImageRepository.saveAndFlush(productImage);
	}

	@Override
	public ProductImage update(ProductImage productImage) {
		return productImageRepository.save(productImage);
	}

	@Override
	public boolean delete(Long id) {
		productImageRepository.deleteById(id);
		return true;
	}

	@Override
	public ProductImage findByProductImageId(Long id) {
		ProductImage result = productImageRepository.findByProductImageId(id);
		return result;
	}

	@Override
	public List<ProductImage> findAllByProductId(Long productId) {
		return productImageRepository.findAllByProductProductId(productId);
	}

	@Override
	public ProductImage save(ProductImage image) {
		ProductImage data;
		if(image.getProductImageId() == null)  data = image; // đang tạo mới dữ liệu
		else { // đang update dữ liệu
			data = productImageRepository.findByProductImageId(image.getProductImageId()); // lấy dữ liệu cũ ra
			if(image.getImageUrl() != null) data.setImageUrl(image.getImageUrl());
			else return null;
			if(image.getProduct() != null && image.getProduct().getProductId() != null) data.setProduct(image.getProduct());
			else return null;
		}
		return productImageRepository.saveAndFlush(data);
	}
}
