package com.prototype.shane.shaneprototype.view.musicSearch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.customObj.MusicListAdapter;
import com.prototype.shane.shaneprototype.database.preference.MusicPreference;
import com.prototype.shane.shaneprototype.view.BaseFragment;

//this class takes view management and state persistent
public class FragmentMusicSearch extends BaseFragment{
	private ListView listViewMusic;
	private EditText editTextTitle;
	private EditText editTextArtist;
	private EditText editTextAlbum;
	private Button buttonSave;

	MusicPreference musicPreference;

	//persistent
	public static final String KEY_TITLE = "shared_pref_title_key";
	public static final String KEY_Artist = "shared_pref_artist_key";
	public static final String KEY_Album = "shared_pref_album_key";

	///////////////////// API /////////////////////
	public ListView getMusicListView(){
		return listViewMusic;
	}

	public EditText getEditTextTitle(){
		return editTextTitle;
	}

	public EditText getEditTextArtist(){
		return editTextArtist;
	}

	public EditText getEditTextAlbum(){
		return editTextAlbum;
	}

	public Button getButtonSave(){
		return buttonSave;
	}

	public void clearAllFields(){
		editTextTitle.setText("");
		editTextArtist.setText("");
		editTextAlbum.setText("");
	}
	
	public void enableSwipable(OnDismissCallback onDismissCallback){
		SwipeDismissAdapter swipeDismissAdapter = new SwipeDismissAdapter((MusicListAdapter)listViewMusic.getAdapter(), onDismissCallback);
		swipeDismissAdapter.setAbsListView(listViewMusic);
	}

	///////////////////// Life Cycle /////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		musicPreference = new MusicPreference(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View rootView = inflater.inflate(R.layout.fragment_music_search, container, false);
		listViewMusic = (ListView) rootView.findViewById(R.id.listViewMusic);

		//restore states
		editTextTitle =  (EditText) rootView.findViewById(R.id.editTextTitle);
		String prevTitle = musicPreference.getStringData(KEY_TITLE);
		if (prevTitle != null && prevTitle.length()>0) editTextTitle.setText(prevTitle);

		editTextArtist = (EditText) rootView.findViewById(R.id.editTextArtist);
		String prevArtist = musicPreference.getStringData(KEY_Artist);
		if (prevArtist != null && prevArtist.length()>0) editTextArtist.setText(prevArtist);

		editTextAlbum = (EditText) rootView.findViewById(R.id.editTextAlbum);
		String prevAlbum= musicPreference.getStringData(KEY_Album);
		if (prevAlbum != null && prevAlbum.length()>0) editTextAlbum.setText(prevAlbum);

		buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

		musicPreference.clear();
		return rootView;
	}

	@Override
	public void onStop() {
		super.onStop();
		musicPreference.setStringData(KEY_TITLE, editTextTitle.getText().toString());
		musicPreference.setStringData(KEY_Artist, editTextArtist.getText().toString());
		musicPreference.setStringData(KEY_Album, editTextAlbum.getText().toString());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		listViewMusic = null;
		editTextTitle = null;
		editTextArtist = null;
		editTextAlbum = null;
		buttonSave = null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
