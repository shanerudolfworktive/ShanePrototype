package com.prototype.shane.shaneprototype.model.rxAndroid;

import java.util.ArrayList;

/**
 * Created by shane on 3/1/17.
 */

public class MovieSearchModel {

    public ArrayList<MovieModel> Search = new ArrayList<>();

    public static class MovieModel{
        public String Title;
        public String Year;
        public String imdbID;
        public String Type;
        public String Poster;
    }
}
