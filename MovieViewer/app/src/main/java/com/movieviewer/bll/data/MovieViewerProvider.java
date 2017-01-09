package com.movieviewer.bll.data;

import com.movieviewer.bll.data.db.MovieDetailTable;
import com.movieviewer.bll.data.db.MovieItemTable;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MovieViewerProvider extends ContentProvider {

	static final String PROVIDER_NAME = "com.movieviewer.bll.data.MovieViewerProvider";
	public static final String URL_POPULAR_MOVIES = "content://" + PROVIDER_NAME + "/popular_movies";
	public static final String URL_MOVIE_DETAIL = "content://" + PROVIDER_NAME + "/movie_detail";
	public static final Uri CONTENT_URI_POPULAR_MOVIES = Uri.parse(URL_POPULAR_MOVIES);
	public static final Uri CONTENT_URI_MOVIE_DETAIL = Uri.parse(URL_MOVIE_DETAIL);
	
	public static final int POPULAR_MOVIE_PAGE = 1;
	public static final int MOVIE_DETAIL_ID = 2;
	public static final int ADD_POPULAR_MOVIE = 3;
	public static final int ADD_MOVIE_DETAIL = 4;

	static final UriMatcher uriMatcher;
	static{
	   uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	   uriMatcher.addURI(PROVIDER_NAME, "popular_movies/#", POPULAR_MOVIE_PAGE);
	   uriMatcher.addURI(PROVIDER_NAME, "movie_detail/#", MOVIE_DETAIL_ID);
	   uriMatcher.addURI(PROVIDER_NAME, "popular_movies", ADD_POPULAR_MOVIE);
	   uriMatcher.addURI(PROVIDER_NAME, "movie_detail", ADD_MOVIE_DETAIL);
	}
	
	private MovieDetailTable tableMovieDetail;
	private MovieItemTable tableMovieItem;
	   
	@Override
	public boolean onCreate() {	         
		tableMovieDetail = new MovieDetailTable(getContext());
		tableMovieItem = new MovieItemTable(getContext());
	    return (tableMovieDetail == null || tableMovieItem == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor c = null;
		switch (uriMatcher.match(uri)) {
		case MOVIE_DETAIL_ID:     
			c = tableMovieDetail.getItem(Integer.parseInt(uri.getPathSegments().get(1)));
		    break;
		case POPULAR_MOVIE_PAGE:  
			c = tableMovieItem.getPage(Integer.parseInt(uri.getPathSegments().get(1)));
		    break;       
		default:  
			break;
		}
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){

        case POPULAR_MOVIE_PAGE:
           return ContentResolver.CURSOR_DIR_BASE_TYPE + "/com.movieviewer.bll.network.responce.dto.MovieMetaData";

        case MOVIE_DETAIL_ID:
           return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/com.movieviewer.bll.network.responce.GetMovieDetailsResponce";
        default:
           throw new IllegalArgumentException("Unsupported URI: " + uri);
     }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		Uri _uri = null;
		long rowId;
		
	    switch (uriMatcher.match(uri)){
	    case ADD_POPULAR_MOVIE:
	        rowId = tableMovieItem.addItem(values);
	        if (rowId > 0) {
	            _uri = ContentUris.withAppendedId(CONTENT_URI_POPULAR_MOVIES, rowId);
	            getContext().getContentResolver().notifyChange(_uri, null);    
	        }
	        break;
	    case ADD_MOVIE_DETAIL:
	        rowId = tableMovieDetail.addItem(values);
	        if (rowId > 0) {
	            _uri = ContentUris.withAppendedId(CONTENT_URI_MOVIE_DETAIL, rowId);
	            getContext().getContentResolver().notifyChange(_uri, null);    
	        }
	        break;
	    default: throw new SQLException("Failed to insert row into " + uri);
	    }
	    return _uri;         
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
