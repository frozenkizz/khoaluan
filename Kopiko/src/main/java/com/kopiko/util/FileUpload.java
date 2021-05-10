/**
 * 
 */
package com.kopiko.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author rianta9
 * @datecreated 29 thg 3, 2021 23:11:51
 */
public class FileUpload {
	
	/**
	 * Upload một ảnh từ client lên server và lưu vào /uploads/images/
	 * @param file
	 * @param servletRequest
	 * @return Trả về tên ảnh mới được lưu trong /uploads/images/
	 */
	public static String uploadImage(MultipartFile file, HttpServletRequest servletRequest) {
		try {
			String newFileName = file.getOriginalFilename();
			String extension = ".jpg";
			if(newFileName.endsWith(".png")) extension = ".png";
			else if(newFileName.endsWith(".jpeg")) extension = ".jpeg";
			else if(!newFileName.endsWith(".jpg")) return null;
			newFileName = RandomUUID.getRandomID()+extension; // Tạo tên mới theo UUID
			byte[] bytes = file.getBytes();
			Path path = Paths.get(servletRequest.getServletContext().getRealPath("/uploads/images/") +  newFileName);
			System.out.println(path);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			return null;
		}
	}
}
