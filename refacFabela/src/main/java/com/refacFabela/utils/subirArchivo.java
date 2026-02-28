package com.refacFabela.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import com.refacFabela.model.TcProducto;


public class subirArchivo {
	
	
public boolean subirArchivoFactura(MultipartFile file,  Integer nombreArchivo, String ruta) throws Exception {
		
		
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

// ✅ Guardar imagen desde una URL externa
public boolean guardarImagenDesdeUrl(String urlImagen, String rutaDestinoCompleta) {
    try {
        URL url = new URL(urlImagen);
        System.err.println(urlImagen);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setConnectTimeout(5000);
        conexion.setReadTimeout(5000);

        int status = conexion.getResponseCode();
        if (status != HttpURLConnection.HTTP_OK) {
            System.err.println("No se pudo acceder a la imagen. Código HTTP: " + status);
            return false;
        }

        Path path = Paths.get(rutaDestinoCompleta);
        Files.createDirectories(path.getParent());
        
        System.err.println(rutaDestinoCompleta);

        try (InputStream inputStream = conexion.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(rutaDestinoCompleta)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return true;

    } catch (Exception e) {
        System.err.println("Error al descargar la imagen: " + e.getMessage());
        return false;
    }
}

// ✅ Guardar imagen desde base64
public boolean guardarImagenDesdeBase64(String base64, String rutaDestinoCompleta) {
    try {
        // Eliminar encabezado si lo trae: "data:image/png;base64,..."
        if (base64.contains(",")) {
            base64 = base64.split(",")[1];
        }

        // Decodifica el base64 a bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64);

        // Leer la imagen desde los bytes
        InputStream is = new ByteArrayInputStream(imageBytes);
        BufferedImage originalImage = ImageIO.read(is);
        if (originalImage == null) {
            System.err.println("❌ No se pudo leer la imagen desde el base64.");
            return false;
        }

        // Redimensionar a 220x220
        BufferedImage resizedImage = new BufferedImage(220, 220, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 220, 220, null);
        g.dispose();

        // Asegurar que el directorio exista
        Path path = Paths.get(rutaDestinoCompleta);
        Files.createDirectories(path.getParent());

        // Forzar extensión .jpg
        if (!rutaDestinoCompleta.toLowerCase().endsWith(".jpg")) {
            rutaDestinoCompleta = rutaDestinoCompleta.replaceAll("\\.[^.]+$", "") + ".jpg";
        }

        // Guardar imagen como JPG
        File outputFile = new File(rutaDestinoCompleta);
        ImageIO.write(resizedImage, "jpg", outputFile);

        return true;

    } catch (Exception e) {
        System.err.println("❌ Error al guardar la imagen redimensionada: " + e.getMessage());
        return false;
    }
}

// Método principal que valida y guarda la imagen según su origen
public boolean procesarImagenProducto(TcProducto producto, String rutaBase) throws Exception {
    return procesarImagenProducto(producto, rutaBase, "https://www.ctpsales.costex.com:11443/Webpics/220x220/");
}

// Método principal con URL de CDN configurable
public boolean procesarImagenProducto(TcProducto producto, String rutaBase, String costexBaseUrl) throws Exception {
    String rutaImagen = producto.getsRutaImagen();

    // Si no hay imagen definida, usar la URL por defecto de CTP
    if (rutaImagen == null || rutaImagen.trim().isEmpty()) {
        rutaImagen = costexBaseUrl + producto.getsNoParte() + ".jpg";
    }

    // Construir nombre de archivo
    String nombreArchivo = producto.getsNoParte() + ".jpg";
    String rutaFinalCompleta = Paths.get(rutaBase, nombreArchivo).toString();

    System.err.println("Ruta de imagen original: " + rutaImagen);
    System.err.println("Ruta final completa: " + rutaFinalCompleta);

    if (rutaImagen.startsWith(costexBaseUrl)) {
        return guardarImagenDesdeUrl(rutaImagen, rutaFinalCompleta);
    } else {
        return guardarImagenDesdeBase64(rutaImagen, rutaFinalCompleta);
    }
}


/*obtine la image del producto desde una ruta*/
public String obtenerImagenBase64(String rutaCompleta) {
    try {
        Path path = Paths.get(rutaCompleta);
        if (!Files.exists(path)) {
            System.err.println("❌ Imagen no encontrada en disco: " + rutaCompleta);
            return null;
        }
        byte[] bytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(bytes);

    } catch (Exception e) {
        System.err.println("❌ Error al convertir imagen a base64: " + e.getMessage());
        return null;
    }
}

/**
 * Procesa múltiples productos CTP descargando sus imágenes desde la URL por defecto de CTP.
 * Sobrecarga retrocompatible que usa la URL por defecto de Costex.
 */
public Map<String, Integer> procesarImagenesProductosCTP(List<com.refacFabela.model.TcProducto> productos, String rutaBase) {
    return procesarImagenesProductosCTP(productos, rutaBase, "https://www.ctpsales.costex.com:11443/Webpics/220x220/");
}

/**
 * Procesa múltiples productos CTP descargando sus imágenes desde la URL de CDN configurable.
 * 
 * @param productos Lista de productos TcProducto a procesar
 * @param rutaBase Ruta base donde se guardarán las imágenes
 * @param costexBaseUrl URL base del CDN de Costex
 * @return Map con estadísticas del proceso: exitosos, fallidos y total procesados
 */
public Map<String, Integer> procesarImagenesProductosCTP(List<com.refacFabela.model.TcProducto> productos, String rutaBase, String costexBaseUrl) {
    Map<String, Integer> resultado = new HashMap<>();
    int exitosos = 0;
    int fallidos = 0;
    int total = productos.size();
    
    System.err.println("🚀 Iniciando procesamiento de " + total + " productos CTP");
    
    for (com.refacFabela.model.TcProducto producto : productos) {
        try {
            // Construir la URL por defecto de CTP
            String urlCTP = costexBaseUrl + producto.getsNoParte() + ".jpg";
            String nombreArchivo = producto.getsNoParte() + ".jpg";
            String rutaFinalCompleta = Paths.get(rutaBase, nombreArchivo).toString();
            
            System.err.println("📥 Procesando: " + producto.getsNoParte() + " - " + producto.getsProducto());
            
            // Intentar descargar la imagen
            boolean descargado = guardarImagenDesdeUrl(urlCTP, rutaFinalCompleta);
            
            if (descargado) {
                exitosos++;
                System.err.println("✅ Imagen descargada exitosamente para: " + producto.getsNoParte());
            } else {
                fallidos++;
                System.err.println("❌ No se pudo descargar la imagen para: " + producto.getsNoParte());
            }
            
        } catch (Exception e) {
            fallidos++;
            System.err.println("❌ Error al procesar producto " + producto.getsNoParte() + ": " + e.getMessage());
        }
    }
    
    resultado.put("exitosos", exitosos);
    resultado.put("fallidos", fallidos);
    resultado.put("total", total);
    
    System.err.println("✨ Proceso completado - Exitosos: " + exitosos + " | Fallidos: " + fallidos + " | Total: " + total);
    
    return resultado;
}

	
	
	

}
