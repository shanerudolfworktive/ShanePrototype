package com.prototype.shane.shaneprototype.model.flickrShark;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shane on 7/9/16.
 */
public class FlickrInfoModel {
    public PhotoDetailsModel photo;

    public static class PhotoDetailsModel{
        public long id;
        public OwnerModel owner;
        public TitleModel title;
        public DescriptionModel description;
    }

    public static class OwnerModel{
        public String username;
    }

    public static class TitleModel{
        @SerializedName("_content")
        public String content;
    }

    public static class DescriptionModel{
        @SerializedName("_content")
        public String content;
    }

}
