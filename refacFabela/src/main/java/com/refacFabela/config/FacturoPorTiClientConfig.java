package com.refacFabela.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FacturoPorTiClientConfig {

	@Bean(name = "facturoPorTiRestTemplate")
	public RestTemplate facturoPorTiRestTemplate(RestTemplateBuilder builder, FacturoPorTiProperties properties) {
		int connectMs = properties.getTimeout() != null && properties.getTimeout().getConnectMs() != null
				? properties.getTimeout().getConnectMs() : 10000;
		int readMs = properties.getTimeout() != null && properties.getTimeout().getReadMs() != null
				? properties.getTimeout().getReadMs() : 30000;

		return builder
				.setConnectTimeout(Duration.ofMillis(connectMs))
				.setReadTimeout(Duration.ofMillis(readMs))
				.build();
	}
}
