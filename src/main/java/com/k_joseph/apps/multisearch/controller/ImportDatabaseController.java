package com.k_joseph.apps.multisearch.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.k_joseph.apps.multisearch.MultiSearchStaticObjects;

/**
 * Handles uploading of of SQL source file
 */
@Controller
public class ImportDatabaseController {
	
	/**
	 * Upload single file using Spring Controller
	 */
	@RequestMapping(value = "/uploadSQLFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("dbName") String name, @RequestParam("sqlSourceFile") MultipartFile file) {
		
		// TODO check that no create database, use database exist in the file
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				// Creating the directory to store file
				String homePath = MultiSearchStaticObjects.homeDirectory;
				File dir = new File(homePath + File.separator + "uploads");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name + ".sql");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				System.out.println("Server File Location=" + serverFile.getAbsolutePath());
				
				return "You successfully uploaded file=" + name;
			}
			catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
			
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}
