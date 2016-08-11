package com.prototype.shane.shaneprototype.database.sql.music;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.prototype.shane.shaneprototype.util.Const;

import java.util.HashMap;

public class MusicContentProvider extends ContentProvider {

	public static final String AUTHORITY = "com.shanefrankly.content.provider";
	public static final String URL = "content://" + AUTHORITY + "/music_table";
	public static final Uri CONTENT_URI = Uri.parse(URL);


	private HashMap<String, String> musicMap;

	public static final int MUSICS = 1;// all musics
	public static final int MUSICS_ID = 2; // single music

	static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, MusicSQLiteOpenHelper.TABLE_NAME, 1);
		uriMatcher.addURI(AUTHORITY, MusicSQLiteOpenHelper.TABLE_NAME + "/#", 2);
	}

	MusicSQLiteOpenHelper musicSQLiteOpenHelper;
	SQLiteDatabase database;

	@Override
	public boolean onCreate() {
		Context context = getContext();
		musicSQLiteOpenHelper = new MusicSQLiteOpenHelper(context);
		database = musicSQLiteOpenHelper.getWritableDatabase();

		if(database == null)
			return false;
		else
			return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MusicSQLiteOpenHelper.TABLE_NAME);

		switch (uriMatcher.match(uri)) {
		case MUSICS:
			queryBuilder.setProjectionMap(musicMap);
			break;

		case MUSICS_ID:
			queryBuilder.appendWhere(MusicSQLiteOpenHelper.ID+ "=" + uri.getLastPathSegment());
			break;

		default:
			break;
		}

		if (sortOrder == null || sortOrder.equals("")) {
			sortOrder = MusicSQLiteOpenHelper.ID;
		}

		Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){

		case MUSICS:
			return Const.PROVIDER_TYPE_PREFIX_DIR + AUTHORITY +".music_database";

		case MUSICS_ID:
			return Const.PROVIDER_TYPE_PREFIX_ITEM + AUTHORITY + ".music_database";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long row = database.insert(MusicSQLiteOpenHelper.TABLE_NAME, "", values);

		// If record is added successfully
		if(row > 0) {
			Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
			getContext().getContentResolver().notifyChange(newUri, null);
			return newUri;
		}
		throw new SQLException("Fail to add a new record into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)){
		case MUSICS:
			// delete all the records of the table
			count = database.delete(MusicSQLiteOpenHelper.TABLE_NAME, selection, selectionArgs);
			break;
		case MUSICS_ID:
			String id = uri.getLastPathSegment();	//gets the id
			count = database.delete(MusicSQLiteOpenHelper.TABLE_NAME, MusicSQLiteOpenHelper.ID +  " = " + id + 
					(!TextUtils.isEmpty(selection) ? " AND (" +
							selection + ')' : ""), selectionArgs);
			break;
		default: 
			throw new IllegalArgumentException("Unsupported URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// no update function in this prototype
		return 0;
	}
}
