/**
 * 
 */
package com.kopiko.controller.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kopiko.converter.ProductConverter;
import com.kopiko.converter.ProductImageConverter;
import com.kopiko.dto.ProductImageDTO;
import com.kopiko.entity.Product;
import com.kopiko.entity.ProductDetail;
import com.kopiko.entity.ProductImage;
import com.kopiko.service.IBrandService;
import com.kopiko.service.ICategoryService;
import com.kopiko.service.IProductDetailService;
import com.kopiko.service.IProductImageService;
import com.kopiko.service.IProductService;
import com.kopiko.service.IStatusService;
import com.kopiko.util.RandomUUID;

/**
 * @author rianta9
 * @datecreated 16 thg 3, 2021 11:09:31
 */

@Controller
@RequestMapping("/admin")
public class ControlProductController {
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IBrandService brandService;
	
	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IStatusService statusService;
	
//	@Autowired
//	private ProductConverter productConverter;
	
	@Autowired
	private ProductImageConverter productImageConverter;
	
	@Autowired
	private IProductImageService productImageService;
	
	@Autowired
	private IProductDetailService productDetailService;
	
	@GetMapping("/product/list")
	public String getListProduct(Model model) {
		return "admin/list-product";
	}

	@GetMapping("/product/list/category/{id}")
	public String getListProductByCategoryId(Model model, @PathVariable(name = "id") Long categoryId) {
		model.addAttribute("categoryId", categoryId);
		return "admin/list";
	}

	@GetMapping("/product/list/brand/{id}")
	public String getListProductByBrandId(Model model, @PathVariable(name = "id") Long brandId) {
		model.addAttribute("brandId", brandId);
		return "admin/list";
	}

	@GetMapping("/product/new")
	public String insert(Model model) {
		model.addAttribute("listBrand", brandService.findAll());
		model.addAttribute("listCategory", categoryService.findAll());
		model.addAttribute("listStatus", statusService.findAll());
		return "admin/insert-product";
	}

	@GetMapping("/product/{id}/edit")
	public String update(Model model, @PathVariable(name = "id") Long productId) {
		model.addAttribute("productId", productId);
		model.addAttribute("productDTO", productImageConverter.toDTO(productService.findByProductId(productId)));
		model.addAttribute("listProductDetail", productDetailService.findByProductProductId(productId));
		model.addAttribute("listBrand", brandService.findAll());
		model.addAttribute("listCategory", categoryService.findAll());
		model.addAttribute("listStatus", statusService.findAll());
		
		return "admin/update-product";
	}
	
	//update product theo ID
	@PostMapping("/product/{id}/edit")
	public String doUpdate(Model model,  @PathVariable(name = "id") Long productId, @ModelAttribute ProductImageDTO productData, HttpServletRequest servletRequest) {
		System.out.println(productData.toString());
		productData.setListImgUrl(new ArrayList<String>());
		
		Product data = productService.findByProductId(productId); // lấy product từ database ra bằng productId
		try {
			System.out.println("File List:");
			for(MultipartFile file : productData.getImagesUrl()) {
				System.out.println("File name:" + file.getOriginalFilename());
				System.out.println("File size:" + file.getSize());
				System.out.println("File type:" + file.getContentType());
				String imgUrl = save(file, servletRequest);
				if(imgUrl != null) productData.getListImgUrl().add(imgUrl);
			}
			
			// Save ảnh vào database
			for (String imageUrl : productData.getListImgUrl()) {
				System.out.println(imageUrl);
				ProductImage image = new ProductImage();
				image.setImageUrl(imageUrl); // set imageUrl
				image.setProduct(data); // set product
				productImageService.save(image); // lưu dữ liệu vào database
			}
			
			// Save product vào database
			Product product = productImageConverter.toEntity(productData);
			productService.save(product);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String referer = servletRequest.getHeader("Referer");
        return "redirect:" + referer;
	}
	
	//insert product 
	
	@PostMapping("/product/insert")
	public String doInsert(Model model, @ModelAttribute ProductImageDTO productData, HttpServletRequest servletRequest) {
		System.out.println(productData.toString());
		productData.setListImgUrl(new ArrayList<String>());
		Product product = productImageConverter.toEntity(productData);
		product= productService.save(product);
		
		// thêm product detail có size mặc định
		ProductDetail productDetail = new ProductDetail();
		productDetail.setProduct(productService.findByProductId(product.getProductId()));
		productDetail.setSize("Mặc định");
		productDetail.setQuantity(0);
		productDetailService.insert(productDetail);
		
//		Product data = productService.findByProductId(productId); // lấy product từ database ra bằng productId
		try {
			System.out.println("File List:");
			for(MultipartFile file : productData.getImagesUrl()) {
				System.out.println("File name:" + file.getOriginalFilename());
				System.out.println("File size:" + file.getSize());
				System.out.println("File type:" + file.getContentType());
				String imgUrl = save(file, servletRequest);
				if(imgUrl != null) productData.getListImgUrl().add(imgUrl);
			}
			
			// Save ảnh vào database
			for (String imageUrl : productData.getListImgUrl()) {
				System.out.println(imageUrl);
				ProductImage image = new ProductImage();
				image.setImageUrl(imageUrl); // set imageUrl
				image.setProduct(productService.findByProductId(product.getProductId())); // set product
				productImageService.save(image); // lưu dữ liệu vào database
				
			}
			
			// Save product vào database
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/product/new";
	}
	
	private String save(MultipartFile file, HttpServletRequest servletRequest) {
		try {
			String newFileName = file.getOriginalFilename();
			String extension = ".jpg";
			if(newFileName.endsWith(".png")) extension = ".png";
			else if(newFileName.endsWith(".jpeg")) extension = ".jpeg";
			else if(!newFileName.endsWith(".jpg")) return null;
			newFileName = RandomUUID.getRandomID()+extension;
			byte[] bytes = file.getBytes();
			Path path = Paths.get(servletRequest.getServletContext().getRealPath("/uploads/images/") +  newFileName);
			System.out.println(path);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping("/product/{id}/view")
	public String viewProduct(Model model, @PathVariable(name = "id") Long productId) {
		model.addAttribute("productDTO", productService.findByProductId(productId));
		return "admin/view-product";
	}

	@DeleteMapping("/product/{id}/delete")
	public String delete(@PathVariable(name = "id") Long productId) {
		boolean result = productService.delete(productId);
		if (result)
			return "admin/product/list";
		return "admin/product/list";
	}
}
