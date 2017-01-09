package com.movieviewer.bll.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.movieviewer.bll.network.responce.GetMovieDetailsResponce;
import com.movieviewer.bll.network.responce.dto.Gener;

public class MovieDetailTable extends DBOpenHelper<GetMovieDetailsResponce>{

	public static final String Tag =  MovieDetailTable.class.getSimpleName();
	
	public final static String TABLE_NAME = "MovieDetail";
	public static final String MovieId = "_id";
	public static final String Title = "title";
	public static final String Date = "date";
	public static final String Duration = "duration";
	public static final String Description = "description";
	public static final String Geners = "geners";
	public static final String AverageRating = "rating";
	public static final String Poster = "poster";
	
	public static String[] PROJECTION = new String[] { MovieId, Title, Date, Duration, Description, Geners, AverageRating, Poster };
	
	public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" 
            + MovieId        + " integer primary key, " 
            + Title          + " text,"
            + Date       + " text,"
			+ Duration     + " integer,"
			+ Description     + " text,"
			+ Geners     + " text,"
			+ AverageRating     + " real,"
			+ Poster   + " text)";
	
	public MovieDetailTable(Context context) {
		super(context);
	}

	public Cursor getItem(int itemId) {
		SQLiteDatabase db = this.getReadableDatabase();
		 
        Cursor cursor = db.query(TABLE_NAME, 
        		new String[] { MovieId, Title, Date, Duration, Description, Geners, AverageRating, Poster }, 
        		MovieId + "=" + itemId, null, null, null, null, null);
     
        if (cursor != null)
            cursor.moveToFirst();
 
        db.close();
        return cursor;
	}

	public static ContentValues MovieDetaiToContentValues(GetMovieDetailsResponce item) {
		ContentValues values = new ContentValues();
        values.put(MovieId, item.getId()); 
        values.put(Title,  item.getOriginal_title());
        values.put(Date, item.getRelease_date());
        values.put(Duration,  item.getRuntime());
        values.put(Description, item.getOverview());
        values.put(Geners,  Gener.toStringObject(item.getGenres()));
        values.put(AverageRating, item.getVote_average());
        values.put(Poster, convertToBase64(item.getOfflinePoster()));
        return values;
	}
	
	public int addItem(ContentValues values) {
		SQLiteDatabase db = this.getWritableDatabase();
        
        int rowId = (int) db.insert(TABLE_NAME, null, values);
        db.close(); 
        
        return (int) rowId;
	}

	public static GetMovieDetailsResponce cursorToItem(Cursor cursor) {
		GetMovieDetailsResponce item = new GetMovieDetailsResponce();
		
		item.setId(cursor.getInt(0));
		item.setOriginal_title(cursor.getString(1));
		item.setRelease_date(cursor.getString(2));
		item.setRuntime(cursor.getInt(3));
		item.setOverview(cursor.getString(4));
		item.setGenres(Gener.fromStringObject(cursor.getString(5)));
		item.setVote_average(cursor.getDouble(6));
		item.setOfflinePoster(convertFromBase64(cursor.getString(7)));
		
		return item;
	}

}
