package com.refacFabela.model;

//Ajusta nombres si el WS usa "ErrorDesc" u otro campo
public class WsError {
 @com.fasterxml.jackson.annotation.JsonProperty("ErrorCode")
 private String errorCode;
 @com.fasterxml.jackson.annotation.JsonProperty("ErrorMessage")
 private String errorMessage;
 
 
 // getters/setters
public String getErrorCode() {
	return errorCode;
}
public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
}
public String getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}

 
 
 
}

