package com.movieviewer.bll.network.request;

import org.apache.http.MethodNotSupportedException;

/**
 * 
 * @author Dima Goldenberg
 * 
 * Example: MOVIE_DB_BASE_URL/API_COMMAND?api_key=a5e2e52a688d5b7a2f2a412922b84ba9&language=en-US&page=1
 * Request page list of popular movies
 */
public class GetPopularMoviesRequest extends BaseRequest<GetPopularMoviesRequest> {

	protected String API_COMMAND = "popular";
	private int page;
	
	public GetPopularMoviesRequest(RequestLanguage language, int page) {
		super(language);
		this.page = page;
	}

	@Override
	public String getURL() {
		
		StringBuilder urlBuilder = new StringBuilder()
			.append(MOVIE_DB_BASE_URL)
			.append(API_COMMAND).append("?")
			.append("api_key=").append(MOVIE_DB_API_KEY).append("&")
			.append("language=").append(language.getValue()).append("&")
			.append("page=").append(page);
		
		return urlBuilder.toString();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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
	public GetPopularMoviesRequest fromJson(String json) {
		try {
			throw new MethodNotSupportedException("Method Not in usage");
		} catch (MethodNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
