package com.example.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class AllAlbumLayout extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    Button btnSelect;
    ListView  gridAlbum;
    //=============
    ListView ItemLV;

    ImageItem[] items =
            {
                    new ImageItem("Nguyễn Văn A", R.drawable.avatar01),
                    new ImageItem("Lê Thị B", R.drawable.avatar02),
                    new ImageItem("Trần Văn C", R.drawable.avatar03),
                    new ImageItem("Phan Văn C", R.drawable.avatar04),
                    new ImageItem("Đinh Văn D", R.drawable.avatar05),
                    new ImageItem("Thái Mai Khánh Vy", R.drawable.avatar06),
                    new ImageItem("Trần Đức Anh", R.drawable.avatar07),
            };

    //===============

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
        //Tài
        GridApdapter adapter = new GridApdapter(getContext(), R.layout.custom_item_list, items);
        ItemLV.setAdapter(adapter);
        //

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });


        return All_Album_layout;
    }

    private void assignViewByFindId(LinearLayout layout){
//        gridAlbum = (GridView) layout.findViewById(R.id.gridAlbum);
        btnSelect = layout.findViewById(R.id.btnSelect);
        ItemLV =  layout.findViewById(R.id.ListView);

    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}