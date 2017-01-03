package com.movieviewer.bll.network;

public interface JsonParselable<T> {
	
	public String toJson();
	
	public T fromJson(String json);
}
