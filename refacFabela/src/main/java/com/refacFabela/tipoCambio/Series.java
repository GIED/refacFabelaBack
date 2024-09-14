package com.refacFabela.tipoCambio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class Series {

    // Método para obtener un único objeto de la posición 0 de la lista de DataSerie
    public DataSerie getFirstDataSerie() throws Exception {
        // La URL a consultar con los parámetros de idSerie y fechas
        String url = "https://www.banxico.org.mx/SieAPIRest/service/v1/series/SF43718/datos/oportuno";
        
        // Configuración de la conexión SSL/TLS
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[]{"TLSv1.2"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        DataSerie firstDataSerie = null;

        // Crea el cliente HTTP con soporte para TLS 1.3
        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
            HttpGet getMethod = new HttpGet(url);
            getMethod.setHeader("Content-Type", "application/json");
            getMethod.setHeader("Bmx-Token", "bac4f3725ad4688e6aa5586baa4af96a1a9b399da7589f25ad533e4a6fa26093");

            HttpResponse httpResponse = httpclient.execute(getMethod);
            StatusLine statusLine = httpResponse.getStatusLine();

            // Verificar si la respuesta HTTP es 200 (OK)
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("Error en la respuesta del servidor: " + statusLine.getReasonPhrase());
            }

            // Convertir la respuesta JSON a objeto Response
            ObjectMapper mapper = new ObjectMapper();
            Response response = mapper.readValue(httpResponse.getEntity().getContent(), Response.class);
            
            // Obtener el primer DataSerie si existe en la lista
            if (!response.getBmx().getSeries().isEmpty()) {
                Serie serie = response.getBmx().getSeries().get(0);  // Obtener la primera serie
                if (!serie.getDatos().isEmpty()) {
                    firstDataSerie = serie.getDatos().get(0);  // Obtener el primer elemento de la lista de datos
                }
            }
        }

        return firstDataSerie;  // Retornar el primer DataSerie (o null si no hay datos)
    }

   
}