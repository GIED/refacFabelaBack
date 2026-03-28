package com.refacFabela.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail.smtp")
public class MailConfigProperties {

	private String host;
	private Integer port;
	private boolean auth;
	private boolean starttlsEnable;
	private Integer connectionTimeout;
	private Integer timeout;
	private String account;
	private String password;
	private String replyTo;
	private String bccPrimary;
	private String bccSecondary;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isStarttlsEnable() {
		return starttlsEnable;
	}

	public void setStarttlsEnable(boolean starttlsEnable) {
		this.starttlsEnable = starttlsEnable;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getBccPrimary() {
		return bccPrimary;
	}

	public void setBccPrimary(String bccPrimary) {
		this.bccPrimary = bccPrimary;
	}

	public String getBccSecondary() {
		return bccSecondary;
	}

	public void setBccSecondary(String bccSecondary) {
		this.bccSecondary = bccSecondary;
	}
}