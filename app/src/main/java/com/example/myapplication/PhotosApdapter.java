package com.example.myapplication;


import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class PhotosApdapter extends ArrayAdapter<Photos>
{
    private final Context context;
    //Test
    private final ArrayList<Photos> items;
    ArrayList<Photos> arrayList = new ArrayList<>();


    public PhotosApdapter(Context context, int layoutToBeInflated, ArrayList<Photos> items)
    {
        super(context, R.layout.photo, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.photo, null);
        ImageView avatar = row.findViewById(R.id.avatar1);
        Log.d("ADebugTag", "Value: ============================================================");
        Log.d("ADebugTag", "Value: " + items.get(position).getPath());
//        avatar.setImageBitmap(BitmapFactory.decodeFile(items.get(position).getPath()));
//        Glide.with(getContext()).load(items.get(position).getPath()).into((ImageView) row);
//        Glide.with(getContext()).load(items.get(position).getPath()).into(avatar);
        avatar.setImageBitmap(BitmapFactory.decodeFile(items.get(position).getPath()));
        return (row);

    }

}
