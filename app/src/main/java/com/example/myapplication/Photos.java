package com.example.myapplication;

import android.net.Uri;

import java.io.Serializable;

public class Photos implements Serializable {
    String path;
    public Photos( String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
}