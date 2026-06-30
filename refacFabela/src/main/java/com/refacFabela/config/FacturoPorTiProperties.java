package com.refacFabela.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "facturoporti")
public class FacturoPorTiProperties {

	private boolean sandbox;
	private String baseUrl;
	private boolean logRawPayloads;
	private AuthProperties auth = new AuthProperties();
	private TimeoutProperties timeout = new TimeoutProperties();
	private RetryProperties retry = new RetryProperties();

	public boolean isSandbox() {
		return sandbox;
	}

	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
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

	public RetryProperties getRetry() {
		return retry;
	}

	public void setRetry(RetryProperties retry) {
		this.retry = retry;
	}

	public static class AuthProperties {

		private String type;
		private String token;
		private String apiKey;
		private String username;
		private String password;
		private String clientId;
		private String clientSecret;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
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

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getClientSecret() {
			return clientSecret;
		}

		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
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

	public static class RetryProperties {

		private Integer maxAttempts = 3;
		private Integer backoffMs = 1000;

		public Integer getMaxAttempts() {
			return maxAttempts;
		}

		public void setMaxAttempts(Integer maxAttempts) {
			this.maxAttempts = maxAttempts;
		}

		public Integer getBackoffMs() {
			return backoffMs;
		}

		public void setBackoffMs(Integer backoffMs) {
			this.backoffMs = backoffMs;
		}
	}
}
