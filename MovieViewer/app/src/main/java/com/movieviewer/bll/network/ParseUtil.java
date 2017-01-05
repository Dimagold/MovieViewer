package com.movieviewer.bll.network;

import com.google.gson.Gson;

public class ParseUtil {
	
	public static Gson getFullCompatibilityGson() {
		Gson gson = new Gson();
		return gson;
	}
}
