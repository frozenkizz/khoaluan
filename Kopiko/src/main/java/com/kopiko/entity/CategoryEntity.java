/**
 * 
 * @author ADMIN
 * @date Mar 10, 2021
 */

package com.kopiko.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "category_id")
	private Long categoryId;

	@Column(nullable = false, unique = true)
	private String categoryCode;

	@Column(nullable = false, columnDefinition = "nvarchar(50)", unique = true)
	private String categoryName;
	
	@Column(columnDefinition = "bigint", nullable = true)
	private Long parentsCategoryId;

}
