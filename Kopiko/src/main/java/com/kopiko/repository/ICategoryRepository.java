/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kopiko.entity.CategoryEntity;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {

	List<CategoryEntity> findByCategoryNameContaining(String categoryName);
	
	CategoryEntity findByCategoryId(Long categoryId);
	
	CategoryEntity findByCategoryName(String categoryName);
	
	CategoryEntity findByCategoryCode(String categoryCode);
	
	CategoryEntity findByCategoryCodeAndCategoryIdNot(String categoryCode, Long categoryId);
	
	CategoryEntity findByCategoryNameAndCategoryIdNot(String categoryName, Long categoryId);
	
	CategoryEntity findByCategoryCodeAndCategoryCodeNot(String categoryCode, String categoryName);
}
