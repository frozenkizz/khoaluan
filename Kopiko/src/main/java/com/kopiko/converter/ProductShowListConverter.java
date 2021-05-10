/**
 * 
 */
package com.kopiko.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kopiko.dto.ProductShowListDTO;
import com.kopiko.entity.Product;
import com.kopiko.entity.ProductImage;

/**
 * @author rianta9
 * @datecreated 20 thg 3, 2021 21:59:38
 */

@Component
public class ProductShowListConverter {
	public Product toEntity(ProductShowListDTO productShowListDTO) {
		return null;
	}

	public ProductShowListDTO toDTO(Product product) {
		ProductShowListDTO result = new ProductShowListDTO();
		result.setProductId(product.getProductId());
		result.setProductName(product.getProductName());
		List<ProductImage> listImage = product.getListProductImage();

		if (listImage != null && !listImage.isEmpty()) {
			ProductImage productImage = listImage.get(0);
			result.setImageUrl(productImage.getImageUrl());
		}
		result.setPrice(product.getPrice());
		result.setSalePrice(product.getSalePrice());

		return result;
	}

	/**
	 * 
	 *  Convert Product to ProductShowListDTO
	 *  
	 * @param products
	 * @return List<ProductShowListDTO>
	 * 
	 */
	public List<ProductShowListDTO> toProductShowListDTO(List<Product> products) {
		List<ProductShowListDTO> listProductShowList = new ArrayList<ProductShowListDTO>();
		try {
			if (products != null) {
				for (Product product : products) {
					listProductShowList.add(toDTO(product));
				}
			} else {
				System.out.println("Products is null");
			}
		} catch (Exception e) {
			System.out.println("Error when convert products to product show list DTO");
		}
		return listProductShowList;
	}
}