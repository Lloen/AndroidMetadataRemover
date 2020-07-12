package com.example.metadataremover;

import android.graphics.drawable.Drawable;

import java.io.File;

public class FileData {
    String name;
    Drawable thumbnail;
    File file;

    public FileData(File file) {
        this.name = file.getName();
        this.file = file;

    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }
}
