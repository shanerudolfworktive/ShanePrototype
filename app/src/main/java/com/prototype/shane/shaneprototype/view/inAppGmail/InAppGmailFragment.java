package com.prototype.shane.shaneprototype.view.inAppGmail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.view.BaseFragment;

/**
 * Created by shane on 11/28/16.
 */

public class InAppGmailFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flickr_shark_grid, container, false);

        return rootView;
    }
}
