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
		SpringApplication.run(RefacFabelaApplication.class, args);
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
