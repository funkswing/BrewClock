package com.example.brewclock;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class TeaData extends SQLiteOpenHelper {
	// private static final means its a constant
	private static final String DATABASE_NAME = "teas.db";
	private static final int DATABASE_VERSION = 1;
	
	// public static final means its a constant
	public static final String TABLE_NAME = "teas";
	
	public static final String _ID = BaseColumns._ID;
	public static final String NAME = "name";
	public static final String BREW_TIME = "brew_time";

	public TeaData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE teas (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT
		// NULL, brew_time INTEGER);
		String sql = 
			"CREATE TABLE " + TABLE_NAME + " ("
			+ _ID + " INTEGAR PRIMARY KEY AUTOINCREMENT, "
			+ NAME + " TEXT NOT NULL, "
			+ BREW_TIME + " INTEGER" 
			+ ");";
		
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public void insert(String name, int brewTime) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NAME,  name);
		values.put(BREW_TIME,  brewTime);
		
		db.insertOrThrow(TABLE_NAME, null, values);
	}
	
	public Cursor all(Activity activity) {
		String[] from = { _ID, NAME, BREW_TIME };
		String order = NAME;
		
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, from, null, null, null, null, order);
		activity.startManagingCursor(cursor);
		
		return cursor;
	}
}
