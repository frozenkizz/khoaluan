package com.kopiko.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kopiko.common.constant.Constants;
import com.kopiko.converter.ProductShowListConverter;
import com.kopiko.entity.Product;
import com.kopiko.model.ITop12ProductSelling;
import com.kopiko.model.PageModel;
import com.kopiko.model.ResponseModel;
import com.kopiko.repository.IProductRepository;
import com.kopiko.service.IProductService;
import com.kopiko.model.Top12ProductSelling;
import com.kopiko.statistic.IProductStatistic;

@Service
public class ProductService implements IProductService{
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ProductShowListConverter productShowDetailConvert;
	
	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product insert(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public Product update(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public boolean delete(Long id) {
		productRepository.deleteById(id);
		return true;
	}

	@Override
	public Product findByProductId(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public List<Product> findAllByCategoryId(Long categoryId) {
		return productRepository.findAllByCategoryCategoryId(categoryId);
	}

	@Override
	public List<Product> findAllByBrandId(Long brandId) {
		return productRepository.findAllByBrandBrandId(brandId);
	}

	@Override
	public List<Product> findByProductNameIgnoreCaseContaining(String text) {
		return productRepository.findByProductNameIgnoreCaseContaining(text);
	}

	@Override
	public List<Product> findByProductSale() {
		return productRepository.findByProductSale();
	}

	@Override
	public List<Product> findAllBySalePrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return productRepository.findAllBySalePriceBetween(minPrice, maxPrice);
	}
	
	@Override
	public Product save(Product product) {
		Product data;
		if(product.getProductId() != null) { //update
			data = productRepository.getOne(product.getProductId());
			if(product.getBrand() != null) data.setBrand(product.getBrand());
			if(product.getCategory() != null) data.setCategory(product.getCategory());
			if(product.getProductName() != null) data.setProductName(product.getProductName());
			if(product.getDescription() != null) data.setDescription(product.getDescription());
			if(product.getStatus() != null) data.setStatus(product.getStatus());
			if(product.getPrice() != null) data.setPrice(product.getPrice());
			if(product.getSalePrice() != null) data.setSalePrice(product.getSalePrice());
			if(product.getListComment() != null) data.setListComment(product.getListComment());
			if(product.getListProductDetail() != null) data.setListProductDetail(product.getListProductDetail());
			if(product.getListProductImage() != null) data.setListProductImage(product.getListProductImage());
		}
		else data = product;
		return productRepository.saveAndFlush(data);
	}
	

	@Override
	public List<Product> searchProductByCategoryIdOrBrandId(Long id) {	
		return productRepository.searchProductByCategoryIdOrBrandId(id);
	}

	@Override
	public List<Product> searchProductOfCategoryByProductId(Long id) {
		return productRepository.searchProductOfCategoryByProductId(id);
	}

	// Search product by category id! trungns4
	@Override
	public List<Product> searchProductByCategoryId(Long id) {
		return productRepository.searchProductByCategoryId(id);
	}
	
	// Find all product with page
	@Override
	public ResponseModel findAllProductWithPage(int pageNumber) {
		int responseCode = Constants.RESULT_CD_FAIL;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Sort sortList = Sort.by(Sort.Direction.DESC, "productId");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortList);
			Page<Product> productPage = productRepository.findAll(pageable);
			responseMap.put("products",  productShowDetailConvert.toProductShowListDTO(productPage.getContent()));
			responseMap.put("paginationList", new PageModel(pageNumber, productPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			System.out.println("Error when get all product with page: " + e);
		}
		
		return new ResponseModel(responseMap, responseCode);
	}

	// Search product by category id with page
	@Override
	public ResponseModel searchProductByCategotyId(Long id, int pageNumber) {
		int responseCode = Constants.RESULT_CD_FAIL;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Sort sortList = Sort.by(Sort.Direction.DESC, "product_id");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sortList);
			Page<Product> productPage = productRepository.searchProductByCategoryIdWithPage(id, pageable);
			responseMap.put("products",  productShowDetailConvert.toProductShowListDTO(productPage.getContent()));
			responseMap.put("paginationList", new PageModel(pageNumber, productPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			System.out.println("Error when search product with page: " + e);
		}
		return new ResponseModel(responseMap, responseCode);
	}
	
	// Search product sale by price desc
	@Override
	public ResponseModel searchProductSalePriceDesc(int pageNumber) {
		int responseCode = Constants.RESULT_CD_FAIL;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Sort sort = Sort.by(Sort.Direction.DESC, "salePrice");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort);
			Page<Product> productPage = productRepository.findAll(pageable);
			responseMap.put("products", productShowDetailConvert.toProductShowListDTO(productPage.getContent()));
			responseMap.put("paginationList", new PageModel(pageNumber, productPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			System.out.println("Search product sale by price desc failed " + e);
		}
		return new ResponseModel(responseMap, responseCode);
	}

	// Search product sale by price asc
	@Override
	public ResponseModel searchProductSalePriceAsc(int pageNumber) {
		int responseCode = Constants.RESULT_CD_FAIL;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Sort sort = Sort.by(Sort.Direction.ASC, "salePrice");
			Pageable pageable = PageRequest.of(pageNumber - 1, Constants.PAGE_SIZE, sort);
			Page<Product> productPage = productRepository.findAll(pageable);
			responseMap.put("products", productShowDetailConvert.toProductShowListDTO(productPage.getContent()));
			responseMap.put("paginationList", new PageModel(pageNumber, productPage.getTotalPages()));
			responseCode = Constants.RESULT_CD_SUCCESS;
		} catch (Exception e) {
			System.out.println("Search product sale by price desc failed " + e);
		}
		return new ResponseModel(responseMap, responseCode);
	}


	@Override
	public List<IProductStatistic> getTop10SellingAllTime() {
		// TODO Auto-generated method stub
		return productRepository.getTop10SellingAllTime();
	}

	@Override
	public List<IProductStatistic> getTop10SellingByMonthAndYear(Integer month, Integer year) {
		// TODO Auto-generated method stub
		return productRepository.getTop10SellingByMonthAndYear(month, year);
	}

	@Override
	public List<ITop12ProductSelling> Selling(int month, int year) {
		return productRepository.Top12ProductSelling(month, year);
	}

	@Override
	public List<Product> HotNewMonth(Date dateStart, Date dateEnd) {
		return productRepository.HotNewMonth(dateStart, dateEnd);
	}




}