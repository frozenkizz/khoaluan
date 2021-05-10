/**
 * 
 */
package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kopiko.dto.ProductDTO;
import com.kopiko.entity.Brand;
import com.kopiko.entity.CategoryEntity;
import com.kopiko.entity.Product;
import com.kopiko.service.IBrandService;
import com.kopiko.service.ICategoryService;

/**
 * @author rianta9
 * @datecreated 20 thg 3, 2021 21:48:04
 */

@Component
public class ProductConverter {
	@Autowired
	ICategoryService categoryService;
	@Autowired
	IBrandService brandService;
	
	public Product toEntity(ProductDTO productDTO) {
		if(productDTO == null) return null;
		Product result = new Product();
		CategoryEntity category = categoryService.findOne(productDTO.getCategoryId());
		Brand brand = brandService.findByBrandId(productDTO.getBrandId());
		
		result.setProductId(productDTO.getProductId());
		result.setProductName(productDTO.getProductName());
		result.setBrand(brand);
		result.setDateCreated(productDTO.getDateCreated());
		result.setCategory(category);
		result.setPrice(productDTO.getPrice());
		result.setSalePrice(productDTO.getSalePrice());
		result.setDescription(productDTO.getDescription());
		result.setStatus(productDTO.getStatus());
		return result;
	}
	
	public ProductDTO toDTO(Product product) {
		if(product == null) return null;
		ProductDTO result = new ProductDTO();
		result.setProductId(product.getProductId());
		result.setProductName(product.getProductName());
		result.setDateCreated(product.getDateCreated());
		result.setBrandId(product.getBrand().getBrandId());
		result.setCategoryId(product.getCategory().getCategoryId());
		result.setPrice(product.getPrice());
		result.setSalePrice(product.getSalePrice());
		result.setDescription(product.getDescription());
		result.setStatus(product.getStatus());
		return result;
	}
	
	public List<Product> toEntityList(List<ProductDTO> listDTO){
		if(listDTO == null) return null;
		List<Product> result = new ArrayList<Product>();
		for (ProductDTO productDTO : listDTO) {
			result.add(toEntity(productDTO));
		}
		return result;
	}
	
	public List<ProductDTO> toDTOList(List<Product> listEntity){
		if(listEntity == null) return null;
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		for (Product product : listEntity) {
			result.add(toDTO(product));
		}
		return result;
	}
}
