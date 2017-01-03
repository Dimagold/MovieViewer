package com.movieviewer.bll.network.responce;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;

public class GetPopularMoviesResponce extends BaseResponce<GetPopularMoviesResponce>{

	private int page;
	private List<MovieMetaData> results;
	private int total_results;
	private int total_pages;
	
	public GetPopularMoviesResponce() {}
	
	public GetPopularMoviesResponce(String json) {
		GetPopularMoviesResponce responce = fromJson(json);
		this.page = responce.page;
		this.results = responce.results;
		this.total_pages = responce.total_pages;
		this.total_results = responce.total_results;
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetPopularMoviesResponce fromJson(String json) {
		GetPopularMoviesResponce obj = new GsonBuilder().create().fromJson(json, GetPopularMoviesResponce.class);
		return obj;
	}
	
	public List<MovieMetaData> getResults() {
		return results;
	}

	public void setResults(List<MovieMetaData> results) {
		this.results = results;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal_results() {
		return total_results;
	}

	public void setTotal_results(int total_results) {
		this.total_results = total_results;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}

}
