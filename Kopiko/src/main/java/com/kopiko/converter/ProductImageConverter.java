package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kopiko.dto.ProductImageDTO;
import com.kopiko.entity.Brand;
import com.kopiko.entity.CategoryEntity;
import com.kopiko.entity.Product;
import com.kopiko.entity.ProductImage;
import com.kopiko.service.IBrandService;
import com.kopiko.service.ICategoryService;
import com.kopiko.service.IProductService;

@Component
public class ProductImageConverter {
	
	@Autowired
	ICategoryService categoryService;
	
	@Autowired
	IBrandService brandService;
	
	@Autowired
	IProductService productService;
	
	public Product toEntity(ProductImageDTO productImageDTO) {
		if(productImageDTO == null) return null;
		Product result = new Product();
		CategoryEntity category = categoryService.findOne(productImageDTO.getCategoryId());
		Brand brand = brandService.findByBrandId(productImageDTO.getBrandId());
		result.setProductId(productImageDTO.getProductId());
		result.setProductName(productImageDTO.getProductName());
		result.setBrand(brand);
		result.setPrice(productImageDTO.getPrice());
		result.setSalePrice(productImageDTO.getSalePrice());
		result.setDateCreated(productImageDTO.getDateCreated());
		result.setCategory(category);
		result.setDescription(productImageDTO.getDescription());
		result.setStatus(productImageDTO.getStatus());
		return result;
	}
	
	public List<Product> toEntityList(List<ProductImageDTO> listDTO){
		if(listDTO == null) return null;
		List<Product> result = new ArrayList<Product>();
		for (ProductImageDTO productDTO : listDTO) {
			result.add(toEntity(productDTO));
		}
		return result;
	}

	public ProductImageDTO toDTO(Product  product) {
		if(product == null) return null;
		ProductImageDTO result = new ProductImageDTO();
		result.setProductId(product.getProductId());
		result.setProductName(product.getProductName());
		result.setDateCreated(product.getDateCreated());
		result.setBrandId(product.getBrand().getBrandId());
		result.setPrice(product.getPrice());
		result.setSalePrice(product.getSalePrice());
		result.setCategoryId(product.getCategory().getCategoryId());
		result.setDescription(product.getDescription());
		result.setStatus(product.getStatus());
		List<String> listImageUrl =  new ArrayList<String>();
		for (ProductImage productImage : product.getListProductImage()) {
			listImageUrl.add(productImage.getImageUrl());
		}
		result.setListImgUrl(listImageUrl);
		return result;
	}
}
