package com.kopiko.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileHepler {

	public static String saveImage(MultipartFile file, HttpServletRequest servletRequest) {
		try {
			String newFileName = file.getOriginalFilename();
			String extension = ".jpg";
			if (newFileName.endsWith(".png"))
				extension = ".png";
			else if (newFileName.endsWith(".jpeg"))
				extension = ".jpeg";
			else if (!newFileName.endsWith(".jpg"))
				return null;
			newFileName = RandomUUID.getRandomID() + extension;
			byte[] bytes = file.getBytes();
			Path path = Paths.get(servletRequest.getServletContext().getRealPath("/uploads/images/") + newFileName);
			System.out.println(path);
			Files.write(path, bytes);
			return newFileName;
		} catch (Exception e) {
			return "Save image falied" + e;
		}
	}
	
	public static String getPathFileImage(String fileName, HttpServletRequest servletRequest) {
		Path path = Paths.get(servletRequest.getServletContext().getRealPath("/uploads/images/") + fileName);
		return path.toString();
	}
	
	public static void deleteFile(String fileName, HttpServletRequest servletRequest) {
		
		File deletedFile = new File(getPathFileImage(fileName, servletRequest));
		if(deletedFile.exists()) {
			deletedFile.delete();
		} else {
			System.out.println("Can't delete file. Image file not found!");
		}
	}
}
