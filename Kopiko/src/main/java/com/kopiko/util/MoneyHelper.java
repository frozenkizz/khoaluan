package com.kopiko.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyHelper {
	/**
	 * Trả về chuỗi định dạng xxx.xxx.xxx đ
	 * @param money
	 * @return
	 */
	public static String toMoneyType(BigDecimal money) {
		if(money == null) money = new BigDecimal(0);
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return formatter.format(money) + " đ";
	}
	
	
	/**
	 * Trả về chuỗi định dạng xxx.xxx.xxx đ
	 * @param money
	 * @return
	 */
	public static String toMoneyType(Long money) {
		if(money == null) money = 0l;
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return formatter.format(money) + " đ";
	}
	
	/**
	 * Trả về chuỗi định dạng xxx.xxx.xxx đ
	 * @param money
	 * @return
	 */
	public static String toMoneyType(Double money) {
		if(money == null) money = 0d;
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return formatter.format(money) + " đ";
	}

}
