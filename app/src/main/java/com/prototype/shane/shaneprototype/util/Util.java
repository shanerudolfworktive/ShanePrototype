package com.prototype.shane.shaneprototype.util;

import android.os.Environment;

/**
 * Created by shane on 8/9/16.
 */
public class Util {

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static String getDisplayedFileSize(long fileSize){
        String value;

        if(fileSize>=1024) value=fileSize/1024+" Mb";
        else value=fileSize+" Kb";

        return value;
    }

    public static String getFileExtension(String fileName){
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index != -1) extension = fileName.substring(index+1);
        return extension;
    }

    public static String getFlickrPhotoInfoURL(long id){
        return Const.URL_BASE + "services/rest/?method=flickr.photos.getInfo&api_key=" + Const.API_KEY + "&nojsoncallback=1&photo_id="+ id + "&format=json";
    }
}
