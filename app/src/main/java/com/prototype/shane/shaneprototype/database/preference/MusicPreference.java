package com.prototype.shane.shaneprototype.database.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MusicPreference {
	
	private SharedPreferences preferences;
	
	private static final String PREFERENCE_MUSIC = "music_preference";
	
	public MusicPreference(Context context){
        preferences= context.getSharedPreferences(PREFERENCE_MUSIC, Context.MODE_PRIVATE);
    }
	
    public void setStringData(String aKey, String aValue){
    	Editor editor = preferences.edit();
    	editor.putString(aKey, aValue);
    	editor.commit();
    }

    public String getStringData(String aKey){
       return preferences.getString(aKey, null);
    }
    
    public void clear(){
    	preferences.edit().clear().commit();
    }
}
