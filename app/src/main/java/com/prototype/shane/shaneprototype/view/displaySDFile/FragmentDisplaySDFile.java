package com.prototype.shane.shaneprototype.view.displaySDFile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.displayFileModel.DisplayFileModel;
import com.prototype.shane.shaneprototype.view.BaseFragment;

import java.util.ArrayList;

/**
 * Created by shane on 8/10/16.
 */
public class FragmentDisplaySDFile extends BaseFragment{
    RecyclerView recyclerView;
    DisplayFileAdapter adapter = new DisplayFileAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_sd_file, container, false);
        loadInitialView(rootView);
        return rootView;
    }

    private void loadInitialView(View rootView){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    protected void setDisplayModels(ArrayList<DisplayFileModel> displayFileModels){
        adapter.setDisplayFileModels(displayFileModels);
    }
}
