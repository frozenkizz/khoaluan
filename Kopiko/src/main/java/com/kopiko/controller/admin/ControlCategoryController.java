///**
// * 
// */
//package com.kopiko.controller.admin;
//
//import java.util.List;
//
//import javax.websocket.server.PathParam;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.kopiko.entity.CategoryEntity;
//import com.kopiko.service.ICategoryService;
//
///**
// * @author rianta9
// * @datecreated 16 thg 3, 2021 10:50:42
// */
//
//@Controller
//@RequestMapping("/admin/category")
//public class ControlCategoryController {
//	@Autowired
//	private ICategoryService categoryService;
//	
//	@RequestMapping(value = "/admin/category/view")
//	public String viewCategoryList(Model model) {
//		List<CategoryEntity> list = categoryService.findAll();
//		model.addAttribute("list-category", list);
//		return "admin/category";
//	}
//	
//	@RequestMapping(value = "/admin/category/insert", method = RequestMethod.GET)
//	public String viewInsertCategory(Model model) {
//		model.addAttribute("category", new CategoryEntity());
//		return "admin/insert-category";
//	}
//	
//	@RequestMapping(value = "/admin/category/insert", method = RequestMethod.POST)
//	public String doInsertCategory(Model model, @ModelAttribute CategoryEntity newCategory) {
//		categoryService.insert(newCategory);
//		return "redirect:admin/insert-category";
//	}
//	
//	@RequestMapping(value = "/admin/category/update", method = RequestMethod.GET)
//	public String viewUpdateCategory(Model model) {
//		model.addAttribute("category", new CategoryEntity());
//		return "admin/update-category";
//	}
//	
//	@RequestMapping(value = "/admin/category/update", method = RequestMethod.PUT)
//	public String doUpdateCategory(Model model, @ModelAttribute CategoryEntity newCategory) {
//		categoryService.insert(newCategory);
//		return "redirect:admin/update-category";
//	}
//	
//	@RequestMapping(value = "admin/category/delete/{id}", method = RequestMethod.DELETE)
//	public String doDeleteCategory(Model model, @PathVariable Long id) {
//		return "redirect:admin/category/view";
//	}
//}
