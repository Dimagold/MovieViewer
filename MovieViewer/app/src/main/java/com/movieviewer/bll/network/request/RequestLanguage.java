package com.movieviewer.bll.network.request;

public enum RequestLanguage {
	
	ENG("en-US");
	
	private String value;
	
	private RequestLanguage(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
