package com.example.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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

        assignViewByFindId_HomeLayout(home_layout);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opendialog();
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

    private void assignViewByFindId_HomeLayout(LinearLayout layout){
        btnGo = (Button) layout.findViewById(R.id.btnGo);
        btnSeeAll = (Button) layout.findViewById(R.id.btnSeeAll);
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }

    public void Opendialog(){
        Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_home_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_cancel_dialog = (Button) dialog.findViewById(R.id.btn_cancel_dialog);
        Button btn_save_dialog = (Button) dialog.findViewById(R.id.btn_save_dialog);

        btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_save_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
            }
        });

        dialog.show();
    }
}