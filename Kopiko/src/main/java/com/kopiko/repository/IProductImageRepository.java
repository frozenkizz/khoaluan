package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.ProductImage;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImage, Long>{

	ProductImage findByProductImageId(Long Id);
	List<ProductImage> findAllByProductProductId(Long productId);
}