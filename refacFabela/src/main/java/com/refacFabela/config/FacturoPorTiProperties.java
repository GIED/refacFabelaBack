package com.refacFabela.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "facturoporti")
public class FacturoPorTiProperties {

	private boolean sandbox;
	private String sandboxBaseUrl;
	private String productionBaseUrl;
	private boolean logRawPayloads;
	private AuthProperties auth = new AuthProperties();
	private TimeoutProperties timeout = new TimeoutProperties();

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public String getSandboxBaseUrl() {
		return sandboxBaseUrl;
	}

	public void setSandboxBaseUrl(String sandboxBaseUrl) {
		this.sandboxBaseUrl = sandboxBaseUrl;
	}

	public String getProductionBaseUrl() {
		return productionBaseUrl;
	}

	public void setProductionBaseUrl(String productionBaseUrl) {
		this.productionBaseUrl = productionBaseUrl;
	}

	public boolean isLogRawPayloads() {
		return logRawPayloads;
	}

	public void setLogRawPayloads(boolean logRawPayloads) {
		this.logRawPayloads = logRawPayloads;
	}

	public AuthProperties getAuth() {
		return auth;
	}

	public void setAuth(AuthProperties auth) {
		this.auth = auth;
	}

	public TimeoutProperties getTimeout() {
		return timeout;
	}

	public void setTimeout(TimeoutProperties timeout) {
		this.timeout = timeout;
	}

	public static class AuthProperties {

		private String type;
		private String sandboxToken;
		private String productionToken;
		private String apiKey;
		private String username;
		private String password;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSandboxToken() {
			return sandboxToken;
		}

		public void setSandboxToken(String sandboxToken) {
			this.sandboxToken = sandboxToken;
		}

		public String getProductionToken() {
			return productionToken;
		}

		public void setProductionToken(String productionToken) {
			this.productionToken = productionToken;
		}

		public String getApiKey() {
			return apiKey;
		}

		public void setApiKey(String apiKey) {
			this.apiKey = apiKey;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class TimeoutProperties {

		private Integer connectMs = 10000;
		private Integer readMs = 30000;

		public Integer getConnectMs() {
			return connectMs;
		}

		public void setConnectMs(Integer connectMs) {
			this.connectMs = connectMs;
		}

		public Integer getReadMs() {
			return readMs;
		}

		public void setReadMs(Integer readMs) {
			this.readMs = readMs;
		}
	}
}
