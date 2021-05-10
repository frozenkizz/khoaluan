package com.kopiko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.Brand;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Long>{
	
	Brand findByBrandId(Long brandId);
	
	Brand findByBrandName(String brandName);
	
	Brand findByBrandNameAndBrandIdNot(String brandName, Long brandId);
}