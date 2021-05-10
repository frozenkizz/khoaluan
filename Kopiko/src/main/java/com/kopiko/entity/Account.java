package com.kopiko.entity;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "bigint", name = "account_id")
	private Long accountId;
	
	@Column(unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String phone;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, columnDefinition = "nvarchar(30)")
	private String fullName;
	
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private String avatar;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false, columnDefinition = "datetime")
	@CreationTimestamp
	private Date dateCreated;
	
	@ColumnDefault("USER") // User
	private String role;
	
	@ColumnDefault("1") // Active
	private Integer status;

	/**
	 * @return
	 */
	public String getDeliveryInfo() {
		String result = "";
		result += "Khách hàng: " + this.fullName;
		result += ";Sđt: " + this.phone;
		result += ";Địa chỉ: " + this.address;
		return result;
	}

}
