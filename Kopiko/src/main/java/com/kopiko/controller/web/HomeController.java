package com.kopiko.controller.web;

 import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.entity.Product;
import com.kopiko.model.ITop12ProductSelling;
import com.kopiko.service.ICategoryService;
import com.kopiko.service.impl.BrandService;
import com.kopiko.service.impl.ProductService;
import com.kopiko.util.DateTimeUtil;

@Controller
public class HomeController {

	@Autowired
	ProductShowListConverter proproductShowListConverter;
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value = {"/", "home"})
	public String homePage(Model model) {
		Date date = new Date();
		int month = date.getMonth() +1 ;
		int year = date.getYear() + 1900;
		
		 Calendar datesql = Calendar.getInstance();  
		  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
         String dateEndString = dateFormat.format(datesql.getTime()); 
         datesql.add(Calendar.MONTH, -1);
         String dateStartString = dateFormat.format(datesql.getTime()); 
         
         java.sql.Date dateEndSql = DateTimeUtil.toDateSQL(dateEndString);
         java.sql.Date dateStrartSql = DateTimeUtil.toDateSQL(dateStartString);
		List<ITop12ProductSelling> data = productService.Selling(month, year);
		if(data != null) System.out.println("Dư lieu 12 sp: " + data);
		List<Product> sellingProduct = new ArrayList<Product>();
		
		for(ITop12ProductSelling result : data) {
			sellingProduct.add(productService.findByProductId(result.getProductId()));	
		}
		
		List<Product> listNewProduct = productService.HotNewMonth(dateStrartSql, dateEndSql);
		List<Product> promotionalProducts = new ArrayList<Product>();
		promotionalProducts = productService.findByProductSale();
		model.addAttribute("promotionalProducts", proproductShowListConverter.toProductShowListDTO(promotionalProducts));
		model.addAttribute("listSelling",proproductShowListConverter.toProductShowListDTO(sellingProduct));
		model.addAttribute("listNewProduct", proproductShowListConverter.toProductShowListDTO(listNewProduct));
		return "web/home";
	}
	
	@RequestMapping(value = {"/search"})
	public String searchPage(Model model, @RequestParam String keyword) {
		System.out.println("Keyword:" + keyword);
		model.addAttribute("listcategory", categoryService.findAll());
		model.addAttribute("listBrand", brandService.findAll());
		if(keyword == null) keyword = ""; // Tìm tất cả
		List<Product> listProduct = productService.findAllProductByName(keyword); 
		System.out.println("Sl ket quả: " + listProduct.size());
		model.addAttribute("listProduct", listProduct);
		model.addAttribute("keyword", keyword);
		return "web/searchProduct";
	}
	
//	@RequestMapping(value = {"/search/sale"})
//	public String searchSalePage() {
//		return "web/search-sale";
//	}
	
	@RequestMapping(value = {"/search/sale"}) 
	public String searchSalePage(Model model) {
		model.addAttribute("listcategory", categoryService.findAll());
		model.addAttribute("listBrand", brandService.findAll());
		return "web/search-sale"; }
	
	@RequestMapping(value = {"/checkout/cart/zalopay"}) 
	public String zaloPayPage(Model model) {
//		model.addAttribute("listcategory", categoryService.findAll());
//		model.addAttribute("listBrand", brandService.findAll());
		return "web/payment_zalopay";
	}
	
	@RequestMapping(value = "/search/sale", method = RequestMethod.POST)
	public String getSearchPrice(@RequestParam("minPrice") BigDecimal minPrice, @RequestParam("maxPrice") BigDecimal maxPrice, Model model) {
		System.out.println(minPrice +"  " + maxPrice);
		List<Product> list = productService.findAllBySalePrice(minPrice, maxPrice);
		for (Product product : list) {
			System.out.println(product.toString());
		}
		model.addAttribute("list", list);
		model.addAttribute("listcategory", categoryService.findAll());
		model.addAttribute("listBrand", brandService.findAll());
		return "web/search-sale";
	}
	
	@RequestMapping(value = {"/checkout/payment/result"}) 
	public String PaymentResult(Model model) {
		/* return "web/payment-result"; */
		return "web/home";
	}
	

}
