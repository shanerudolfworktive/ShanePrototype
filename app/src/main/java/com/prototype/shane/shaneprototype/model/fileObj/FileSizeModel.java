package com.prototype.shane.shaneprototype.model.fileObj;

/**
 * Created by shane on 8/9/16.
 */
public class FileSizeModel {
    public String fileName;
    public long size;

    public FileSizeModel(String fileName, long size){
        this.size = size;
        this.fileName = fileName;
    }
}
