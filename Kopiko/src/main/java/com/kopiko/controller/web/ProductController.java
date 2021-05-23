/**
 * 
 */
package com.kopiko.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kopiko.entity.Product;
import com.kopiko.service.IProductService;


@Controller
public class ProductController {
	@Autowired
	private IProductService productService;
	
	
	@RequestMapping("/product/{id}")
	public String viewProductDetail(Model model, @PathVariable Long id) {
		Product product = productService.findByProductId(id);
		model.addAttribute("product", product);
		
		
		List<Product> listProductsOffer = productService.searchProductByCategoryIdOrBrandId(id);
		model.addAttribute("listProductsOffer", listProductsOffer); 
		
		List<Product> listProductsByCategory = productService.searchProductOfCategoryByProductId(id);
		model.addAttribute("listProductsByCategory", listProductsByCategory); 
		
		  //for (Product product2: listProductsOffer) {
		  //System.out.println("Các sản phẩm cùng loại:"+ product2.getProductId()+product2.getProductName()); }
		  
		return "web/product";
	}
			
}
