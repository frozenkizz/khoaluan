/**
 * 
 */
package com.kopiko.controller.admin.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopiko.converter.ProductConverter;
import com.kopiko.dto.ProductDTO;
import com.kopiko.entity.Product;
import com.kopiko.service.IProductService;
import com.kopiko.service.impl.ProductDetailService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:09:31
 */

@RestController
@RequestMapping("/api/v1/admin")
public class ControlProductAPI {
	@Autowired
	private IProductService productService;

	@Autowired
	private ProductConverter productConverter;
//
//	private ServletContext servletContex;

	@Autowired
	private ProductDetailService productDetailService;

	@GetMapping("/product/list")
	public List<ProductDTO> getListProduct() {
		return productConverter.toDTOList(productService.findAll());
	}

	@GetMapping("/product/list/category/{id}")
	public List<ProductDTO> getListProductByCategoryId(@PathVariable(name = "id") Long categoryId) {
		return productConverter.toDTOList(productService.findAllByCategoryId(categoryId));
	}

	@GetMapping("/product/list/brand/{id}")
	public List<ProductDTO> getListProductByBrandId(@PathVariable(name = "id") Long brandId) {
		return productConverter.toDTOList(productService.findAllByBrandId(brandId));
	}

	@GetMapping("/product/{id}")
	public ProductDTO get(@PathVariable(name = "id") Long productId) {
		Product product = productService.findByProductId(productId);
		return productConverter.toDTO(product);
	}

	@PostMapping("/product")
	public ProductDTO insert(@RequestBody ProductDTO productDTO) {
		if (productDTO != null)
			System.out.println(productDTO.toString());
		else
			System.out.println("Get data null!");
		Product product = productConverter.toEntity(productDTO);
		product = productService.save(product);
		return productConverter.toDTO(product);
	}

	@DeleteMapping("/product/{id}")
	public String delete(@PathVariable(name = "id") Long productId) {
		boolean result = productService.delete(productId);
		if (result)
			return "Delete success!";
		return "Delete fail!";
	}
	
}