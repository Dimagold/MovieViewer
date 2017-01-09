package com.movieviewer.bll.data.db;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public abstract class DBOpenHelper<T> extends SQLiteOpenHelper  {

	public static final String Tag = DBOpenHelper.class.getSimpleName();

	protected Context context;
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "MoviewViewerDB";
	
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MovieDetailTable.CREATE_TABLE);
		db.execSQL(MovieItemTable.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXIST " + MovieDetailTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXIST " + MovieItemTable.TABLE_NAME);
		this.onCreate(db);
	}
	
	protected static String convertToBase64(Bitmap bitmap) {
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG,100,os);
	    byte[] byteArray = os.toByteArray();
	    return Base64.encodeToString(byteArray, 0);
	}

	protected static Bitmap convertFromBase64(String base64String) {
	    byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
	    Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	    return bitmapResult;
	}

}
