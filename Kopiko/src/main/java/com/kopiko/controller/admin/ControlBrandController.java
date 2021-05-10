/**
 * 
 */
package com.kopiko.controller.admin;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kopiko.entity.Brand;
import com.kopiko.model.ResponseModel;
import com.kopiko.service.impl.BrandService;
import com.kopiko.util.FileHepler;

@Controller
@RequestMapping("/admin/brand")
public class ControlBrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping(value = "/list")
	public String initPageBrand(Model model) {
		return "admin/brand-admin";
	}

	@GetMapping(value = "/findAll")
	@ResponseBody
	public List<Brand> findAllBrand() {
		return brandService.findAllBrand();
	}

	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseModel addNewBrand(@ModelAttribute Brand brandEntity, HttpServletRequest servletRequest) {
		MultipartFile file = brandEntity.getImageFile();
		brandEntity.setBrandImgUrl(FileHepler.saveImage(file, servletRequest));
		return brandService.addNewBrand(brandEntity);
	}

	@RequestMapping(value = "/update", method = { RequestMethod.PUT, RequestMethod.POST })
	@ResponseBody
	public ResponseModel updateBrand(@ModelAttribute Brand brandEntity, HttpServletRequest servletRequest) {
		MultipartFile file = brandEntity.getImageFile();
		if (file != null && file.getSize() > 0) {
			brandEntity.setBrandImgUrl(FileHepler.saveImage(file, servletRequest));
		}
		return brandService.updateBrand(brandEntity);
	}

	@DeleteMapping(value = "/delete/{id}")
	@ResponseBody
	public ResponseModel deleteBrand(@PathVariable(value = "id") Long id, HttpServletRequest servletRequest) {
		Brand brand = brandService.findByBrandId(id);
		if(brand != null) {
			FileHepler.deleteFile(brand.getBrandImgUrl(), servletRequest);
		}
		return brandService.deleteBrandById(brand.getBrandId());
	}

	@GetMapping(value = "/find/{id}")
	@ResponseBody
	public Brand findByBrandId(@PathVariable(value = "id") Long id) {
		return brandService.findByBrandId(id);
	}
}
