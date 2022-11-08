package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class FragmentFrame2 extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;

    public static FragmentFrame2 newInstance(){
        FragmentFrame2 fragment = new FragmentFrame2();
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

        LinearLayout layout= (LinearLayout) inflater.inflate(R.layout.activity_fragment_frame2, null);

        return layout;
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
}