package com.ppm.boot.error;

public class ValidationErrors {
	
	private String key;
	
	private String message;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ValidationErrors [key=" + key + ", message=" + message + "]";
	}
	
	
	
	

}
