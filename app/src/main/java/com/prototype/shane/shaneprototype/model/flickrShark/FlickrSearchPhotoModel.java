package com.prototype.shane.shaneprototype.model.flickrShark;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shane on 7/9/16.
 */
public class FlickrSearchPhotoModel {

    public PhotosModel photos;

    public static class PhotosModel{
        public int page;
        public int pages;
        public int perpage;
        public int total;
        public ArrayList<PhotoModel> photo;
    }


    public static class PhotoModel{
        public long id;
        public String title;
        @SerializedName("url_t")
        public String thumbNeilUrl;
        @SerializedName("url_c")
        public String mediumPhotoUrl;
        @SerializedName("url_l")
        public String largePhotoUrl;
        @SerializedName("url_o")
        public String originalPhotoUrl;

    }
}
