package com.movieviewer.bll.data.db;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.movieviewer.bll.network.request.BaseRequest;
import com.movieviewer.bll.network.responce.GetPopularMoviesResponce;
import com.movieviewer.bll.network.responce.dto.MovieMetaData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

public class MovieItemTable extends DBOpenHelper<MovieMetaData>{

	public static final String Tag =  MovieItemTable.class.getSimpleName();
	
	public final static String TABLE_NAME = "MovieItem";
	public static final String ItemId = "_id";
	public static final String Page = "page";
	public static final String MovieId = "movieId";
	public static final String MovieName = "name";
	public static final String MoviePoster = "poster";
	
	public static String[] PROJECTION = new String[] { Page, MovieId, MovieName, MoviePoster };
	
	public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" 
            + ItemId        + " integer primary key autoincrement, " 
            + Page          + " integer,"
            + MovieId       + " integer,"
			+ MovieName     + " text,"
			+ MoviePoster   + " text)";
	
	public MovieItemTable(Context context) {
		super(context);
	}

	public Cursor getPage(int page) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		//ArrayList<MovieMetaData> moviesPage = new ArrayList<MovieMetaData>();
		Cursor cursor = db.query(TABLE_NAME, new String[] { Page, MovieId, MovieName, MoviePoster },
				Page + " = " + page, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			//moviesPage.add(cursorToItem(cursor));
			cursor.moveToNext();
		}
		
		//cursor.close();
		db.close();
		return cursor;
	}

	public static ContentValues MovieItemToContentValues(MovieMetaData item) {
		ContentValues values = new ContentValues();
        values.put(Page, item.getPage()); 
        values.put(MovieId,  item.getId());
        values.put(MovieName, String.valueOf(item.getOriginal_title()));
        values.put(MoviePoster,  convertToBase64(item.getOfflineBitmap()));
        return values;
	}
	
	public boolean addPage(List<ContentValues> valuesList) {
		for(ContentValues i : valuesList) {
			addItem(i);
		}
		return true;
	}
	
	public long addItem(ContentValues values) {
		final SQLiteDatabase db = this.getWritableDatabase();
		
		// Inserting Row
		long rowId = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return rowId;
	}

	public static MovieMetaData cursorToItem(Cursor cursor) {
		MovieMetaData movie = new MovieMetaData();
		movie.setId(cursor.getInt(0));
		movie.setOriginal_title(cursor.getString(1));
		movie.setOfflineBitmap(convertFromBase64(cursor.getString(2)));
		return movie;
	}

}
