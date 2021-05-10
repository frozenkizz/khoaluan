package com.kopiko.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kopiko.entity.ProductDetail;
import com.kopiko.repository.IProductDetailRepository;
import com.kopiko.service.IProductDetailService;

@Service
public class ProductDetailService implements IProductDetailService {
	@Autowired
	private IProductDetailRepository productDetailRepository;

	@Override
	public List<ProductDetail> findAll() {
		return productDetailRepository.findAll();
	}

	@Override
	public ProductDetail insert(ProductDetail productDetail) {
		return productDetailRepository.saveAndFlush(productDetail);
	}
	
	
	public ProductDetail save(ProductDetail productDetail) {
		if (productDetail == null)
			return null;
		ProductDetail data;
		if (productDetail.getProductDetailId() == null) {
			if (productDetail.getProduct() == null)
				return null;
			if (productDetail.getQuantity() == null || productDetail.getQuantity() < 0)
				return null;
			if (productDetail.getSize() == null)
				return null;
			else
				data = productDetail;
		} else { // update
			data = productDetailRepository.findByProductDetailId(productDetail.getProductDetailId());
			if (productDetail.getProduct() != null)
				data.setProduct(productDetail.getProduct());
			if (productDetail.getQuantity() != null && productDetail.getQuantity() >= 0)
				data.setQuantity(productDetail.getQuantity());
			if (productDetail.getSize() != null)
				data.setSize(productDetail.getSize());
		}
		return productDetailRepository.saveAndFlush(data);
	}

	@Override
	public boolean delete(Long id) {
		productDetailRepository.deleteById(id);
		return true;
	}

	@Override
	public ProductDetail findByProductDetailId(Long id) {
		return productDetailRepository.findByProductDetailId(id);
	}

	@Override
	public List<ProductDetail> findAllBySize(String size) {
		return productDetailRepository.findAllBySize(size);
	}

	@Override
	public List<ProductDetail> findByProductProductId(Long Id) {
		if (Id == null)
			return null;
		else {
			return productDetailRepository.findAllByProductProductId(Id);
		}
	}

	@Override
	public ProductDetail findByProductIdAndSize(Long productId, String size) {
		return productDetailRepository.findByProductIdAndSize(productId, size);
	}
}
