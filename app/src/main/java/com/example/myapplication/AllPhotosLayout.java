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

public class AllPhotosLayout extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    GridView gridPhoto;
    Button btnSelect;

    public static AllPhotosLayout newInstance(){
        AllPhotosLayout fragment = new AllPhotosLayout();
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

        LinearLayout All_Photo_layout= (LinearLayout) inflater.inflate(R.layout.all_photos_layout, null);
        assignViewByFindId(All_Photo_layout);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        return All_Photo_layout;
    }

    private void assignViewByFindId(LinearLayout layout){
        gridPhoto = (GridView) layout.findViewById(R.id.gridPhoto);
        btnSelect = (Button) layout.findViewById(R.id.btnSelect);
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}
