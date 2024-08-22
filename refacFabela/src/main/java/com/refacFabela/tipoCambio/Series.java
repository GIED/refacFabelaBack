package com.refacFabela.tipoCambio;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

public class Series {

    private static final String URL = "https://www.banxico.org.mx/SieAPIRest/service/v1/series/SF43718/datos/";
    private static final String TOKEN = "bac4f3725ad4688e6aa5586baa4af96a1a9b399da7589f25ad533e4a6fa26093";

    // Método que realiza la consulta del tipo de cambio
    public static DataSerie getExchangeRate() {
        try {
            Response response = readSeries();
            Serie serie = response.getBmx().getSeries().get(0);
            System.err.println(response.getBmx().getSeries().get(0));
            DataSerie data = serie.getDatos().get(0);
            if (data.getDato().equals("N/E")) {
                return null;
            } else {
                return data;
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Método que realiza la solicitud HTTP y convierte la respuesta en un objeto Response
    private static Response readSeries() throws Exception {
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                SSLContexts.createDefault(),
                new String[]{"TLSv1.3"}, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
        	
        	/*Date fechaDia=new Date();
        	String fechaConsulta="";
        	try {
				fechaConsulta=fecha(fechaDia,-1);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} */
            HttpGet getMethod = new HttpGet(URL+"/oportuno");
            getMethod.setHeader("Content-Type", "application/json");
            getMethod.setHeader("Bmx-Token", TOKEN);
            HttpResponse httResponse = httpclient.execute(getMethod);
            StatusLine statusLine = httResponse.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("Http code error: " + statusLine);
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(httResponse.getEntity().getContent(), Response.class);
        }
    }

    // Método que invoca getExchangeRate y maneja el resultado
    public static void displayExchangeRate() {
    	DataSerie exchangeRate = getExchangeRate();
        System.out.println("Tipo de cambio: " + exchangeRate);
    }

    // Método adicional que llama a displayExchangeRate
    public static void anotherMethod() {
    	DataSerie rate = getExchangeRate();
        System.out.println("Tipo de cambio desde otro método: " + rate);
    }
    
    public static String fecha(Date fecha, int dias) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, dias); // numero de días a añadir, o restar en caso de días<0
		
		 SimpleDateFormat objSDF = new SimpleDateFormat("yyyy-MM-dd"); 
		return String.valueOf( objSDF.format(calendar.getTime())) ; // Devuelve el objeto Date con los nuevos días añadidos
	}

   
}