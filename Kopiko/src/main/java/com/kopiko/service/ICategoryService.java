/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.service;

import java.util.List;

import com.kopiko.entity.CategoryEntity;
import com.kopiko.model.ResponseModel;

public interface ICategoryService {

	CategoryEntity insert(CategoryEntity categoryEntity);
	CategoryEntity update(CategoryEntity categoryEntity);
	boolean delete(Long id);
	List<CategoryEntity> findAll();
	/**
	 * @param categoryId
	 * @return
	 */
	CategoryEntity findOne(Long categoryId);
	/**
	 * Add new category entity
	 * 
	 * @param categoryEntity
	 * @return new categoryEntity
	 * 
	 */
	ResponseModel addCategory(CategoryEntity categoryEntity);
	
	/**
	 * Update category
	 * 
	 * @param categoryEntity
	 * @return categoryEntity
	 * 
	 */
	ResponseModel updateCategory(CategoryEntity categoryEntity);
	
	/**
	 * Delete category
	 * @param categortId
	 * 
	 */
	void deleteCategory(Long categortId);
	
	/**
	 * Search category by name
	 * 
	 * @param categoryName
	 * @return List<CategoryEntity>
	 * 
	 */
	List<CategoryEntity> searchCategoryByName(String categoryName);
	
	/**
	 * Get all category
	 * @return
	 */
	List<CategoryEntity> getAllCategory();

	/**
	 * 
	 * Find category by id
	 * 
	 * @param categoryId
	 * @return CategoryEntity
	 * 
	 */
	CategoryEntity findByCategoryId(Long categoryId);
	
	/**
	 * 
	 * Find category by category name 
	 * 
	 * 
	 * @param categoryName
	 * @return CategoryEntity
	 * 
	 */
	CategoryEntity findByCategoryName(String categoryName);
	
	/**
	 * Find category by category code
	 *
	 * @param categoryCode
	 * @return CategoryEntity
	 * 
	 */
	CategoryEntity findByCategoryCode(String categoryCode);
	
	/**
	 * 
	 * Find category by category code and category not
	 * 
	 * @param categoryCode
	 * @param categoryName
	 * @return CategoryEntity
	 * 
	 */
	CategoryEntity findByCategoryCodeAndCategoryIdNot(String categoryCode, Long categoryId);
	
	/**
	 * 
	 * Find category by category name and category not
	 * 
	 * @param categoryCode
	 * @param categoryName
	 * @return CategoryEntity
	 * 
	 */
	CategoryEntity findByCategoryNameAndCategoryIdNot(String categoryName, Long categoryId);
	
}
