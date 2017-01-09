package com.movieviewer.bll.data.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.movieviewer.bll.data.MovieViewerProvider;
import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;

public class DBStorageUtil {
	
	public static void insertPage(int page, List<MovieMetaData> movies, Context context) {
		for(MovieMetaData i : movies) {
			insertMovieMetaDataItem(context, i);
		}
	}
	
	public static Uri insertMovieMetaDataItem(Context context, MovieMetaData item) {
		return context.getContentResolver().insert(
				MovieViewerProvider.CONTENT_URI_POPULAR_MOVIES, 
				MovieItemTable.MovieItemToContentValues(item));
	}

	public static List<MovieMetaData> retrivePage(Context context, int page) {
		String URL = MovieViewerProvider.URL_POPULAR_MOVIES + "/" + page;
	    Uri uri = Uri.parse(URL);
	    Cursor c = context.getContentResolver().query(uri, MovieItemTable.PROJECTION, null, null, null);

	    List<MovieMetaData> data = new ArrayList<MovieMetaData>();
	    if (c.moveToFirst()) {
	       do{
	            data.add(MovieItemTable.cursorToItem(c));
	       } while (c.moveToNext());
	    }
	    
	    return data;
	}
	
	public static Uri insertMovieDetail(Context context, GetMovieDetailsResponce item) {
		return context.getContentResolver().insert(
				MovieViewerProvider.CONTENT_URI_MOVIE_DETAIL, 
				MovieDetailTable.MovieDetaiToContentValues(item));
	}
	
	public static GetMovieDetailsResponce retriveMovieDetails(Context context, int movieId) {
		String URL = MovieViewerProvider.URL_MOVIE_DETAIL + "/" + movieId;
	    Uri uri = Uri.parse(URL);
	    Cursor c = context.getContentResolver().query(uri, MovieDetailTable.PROJECTION, null, null, null);

	    if (c.moveToFirst()) {
	       return MovieDetailTable.cursorToItem(c);
	    }
	    
	    return null;
	}
	
}
