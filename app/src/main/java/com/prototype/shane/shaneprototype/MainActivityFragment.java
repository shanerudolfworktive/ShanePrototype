package com.prototype.shane.shaneprototype;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.shane.shaneprototype.controller.musicSearch.ControllerMusicSearch;
import com.prototype.shane.shaneprototype.controller.sdFileScan.ControllerSDFileScan;
import com.prototype.shane.shaneprototype.view.flickrSharkGrid.FragmentFlickrSharkGrid;
import com.prototype.shane.shaneprototype.view.wordSearch.FragmentSceneMain;

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
        rootView.findViewById(R.id.buttonSDFileScanPrototype).setOnClickListener(createSDFileScanClickListener());
        rootView.findViewById(R.id.buttonFlickrSharkPrototype).setOnClickListener(createFlickrSharkClickListener());
        rootView.findViewById(R.id.buttonWordSearchGamePrototype).setOnClickListener(createWordSearchGameClickListener());
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

    private View.OnClickListener createSDFileScanClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new ControllerSDFileScan()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createFlickrSharkClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new FragmentFlickrSharkGrid()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createWordSearchGameClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new FragmentSceneMain()).addToBackStack(null).commit();
            }
        };
    }
}
