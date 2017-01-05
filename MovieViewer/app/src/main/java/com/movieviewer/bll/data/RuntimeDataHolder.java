package com.movieviewer.bll.data;

import java.util.HashMap;

import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.GetPopularMoviesResponce;

/**
 * Data holder for runtime period. Holds all application data that was loaded from web.
 * HashMap<Integer, GetMovieDetailsResponce> moviesDetails - Hash data, saves previously loaded info.
 * 
 * @author Dima Goldenberg
 *
 */
public class RuntimeDataHolder {
	
	private static RuntimeDataHolder instance = null;
	private static final int DEFAULT_DATA_PAGE = 0;
	
	private HashMap<Integer, GetPopularMoviesResponce> popularMovies;
	private HashMap<Integer, GetMovieDetailsResponce> moviesDetails;
	private GetMovieDetailsResponce currentMovieDetail;
	private int currentMovieDetailId;
	private int currentMoviesPage;
	
	private RuntimeDataHolder() {
		this.popularMovies = new HashMap<Integer, GetPopularMoviesResponce>();
		this.moviesDetails = new HashMap<Integer, GetMovieDetailsResponce>();
		this.currentMoviesPage = 0;
	}
	
	public static synchronized RuntimeDataHolder getInstance() {
		if(instance == null) {
			instance = new RuntimeDataHolder();
		}
		return instance;
	}
	
	public void reset() {
		this.popularMovies.clear();
		this.moviesDetails.clear();
		this.currentMoviesPage = 0;
	}

	public HashMap<Integer, GetPopularMoviesResponce> getPopularMovies() {
		return popularMovies;
	}

	public void setPopularMovies(HashMap<Integer, GetPopularMoviesResponce> popularMovies) {
		this.popularMovies = popularMovies;
	}

	public HashMap<Integer, GetMovieDetailsResponce> getMoviesDetails() {
		return moviesDetails;
	}

	public void setMoviesDetails(HashMap<Integer, GetMovieDetailsResponce> moviesDetails) {
		this.moviesDetails = moviesDetails;
	}

	public int getCurrentMovieDetailId() {
		return currentMovieDetailId;
	}

	public void setCurrentMovieDetailId(int currentMovieDetailId) {
		this.currentMovieDetailId = currentMovieDetailId;
	}

	public int getCurrentMoviesPage() {
		return currentMoviesPage;
	}

	public void setCurrentMoviesPage(int currentMoviesPage) {
		this.currentMoviesPage = currentMoviesPage;
	}

	public GetMovieDetailsResponce getCurrentMovieDetail() {
		return currentMovieDetail;
	}

	public void setCurrentMovieDetail(GetMovieDetailsResponce currentMovieDetail) {
		this.currentMovieDetail = currentMovieDetail;
	}
	
}
