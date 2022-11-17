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

public class HomeLayout extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    Button btnGo, btnSeeAll;

    public static HomeLayout newInstance(){
        HomeLayout fragment = new HomeLayout();
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
        LinearLayout home_layout = (LinearLayout) inflater.inflate(R.layout.home_layout, null);

        assignViewByFindId(home_layout);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("Home_Layout", "All_Album_Layout");
            }
        });


        return home_layout;
    }

    private void assignViewByFindId(LinearLayout layout){
        btnGo = (Button) layout.findViewById(R.id.btnGo);
        btnSeeAll = (Button) layout.findViewById(R.id.btnSeeAll);
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}