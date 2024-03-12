package com.refacFabela.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


public class subirArchivo {
	
	
public boolean subirArchivoFactura(MultipartFile file,  Long nombreArchivo, String ruta) throws Exception {
		
		
		try {
			String fileName =nombreArchivo.toString();
			byte[] bytes=file.getBytes();
			//accede al nombre original del archivo
			String fileOriginalName=file.getOriginalFilename();
			
			if(!fileOriginalName.endsWith(".pdf") && !fileOriginalName.endsWith(".xml")) {
				return false;
			}
			String fileExtension=fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
			
			String newFileName=fileName+fileExtension;			
			
			Path path= Paths.get(ruta+newFileName);
			System.err.println();
			
			Files.write(path,  bytes);
			
			return true;
			
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
			
		}
		
		
		
	}
	
	
	
	

}
