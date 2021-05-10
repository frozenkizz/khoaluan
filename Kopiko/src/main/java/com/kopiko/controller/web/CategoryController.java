/**
 * 
 */
package com.kopiko.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.dto.ProductShowListDTO;
import com.kopiko.entity.CategoryEntity;
import com.kopiko.model.ResponseModel;
import com.kopiko.service.ICategoryService;
import com.kopiko.service.IProductService;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:20:21 Sản phẩm theo category
 */

@Controller
@RequestMapping("/search/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IProductService productService;

	@Autowired
	private ProductShowListConverter productShowListConvert;

	@GetMapping
	public String initPageCategory(Model model) {
		List<CategoryEntity> categories = categoryService.findAll();
		model.addAttribute("categories", categories);
		return "web/category";
	}

	@GetMapping(value = "/findAll")
	@ResponseBody
	public List<CategoryEntity> findAllCategory() {
		return categoryService.getAllCategory();
	}

	@GetMapping(value = "/{id}/products/{pageNumber}")
	@ResponseBody
	public ResponseModel searchProductByCategoryId(@PathVariable("id") Long id, @PathVariable("pageNumber") int pageNumber) {
		return productService.searchProductByCategotyId(id, pageNumber);
	}

	// Add new branch
	@GetMapping(value = "/findAllProduct")
	@ResponseBody
	public List<ProductShowListDTO> findAllProduct() {
		return productShowListConvert.toProductShowListDTO(productService.findAll());
	}

	@GetMapping(value = "/{pageNumber}")
	@ResponseBody
	public ResponseModel findAllProductWithPage(@PathVariable("pageNumber") int pageNumber) {
		return productService.findAllProductWithPage(pageNumber);
	}
	
	@GetMapping(value = "/desc/{pageNumber}")
	@ResponseBody
	public ResponseModel searchProductSalePriceDesc(@PathVariable("pageNumber") int pageNumber) {
		return productService.searchProductSalePriceDesc(pageNumber);
	}
	
	@GetMapping(value = "/asc/{pageNumber}")
	@ResponseBody
	public ResponseModel searchProductSalePriceAsc(@PathVariable("pageNumber") int pageNumber) {
		return productService.searchProductSalePriceAsc(pageNumber);
	}
	
//	@GetMapping(value = "/newProduct/{pageNumber}")
//	@ResponseBody
//	public ResponseModel searchProductNearestMonth(@PathVariable("pageNumber") int pageNumber) {
//		return productService.searchProductNearestMonth(pageNumber);
//	}
}