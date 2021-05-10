/**
 * 
 * @author ADMIN
 * @date Mar 17, 2021
 */

package com.kopiko.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kopiko.entity.CategoryEntity;
import com.kopiko.model.ResponseModel;
import com.kopiko.service.ICategoryService;

@Controller
@RequestMapping(value = "/admin/category")
public class CategoryControllerAdmin {

	@Autowired
	private ICategoryService categoryService;

	@GetMapping("/list")
	public String initPage() {
		return "admin/category-admin";
	}
	
	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseModel addCategory(@ModelAttribute CategoryEntity categoryEntity) {
		return categoryService.addCategory(categoryEntity);
	}

	@RequestMapping(value = "/update", method = {RequestMethod.PUT, RequestMethod.POST})
	@ResponseBody
	public ResponseModel updateCategory(@ModelAttribute CategoryEntity categoryEntity) {
		return categoryService.updateCategory(categoryEntity);
	}

	@DeleteMapping(value = "/delete/{id}")
	@ResponseBody
	public String deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
		return "Delete Success";
	}
	
	@GetMapping(value = "/search/{keyword}")
	@ResponseBody
	public List<CategoryEntity> searchCategory(@PathVariable(value = "keyword") String categoryName) {
		return categoryService.searchCategoryByName(categoryName);
	}
	
	@GetMapping(value = "/view")
	@ResponseBody
	public List<CategoryEntity> getAllCategory() {
		return categoryService.getAllCategory();
	}
	
	@GetMapping(value = "/find/{id}")
	@ResponseBody
	public CategoryEntity findByCategoryId(@PathVariable("id") Long id) {
		return categoryService.findByCategoryId(id);
	}

}
