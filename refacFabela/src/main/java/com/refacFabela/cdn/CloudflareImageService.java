package com.refacFabela.cdn;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Servicio de integración con Cloudflare R2 (S3-compatible).
 * Permite consultar, subir y obtener URLs públicas de imágenes almacenadas en R2.
 */
@Service
public class CloudflareImageService {

    @Autowired
    private CloudflareProperties properties;

    private AmazonS3 s3Client;

    @PostConstruct
    public void init() {
        if (properties.isR2Configurado()) {
            try {
                BasicAWSCredentials credentials = new BasicAWSCredentials(
                        properties.getR2AccessKeyId(),
                        properties.getR2SecretAccessKey()
                );

                s3Client = AmazonS3ClientBuilder.standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                                properties.getR2Endpoint(), "auto"))
                        .withCredentials(new AWSStaticCredentialsProvider(credentials))
                        .withPathStyleAccessEnabled(true)
                        .build();

                System.out.println("✅ Cliente S3 (Cloudflare R2) inicializado correctamente.");
            } catch (Exception e) {
                System.err.println("❌ Error al inicializar cliente S3 para R2: " + e.getMessage());
                s3Client = null;
            }
        } else {
            System.err.println("⚠️ Cloudflare R2 no configurado. Funcionalidad de CDN propio deshabilitada.");
        }
    }

    /**
     * Verifica si una imagen existe en el bucket R2.
     *
     * @param objectKey Clave del objeto (ej: "noParte.jpg")
     * @return true si el objeto existe en R2
     */
    public boolean existeImagen(String objectKey) {
        if (s3Client == null) {
            return false;
        }
        try {
            return s3Client.doesObjectExist(properties.getR2BucketName(), objectKey + ".jpg");
        } catch (Exception e) {
            System.err.println("Error al verificar imagen en R2 [" + objectKey + "]: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si una URL es accesible (HTTP HEAD con código 200).
     * Se usa para verificar imágenes en CDN de Costex.
     *
     * @param urlStr URL completa a verificar
     * @return true si la URL responde HTTP 200
     */
    public boolean verificarUrlAccesible(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setInstanceFollowRedirects(true);
            int code = conn.getResponseCode();
            conn.disconnect();
            return code == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sube una imagen a Cloudflare R2 desde un archivo local.
     *
     * @param objectKey Clave del objeto (noParte, sin extensión)
     * @param archivo   Archivo de imagen local
     * @return URL pública del objeto subido, o null si falló
     */
    public String subirImagen(String objectKey, File archivo) {
        if (s3Client == null) {
            System.err.println("R2 no configurado. No se puede subir imagen: " + objectKey);
            return null;
        }
        try {
            String key = objectKey + ".jpg";

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            metadata.setContentLength(archivo.length());

            PutObjectRequest putRequest = new PutObjectRequest(
                    properties.getR2BucketName(), key, archivo)
                    .withMetadata(metadata);

            s3Client.putObject(putRequest);
            System.out.println("✅ Imagen subida a R2: " + key);
            return obtenerUrlPublica(objectKey);
        } catch (Exception e) {
            System.err.println("❌ Error al subir imagen a R2 [" + objectKey + "]: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene la URL pública de un objeto en R2.
     *
     * @param objectKey Clave del objeto (noParte, sin extensión)
     * @return URL pública
     */
    public String obtenerUrlPublica(String objectKey) {
        return properties.getPublicUrl(objectKey + ".jpg");
    }

    /**
     * Elimina una imagen de Cloudflare R2.
     *
     * @param objectKey Clave del objeto (noParte, sin extensión)
     * @return true si se eliminó correctamente
     */
    public boolean eliminarImagen(String objectKey) {
        if (s3Client == null) {
            return false;
        }
        try {
            s3Client.deleteObject(properties.getR2BucketName(), objectKey + ".jpg");
            System.out.println("✅ Imagen eliminada de R2: " + objectKey);
            return true;
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar imagen de R2 [" + objectKey + "]: " + e.getMessage());
            return false;
        }
    }
}
