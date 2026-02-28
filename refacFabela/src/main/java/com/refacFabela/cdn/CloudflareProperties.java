package com.refacFabela.cdn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de propiedades para los servicios de CDN.
 * Costex (proveedor), Cloudflare R2 (almacenamiento propio) y repositorio local.
 */
@Configuration
public class CloudflareProperties {

    // ===== Cloudflare R2 =====
    @Value("${cloudflare.r2.account.id:}")
    private String r2AccountId;

    @Value("${cloudflare.r2.access.key.id:}")
    private String r2AccessKeyId;

    @Value("${cloudflare.r2.secret.access.key:}")
    private String r2SecretAccessKey;

    @Value("${cloudflare.r2.bucket.name:}")
    private String r2BucketName;

    @Value("${cloudflare.r2.endpoint:}")
    private String r2Endpoint;

    @Value("${cloudflare.r2.public.url:https://cdn.jemkal.com}")
    private String r2PublicUrl;

    // ===== Costex CDN =====
    @Value("${cdn.costex.base-url:https://www.ctpsales.costex.com:11443/Webpics/220x220/}")
    private String costexBaseUrl;

    // ===== Local =====
    @Value("${cdn.local.ruta-imagenes:/opt/imgprod/}")
    private String rutaImagenesLocal;

    public String getR2AccountId() {
        return r2AccountId;
    }

    public String getR2AccessKeyId() {
        return r2AccessKeyId;
    }

    public String getR2SecretAccessKey() {
        return r2SecretAccessKey;
    }

    public String getR2BucketName() {
        return r2BucketName;
    }

    public String getR2Endpoint() {
        return r2Endpoint;
    }

    public String getR2PublicUrl() {
        return r2PublicUrl;
    }

    public String getCostexBaseUrl() {
        return costexBaseUrl;
    }

    public String getRutaImagenesLocal() {
        return rutaImagenesLocal;
    }

    /**
     * URL publica de un objeto en R2 via dominio personalizado.
     * Formato: https://cdn.jemkal.com/{key}
     */
    public String getPublicUrl(String objectKey) {
        String base = r2PublicUrl != null && !r2PublicUrl.trim().isEmpty()
                ? r2PublicUrl : "https://cdn.jemkal.com";
        // Quitar slash final si lo tiene
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        return base + "/" + objectKey;
    }

    /**
     * Verifica si la configuracion de Cloudflare R2 esta completa.
     */
    public boolean isR2Configurado() {
        return r2AccessKeyId != null && !r2AccessKeyId.trim().isEmpty()
                && r2SecretAccessKey != null && !r2SecretAccessKey.trim().isEmpty()
                && r2BucketName != null && !r2BucketName.trim().isEmpty()
                && r2Endpoint != null && !r2Endpoint.trim().isEmpty();
    }
}
