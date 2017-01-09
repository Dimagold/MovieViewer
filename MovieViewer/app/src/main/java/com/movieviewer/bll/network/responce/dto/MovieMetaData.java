package com.movieviewer.bll.network.responce.dto;

import java.io.Serializable;

import android.graphics.Bitmap;

public class MovieMetaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4849333188476160777L;
	private String poster_path = "";
	private boolean adult = false;
	private String overview = "";
	private String release_date = "";
	private int[] genre_ids = null;
	private int id = 0;
	private String original_title = "";
	private String original_language = "";
	private String title = "";
	private String backdrop_path = "";
	private double popularity = 0.0;
	private int vote_count = 0;
	private boolean video = false;
	private double vote_average = 0.0;
	
	private Bitmap offlineBitmap = null;
	private int page = -1;
	
	public MovieMetaData() {}
	
	/**
	 *  Better to use here "Builder Pattern" as there are a lot of parameters
	 *  
	 * @param poster_path
	 * @param adult
	 * @param overview
	 * @param release_date
	 * @param genre_ids
	 * @param id
	 * @param original_title
	 * @param original_language
	 * @param title
	 * @param backdrop_path
	 * @param popularity
	 * @param vote_count
	 * @param video
	 * @param vote_average
	 */
	public MovieMetaData(
			String poster_path,
			boolean adult,
			String overview,
			String release_date,
			int[] genre_ids,
			int id,
			String original_title,
			String original_language,
			String title,
			String backdrop_path,
			double popularity,
			int vote_count,
			boolean video,
			double vote_average) {
		
		this.poster_path = poster_path;
		this.adult = adult;
		this.overview = overview;
		this.release_date = release_date;
		this.genre_ids = genre_ids;
		this.id = id;
		this.original_title = original_title;
		this.original_language = original_language;
		this.title = title;
		this.backdrop_path = backdrop_path;
		this.popularity = popularity;
		this.vote_count = vote_count;
		this.video = video;
		this.vote_average = vote_average;
		
	}
	
	public String getPoster_path() {
		return poster_path;
	}
	
	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}
	
	public boolean isAdult() {
		return adult;
	}
	
	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public String getOverview() {
		return overview;
	}
	
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public String getRelease_date() {
		return release_date;
	}
	
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	public int[] getGenre_ids() {
		return genre_ids;
	}
	
	public void setGenre_ids(int[] genre_ids) {
		this.genre_ids = genre_ids;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getOriginal_title() {
		return original_title;
	}
	
	public void setOriginal_title(String original_title) {
		this.original_title = original_title;
	}
	
	public String getOriginal_language() {
		return original_language;
	}
	
	public void setOriginal_language(String original_language) {
		this.original_language = original_language;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBackdrop_path() {
		return backdrop_path;
	}
	
	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}
	
	public double getPopularity() {
		return popularity;
	}
	
	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	
	public int getVote_count() {
		return vote_count;
	}
	
	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
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

	public Bitmap getOfflineBitmap() {
		return offlineBitmap;
	}

	public void setOfflineBitmap(Bitmap offlineBitmap) {
		this.offlineBitmap = offlineBitmap;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
