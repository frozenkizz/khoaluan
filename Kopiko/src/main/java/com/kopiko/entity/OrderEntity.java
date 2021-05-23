/**
 * 
 * @author ADMIN
 * @date Mar 11, 2021
 */

package com.kopiko.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.kopiko.util.DateTimeUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "[order]")
@Data
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "order_id")
	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "order_status_id", nullable = false)
	@ColumnDefault("1")
	private OrderStatusEntity orderStatus;

	@Column(nullable = false, columnDefinition = "nvarchar(250)")
	private String deliveryInfo;

	private BigDecimal shippingFee;

	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false)
	private PaymentMethodEntity paymentMethod;

	@OneToMany(mappedBy = "order")
	private List<OrderDetailEntity> listOrderDetail;
	
	public OrderEntity() {
		listOrderDetail = new ArrayList<OrderDetailEntity>();
	}

	public Long getTotalPrice() {
		Long result = 0l;
		
		// List dang rong
		if(listOrderDetail.size() == 0) System.out.println("DS Sp rong!");
		for (OrderDetailEntity orderDetail : listOrderDetail) {
			if(orderDetail != null && orderDetail.getSalePrice() != null && orderDetail.getQuantity() != null) {
				result += orderDetail.getSalePrice().longValue()*orderDetail.getQuantity();
				System.out.println("Sl sp:" + orderDetail.getQuantity());
			}
			else System.out.println("Co loi du lieu!");
		}
		
		if(shippingFee != null) result += shippingFee.longValue();
		System.out.println("Tong tien hoa don: " + result);
		return result;
	}
	
	public String getStringTotalPrice() {
		String data = String.valueOf(getTotalPrice());
		String result = "";
		
		return result;
	}
	
	public String getName() {
		String result = "Rỗng!";
		if(listOrderDetail != null && !listOrderDetail.isEmpty()) {
			result = listOrderDetail.get(0).getProductDetail().getProduct().getProductName();
			int size = listOrderDetail.size();
			if(size > 1) result += "...và " + size + " sản phẩm khác";
		}
		return result;
	}
	
	public String getDeliveryTypeHtml() {
		return this.deliveryInfo.replace(";", "<br>");
	}
	
	public String getDateCreatedString() {
		return DateTimeUtil.toStringType(dateCreated);
	}
}
