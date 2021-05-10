package com.kopiko.controller.admin.api;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.kopiko.entity.Product;
import com.kopiko.entity.ProductDetail;
import com.kopiko.service.IProductService;
import com.kopiko.service.impl.ProductDetailService;
import com.kopiko.util.RandomUUID;

@RestController
@RequestMapping("/api/v1/admin")
public class ControlProductDetailAPI implements ServletContextAware{
	@Autowired
	private IProductService productService;

//	@Autowired
//	private ProductConverter productConverter;

	private ServletContext servletContex;
	
	@Autowired
	private ProductDetailService productDetailService;
	
	
	@PostMapping("/product/{id}/detail")
	public String updateProductDetail(@PathVariable(name = "id") Long productId, @RequestBody ProductDetail productDetail) {
		if(productId != null && productDetail != null) {
			if(productDetail.getQuantity() == null|| productDetail.getQuantity() <= 0	  || productDetail.getSize() == null) return "Fail"; // dữ liệu lấy đc trống thì bỏ qua
			
			Product product = productService.findByProductId(productId); // tìm dữ liệu trong database
			if(product == null) return "Fail!";
						
			// tìm dữ liệu trong table ProductDetail theo size và productId
			ProductDetail findProductDetail = productDetailService.findByProductIdAndSize(productId,
					productDetail.getSize()); 
			if (findProductDetail == null) { //ko tồn tại size của product trong database -> create new productDetail
				findProductDetail = new ProductDetail();
				// đây là các dữ liệu cần thiết để tạo mới một productDetail
				findProductDetail.setProduct(productService.findByProductId(productId));
				findProductDetail.setQuantity(productDetail.getQuantity());
				findProductDetail.setSize(productDetail.getSize());
			}
			else { // tồn tại trong database -> update quantity
				findProductDetail.setQuantity(productDetail.getQuantity());
			}
			ProductDetail result = productDetailService.save(findProductDetail);
			if(result != null) return "Success!";
		}
		return "Fail!";
	}
	
	@DeleteMapping("/product/{id}/detail")
	public String deleteproductdetail(@PathVariable(name = "id") Long productId) {
		boolean result = productDetailService.delete(productId);
		if (result)
			return "Delete success!";
		return "Delete fail!";
	}

	@PostMapping(value = "/upload")
	public ResponseEntity<Void> upload(@RequestParam("files") MultipartFile[] files,
			@RequestParam("productName") String productName) {
		System.out.println("du lieu lay ve tu form:" + productName);
		try {
			System.out.println("File List:");
			for (MultipartFile file : files) {
				System.out.println("File name:" + file.getOriginalFilename());
				System.out.println("File size:" + file.getSize());
				System.out.println("File type:" + file.getContentType());
				save(file);
			}
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

		}
	}
	
	private String save(MultipartFile file) {
		try {
			String newFileName = file.getOriginalFilename();
			String extension = ".jpg";
			if (newFileName.contains(".png"))
				extension = ".png";
			else if (newFileName.contains(".jpeg"))
				extension = ".jpeg";
			newFileName = RandomUUID.getRandomID() + extension;
			byte[] bytes = file.getBytes();
			Path path = Paths.get(this.servletContex.getRealPath("/uploads/images/" + newFileName));
			System.out.println(path);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContex = servletContext;

	}
	
}
