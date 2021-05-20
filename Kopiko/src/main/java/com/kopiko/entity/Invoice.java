package com.kopiko.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;


@Data
@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint")
	private Long invoiceId;
	
	@ManyToOne()
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;
	
	@Column(nullable = false)
	private String txnRef;
	
	@Column(nullable = false, columnDefinition = "money")
	private BigDecimal amount;
	
	private String bankCode;
	
	@Basic
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date payDate;
	
	@Column(nullable = false)
	private String orderInfo;
	
	@Column(nullable = false)
	private String responseCode;
	
	@Column(nullable = false)
	private String transactionNo;
	
}