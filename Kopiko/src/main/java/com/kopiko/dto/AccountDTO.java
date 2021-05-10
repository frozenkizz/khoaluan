/**
 * 
 */
package com.kopiko.dto;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 15 thg 3, 2021 13:26:34
 */

@Data
@NoArgsConstructor
public class AccountDTO {
	private Long accountId;
	private String username;
	private String fullName;
	private String phone;
	private String address;
	private String avatar;
	private String role;
	private String email;
	private Integer status;
	private String password;
	private Date dateCreated;
	
	
}
