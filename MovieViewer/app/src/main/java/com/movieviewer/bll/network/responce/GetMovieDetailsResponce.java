package com.movieviewer.bll.network.responce;

import java.util.List;

import android.graphics.Bitmap;

import com.google.gson.GsonBuilder;
import com.movieviewer.bll.network.responce.dto.Gener;
import com.movieviewer.bll.network.responce.dto.ProductionCompany;
import com.movieviewer.bll.network.responce.dto.ProductionCountries;
import com.movieviewer.bll.network.responce.dto.SpokenLanguages;

public class GetMovieDetailsResponce extends BaseResponce<GetMovieDetailsResponce> {

	private boolean adult;
	private String backdrop_path;
	private Object belongs_to_collection;
	private int budget;
	private List<Gener> genres;
	private String homepage;
	private int id;
	private String imdb_id;
	private String original_language;
	private String original_title;
	private String overview;
	private double popularity;
	private String poster_path;
	private List<ProductionCompany> production_companies;
	private List<ProductionCountries> production_countries;
	private String release_date;
	private int revenue;
	private int runtime;
	private List<SpokenLanguages> spoken_languages;
	private String status;
	private String tagline;
	private String title;
	private boolean video;
	private double vote_average;
	private double vote_count;
	
	private Bitmap offlinePoster;
	
	public GetMovieDetailsResponce() {}
	
	public GetMovieDetailsResponce(String json) {
		GetMovieDetailsResponce responce = fromJson(json);
		this.adult = responce.adult;
		this.backdrop_path = responce.backdrop_path;
		this.belongs_to_collection = responce.belongs_to_collection;
		this.budget = responce.budget;
		this.genres = responce.genres;
		this.homepage = responce.homepage;
		this.id = responce.id;
		this.original_language = responce.original_language;
		this.original_title = responce.original_title;
		this.overview = responce.overview;
		this.popularity = responce.popularity;
		this.poster_path = responce.poster_path;
		this.production_companies = responce.production_companies;
		this.release_date = responce.release_date;
		this.revenue = responce.revenue;
		this.runtime = responce.runtime;
		this.spoken_languages = responce.spoken_languages;
		this.status = responce.status;
		this.tagline = responce.tagline;
		this.title = responce.title;
		this.video = responce.video;
		this.vote_average = responce.vote_average;
		this.vote_count = responce.vote_count;
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetMovieDetailsResponce fromJson(String json) {
		GetMovieDetailsResponce obj = new GsonBuilder().create().fromJson(json, GetMovieDetailsResponce.class);
		return obj;
	}
	
	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public Object getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(Object belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public List<Gener> getGenres() {
		return genres;
	}

	public void setGenres(List<Gener> genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_language() {
		return original_language;
	}

	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getPopularity() {
		return popularity;
	}

	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public List<ProductionCompany> getProduction_companies() {
		return production_companies;
	}

	public void setProduction_companies(List<ProductionCompany> production_companies) {
		this.production_companies = production_companies;
	}

	public List<ProductionCountries> getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(
			List<ProductionCountries> production_countries) {
		this.production_countries = production_countries;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public List<SpokenLanguages> getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(List<SpokenLanguages> spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public double getVote_average() {
		return vote_average;
	}

	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
	}

	public double getVote_count() {
		return vote_count;
	}

	public void setVote_count(double vote_count) {
		this.vote_count = vote_count;
	}

	public Bitmap getOfflinePoster() {
		return offlinePoster;
	}

	public void setOfflinePoster(Bitmap offlinePoster) {
		this.offlinePoster = offlinePoster;
	}

}
