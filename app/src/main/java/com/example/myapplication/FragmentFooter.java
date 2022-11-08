package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class FragmentFooter extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    Button btnPhotos, btnAlbums, btnSearch;

    public static FragmentFooter newInstance(){
        FragmentFooter fragment = new FragmentFooter();
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

        LinearLayout layout_footer = (LinearLayout) inflater.inflate(R.layout.activity_fragment_footer, null);

        assignViewByFindId(layout_footer);

        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("Footer", "Photos");
            }
        });

        btnAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("Footer", "Albums");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("Footer", "Search");
            }
        });
        return layout_footer;
    }

    private void assignViewByFindId(LinearLayout layout){
        btnPhotos = (Button)  layout.findViewById(R.id.btnPhotos);
        btnAlbums = (Button) layout.findViewById(R.id.btnAlbum);
        btnSearch = (Button) layout.findViewById(R.id.btnSearch);
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}