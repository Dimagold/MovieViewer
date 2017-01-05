package com.movieviewer.bll.network.request;

import java.io.Serializable;

import com.movieviewer.bll.network.JsonParselable;

public abstract class BaseRequest<E> implements JsonParselable<E>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3621244970469629026L;
	protected static final String MOVIE_DB_API_KEY = "a5e2e52a688d5b7a2f2a412922b84ba9";
	protected static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";
	public static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w500/";
	
	protected RequestLanguage language;
	
	public BaseRequest(RequestLanguage language) {
		this.language = language;
	}
	
	public abstract String getURL();
	
	protected String getBaseUrl() {
		return BaseRequest.MOVIE_DB_BASE_URL;
	}
}
