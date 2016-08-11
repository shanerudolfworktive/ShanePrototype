package com.prototype.shane.shaneprototype.controller.displaySDFile;

import android.os.Bundle;

import com.prototype.shane.shaneprototype.model.displayFileModel.DisplayFileModel;
import com.prototype.shane.shaneprototype.view.displaySDFile.FragmentDisplaySDFile;

import java.util.ArrayList;

/**
 * Created by shane on 8/10/16.
 */
public class ControllerDisplaySDFile extends FragmentDisplaySDFile{
    ArrayList<DisplayFileModel> displayFileModels;

    public static ControllerDisplaySDFile create(ArrayList<DisplayFileModel> displayFileModels){
        ControllerDisplaySDFile controller = new ControllerDisplaySDFile();
        controller.displayFileModels = displayFileModels;
        return controller;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayModels(displayFileModels);
    }
}
