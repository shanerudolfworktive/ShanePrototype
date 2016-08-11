package com.prototype.shane.shaneprototype.customObj;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.database.sql.music.MusicSQLiteOpenHelper;


public class MusicListAdapter extends CursorAdapter {
	private LayoutInflater layoutInflater;

	public MusicListAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context arg1, Cursor cursor) {
		TextView textViewTitle = (TextView) view.findViewById(R.id.textViewTitleValue);
		textViewTitle.setText(cursor.getString(cursor.getColumnIndex(MusicSQLiteOpenHelper.TITLE)));
		
		TextView textViewArtist = (TextView) view.findViewById(R.id.textViewArtistValue);
		textViewArtist.setText(cursor.getString(cursor.getColumnIndex(MusicSQLiteOpenHelper.ARTIST)));
		
		TextView textViewAlbum = (TextView) view.findViewById(R.id.textViewAlbumValue);
		textViewAlbum.setText(cursor.getString(cursor.getColumnIndex(MusicSQLiteOpenHelper.ALBUM)));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.row_music_list, parent, false);
		
	}
	
}
