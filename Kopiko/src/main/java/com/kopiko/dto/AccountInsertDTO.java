/**
 * 
 */
package com.kopiko.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rianta9
 * @datecreated 22 thg 3, 2021 08:42:41
 */

@Data
@NoArgsConstructor
public class AccountInsertDTO {
	private Long accountId;
	private String username;
	private String password;
	private String fullName;
	private String phone;
	private String email;
	private String role;
}
