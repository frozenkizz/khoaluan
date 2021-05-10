/**
 * 
 */
package com.kopiko.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 26 thg 3, 2021 16:01:22
 * Sử dụng để truy xuất thông tin khách hàng
 */

@Data
@NoArgsConstructor
public class AccountCustomerDTO {
	private Long accountId;
	private String username;
	private String fullName;
	private String phone;
	private String email;
	private String address;
	private String avatar;
	private MultipartFile avatarFile;
	private String role;
	
	public String getDeliveryInfo() {
		String result = "";
		result += "Khách hàng: " + this.fullName;
		result += ", Sđt: " + this.phone;
		result += ", Địa chỉ: " + this.address;
		return result;
	}

}
