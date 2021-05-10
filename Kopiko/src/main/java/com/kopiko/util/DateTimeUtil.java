/**
 * 
 */
package com.kopiko.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author rianta9
 * @datecreated 30 thg 3, 2021 02:13:47
 */
public class DateTimeUtil {
	/**
	 * Trả về một chuỗi ngày định dạng dd/MM/yyyy
	 * @param date
	 * @return
	 */
	public static String toStringType(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = formatter.format(date);
        return result;
	}
	
	/**
	 * Trả về Date(sql) từ một chuỗi có định dạng dd/MM/yyyy
	 * @param date
	 * @return
	 */
	public static Date toDateSQL(String date) {
		java.util.Date dateUtil;
		try {
			dateUtil = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			return null;
		}
		Long result = dateUtil.getTime();
		return new Date(result);
	}
	
	/**
	 * Chuyển từ một date util sang date sql
	 * @param date
	 * @return
	 */
	public static Date dateUtilToDateSQL(java.util.Date date) {
		Long milis = date.getTime();
		return new Date(milis);
	}
	
	public static java.util.Date dateSQLToDateUtil(Date date){
		Long milis = date.getTime();
		return new java.util.Date(milis);
	}
}
