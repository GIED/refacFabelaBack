package com.refacFabela;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RefacFabelaApplication {

	public static void main(String[] args) {
		// Fix JAX-WS provider for JDK 9+ (Spring Boot classloader doesn't discover META-INF/services from nested JARs)
		System.setProperty("javax.xml.ws.spi.Provider", "com.sun.xml.ws.spi.ProviderImpl");
		// Fix TLS handshake failures with JDK 1.8 against modern HTTPS servers (foliosdigitalespac.com)
		// JDK 8 defaults to TLS 1.0; force TLS 1.2 to prevent SOAP connection failures returning 0 credits
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		SpringApplication.run(RefacFabelaApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
