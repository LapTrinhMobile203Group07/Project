package com.example.myapplication;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView coursesGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_frame2);
        coursesGV = findViewById(R.id.GridView);
        ArrayList<Item> courseModelArrayList = new ArrayList<Item>();

        courseModelArrayList.add(new Item("1", R.drawable.avatar01));
        courseModelArrayList.add(new Item("2", R.drawable.avatar02));
        courseModelArrayList.add(new Item("3", R.drawable.avatar03));
        courseModelArrayList.add(new Item("4", R.drawable.avatar04));
        courseModelArrayList.add(new Item("5", R.drawable.avatar05));
        courseModelArrayList.add(new Item("6", R.drawable.avatar06));
        courseModelArrayList.add(new Item("7", R.drawable.avatar06));

        GridApdapter adapter = new GridApdapter(this, courseModelArrayList);
        coursesGV.setAdapter(adapter);
    }
}