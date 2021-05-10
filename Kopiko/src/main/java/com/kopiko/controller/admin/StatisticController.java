/**
 * 
 */
package com.kopiko.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kopiko.service.IOrderService;
import com.kopiko.service.IProductService;
import com.kopiko.statistic.IOrderStatistic;
import com.kopiko.statistic.IProductStatistic;
import com.kopiko.statistic.IRevenueStatistic;
import com.kopiko.statistic.ProductStatistic;

/**
 * @author rianta9
 * @datecreated 30 thg 3, 2021 22:18:35
 */

@Controller
@RequestMapping("/admin/statistic")
public class StatisticController {
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IOrderService orderService;
	
	@GetMapping("/product")
	public String viewStatisticProduct(Model model) {
		List<IProductStatistic> listData = productService.getTop10SellingAllTime();
		List<ProductStatistic> result = new ArrayList<ProductStatistic>();
		for (IProductStatistic data : listData) {
			ProductStatistic productStatic = new ProductStatistic();
			productStatic.setProductId(data.getProductId());
			productStatic.setProductName(data.getProductName());
			productStatic.setQuantityOfProduct(data.getQuantityOfProduct());
			result.add(productStatic);
		}
		model.addAttribute("title", "Thống Kê Top 10 Sản Phẩm Bán Chạy Nhất Mọi Thời Gian");
		model.addAttribute("listProductStatistic", result);
		return "admin/statistic-product";
	}
	
	@GetMapping("/product/filter")
	public String doStatisticProduct(Model model, @RequestParam Integer month, @RequestParam Integer year, RedirectAttributes redirectAttributes) {
		if(month != null && year != null) {
			List<IProductStatistic> listData = productService.getTop10SellingByMonthAndYear(month, year);
			List<ProductStatistic> result = new ArrayList<ProductStatistic>();
			for (IProductStatistic data : listData) {
				ProductStatistic productStatic = new ProductStatistic();
				productStatic.setProductId(data.getProductId());
				productStatic.setProductName(data.getProductName());
				productStatic.setQuantityOfProduct(data.getQuantityOfProduct());
				result.add(productStatic);
			}
			model.addAttribute("title", "Thống Kê Top 10 Sản Phẩm Bán Chạy Trong " + month + "/" + year);
			model.addAttribute("listProductStatistic", result);
		}
		return "forward:/admin/statistic/product1";
	}
	
	@GetMapping("/product1")
	public String viewStatisticProductFilter(Model model) {
		return "admin/statistic-product";
	}
	
	
	@RequestMapping("/order")
	public String viewStatisticOrder(Model model, @RequestParam(required = false) Integer year) {
		List<IOrderStatistic> listOrderStatisticData;
		List<IRevenueStatistic> listRevenueStatisticData;
		if(year != null) {
			model.addAttribute("titleOrder", "Thống Kê Số Lượng Đơn Hàng Trong Năm " + year);
			model.addAttribute("titleRevenue", "Thống Kê Doanh Thu Hàng Tháng Trong Năm " + year);
			listOrderStatisticData = orderService.getListQuantityOfOrderByYear(year);
			listRevenueStatisticData = orderService.getListRevenueOfOrderByYear(year);
			
		}
		else {
			Date date = new Date();
			model.addAttribute("titleOrder", "Thống Kê Số Lượng Đơn Hàng Trong Năm " + (date.getYear()+1900));
			model.addAttribute("titleRevenue", "Thống Kê Doanh Thu Hàng Tháng Trong Năm " + (date.getYear()+1900));
			listOrderStatisticData = orderService.getListQuantityOfOrderByYear(date.getYear()+1900);
			listRevenueStatisticData = orderService.getListRevenueOfOrderByYear(date.getYear()+1900);
		}
		List<Integer> listOrderStatisticResult = new ArrayList<Integer>();
		List<Integer> listRevenueStatisticResult = new ArrayList<Integer>();
		Integer maxQuantityOfOrderValue = 0, maxRevenueValue = 0;
		
		// Lưu số lượng order mỗi tháng vào mảng
		for (IOrderStatistic data : listOrderStatisticData) {
			listOrderStatisticResult.add(data.getQuantityOfOrder());
			maxQuantityOfOrderValue = Math.max(maxQuantityOfOrderValue, data.getQuantityOfOrder());
		}
		
		// Lưu doanh số mỗi tháng vào mảng
		for (IRevenueStatistic data : listRevenueStatisticData) {
			listRevenueStatisticResult.add(data.getTotalOfSalePrice());
			if(data.getTotalOfSalePrice() == null) System.out.println("data null");
			else maxRevenueValue = Math.max(maxRevenueValue, data.getTotalOfSalePrice());
		}
		
		
		model.addAttribute("listOrderStatistic", listOrderStatisticResult);
		model.addAttribute("listRevenueStatistic", listRevenueStatisticResult);
		model.addAttribute("maxQuantityOfOrderValue", maxQuantityOfOrderValue);
		model.addAttribute("maxRevenueValue", maxRevenueValue);
		return "admin/statistic-order";
	}
}
