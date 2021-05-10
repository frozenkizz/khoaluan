/**
 * 
 * @author ADMIN
 * @date Mar 17, 2021
 */

package com.kopiko.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {

	private Object data;
	private int responseCode;
	
	
	/**
	 * @param resonseCode
	 */
	public ResponseModel(int responseCode) {
		this.responseCode = responseCode;
	}
	
	
}
