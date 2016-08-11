package com.prototype.shane.shaneprototype;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.shane.shaneprototype.musicSearch.ControllerMusicSearch;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.findViewById(R.id.buttonMusicSearchPrototype).setOnClickListener(createMusicSearchClickListener());
        return rootView;
    }

    private View.OnClickListener createMusicSearchClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new ControllerMusicSearch()).addToBackStack(null).commit();
            }
        };
    }
}
