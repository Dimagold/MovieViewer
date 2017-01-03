package com.movieviewer.bll.network.request;

import org.apache.http.MethodNotSupportedException;

/**
 * 
 * @author Dima Goldenberg
 * 
 * Example: MOVIE_DB_BASE_URL/MOVIE_ID?api_key=a5e2e52a688d5b7a2f2a412922b84ba9&language=en-US
 * 
 */
public class GetMovieDetailsRequest extends BaseRequest<GetMovieDetailsRequest> {

	protected String API_COMMAND = "popular";
	private int movieId;
	
	public GetMovieDetailsRequest(RequestLanguage language, int movieId) {
		super(language);
		this.movieId = movieId;
	}

	@Override
	public String getURL() {
		StringBuilder urlBuilder = new StringBuilder()
		.append(MOVIE_DB_BASE_URL)
		.append(movieId).append("?")
		.append("api_key=").append(MOVIE_DB_API_KEY).append("&")
		.append("language=").append(language.getValue());
	
		return urlBuilder.toString();
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	@Override
	public String toJson() {
		try {
			throw new MethodNotSupportedException("Method Not in usage");
		} catch (MethodNotSupportedException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public GetMovieDetailsRequest fromJson(String json) {
		try {
			throw new MethodNotSupportedException("Method Not in usage");
		} catch (MethodNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
