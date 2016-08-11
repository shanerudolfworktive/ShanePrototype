package com.prototype.shane.shaneprototype.database.sql.music;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicSQLiteOpenHelper extends SQLiteOpenHelper {


	public static final String ID = "_id";
	public static final String TITLE = "title";
	public static final String ARTIST = "artist";
	public static final String ALBUM = "album";

	public static final String DATABASE_NAME = "music_database";
	public static final String TABLE_NAME = "music_table";
	public static final int DATABASE_VERSION = 1;
	
	private static final String CREATE_TABLE =
			" CREATE TABLE " + TABLE_NAME +
			" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
			" title TEXT NOT NULL, " +
			" artist TEXT NOT NULL, " +
			" album TEXT NOT NULL);";

	public MusicSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//this prototype simply drop the table and recreate one
		db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
		onCreate(db);
	}

}
