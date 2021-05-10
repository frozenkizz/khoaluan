/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_detail")
@Data
@NoArgsConstructor
public class OrderDetailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "order_detail_id")
	private Long orderDetailId;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@ManyToOne
	@JoinColumn(name = "product_detail_id", nullable = false)
	private ProductDetail productDetail;
	
	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false, columnDefinition = "money")
	private BigDecimal salePrice;
	
	
	public Long getLongSalePrice() {
		return this.salePrice.longValue();
	}
}
