/**
 * 
 */
package com.kopiko.controller.admin.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kopiko.service.IProductService;
import com.kopiko.statistic.IProductStatistic;
import com.kopiko.statistic.ProductStatistic;

/**
 * @author rianta9
 * @datecreated 30 thg 3, 2021 22:02:44
 */

@RestController
@RequestMapping("/api/v1/admin")
public class ProductStatisticAPI {
	@Autowired
	private IProductService productService;
	
	@GetMapping("/static/product/top10alltime")
	public List<ProductStatistic> getTop10SellingAllTime(){
		List<IProductStatistic> listData = productService.getTop10SellingAllTime();
		List<ProductStatistic> result = new ArrayList<ProductStatistic>();
		for (IProductStatistic data : listData) {
			ProductStatistic productStatic = new ProductStatistic();
			productStatic.setProductId(data.getProductId());
			productStatic.setProductName(data.getProductName());
			productStatic.setQuantityOfProduct(data.getQuantityOfProduct());
			result.add(productStatic);
		}
		return result;
	}
}
