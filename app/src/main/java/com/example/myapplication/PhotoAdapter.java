package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class PhotoAdapter extends ArrayAdapter<ImageItem> {

    private final Context context;
    private final ImageItem[] items;

    public PhotoAdapter(Context context, int layoutToBeInflated, ImageItem[] items){
        super(context, R.layout.photo, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View image = inflater.inflate(R.layout.photo, null);
        ImageView photo = (ImageView) image.findViewById(R.id.imagePhoto);
        //photo.setImage
        return image;
    }
}
