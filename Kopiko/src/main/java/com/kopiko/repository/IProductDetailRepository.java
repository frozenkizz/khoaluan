package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.ProductDetail;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
	List<ProductDetail> findAllByProductProductId(Long productId);

	ProductDetail findByProductDetailId(Long productDetailId);

	List<ProductDetail> findAllBySize(String size);

	/**
	 * @param productId
	 * @param size
	 * @return
	 */


	@Query(value = "select *from product_detail where product_id = ?1 and size = ?2", nativeQuery = true)
	ProductDetail findByProductIdAndSize(Long productId, String size);
}
