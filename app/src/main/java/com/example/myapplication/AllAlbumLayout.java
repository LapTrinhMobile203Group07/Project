package com.example.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class AllAlbumLayout extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    Button btnSelect;

    public static AllAlbumLayout newInstance(){
        AllAlbumLayout fragment = new AllAlbumLayout();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity(); // use this reference to invoke main callbacks
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout All_Album_layout = (LinearLayout) inflater.inflate(R.layout.all_album_layout, null);
        assignViewByFindId(All_Album_layout);


        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });


        return All_Album_layout;
    }

    private void assignViewByFindId(LinearLayout layout){
        btnSelect = (Button) layout.findViewById(R.id.btnSelect);
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}