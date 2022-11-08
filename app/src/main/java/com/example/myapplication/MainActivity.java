package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;



import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements MainCallbacks {

    FragmentTransaction ft;
    FragmentFooter fragmentFooter;
    FragmentSearch fragmentSearch;
    FragmentFrame1 fragmentFrame1;
    FragmentFrame2 fragmentFrame2;
    FragmentFrame5 fragmentFrame5;
    FragmentFrame6 fragmentFrame6;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getSupportFragmentManager().beginTransaction();
        fragmentFrame1 = FragmentFrame1.newInstance();
        ft.replace(R.id.mainFrag_holder, fragmentFrame1);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        fragmentFooter = FragmentFooter.newInstance();
        ft.replace(R.id.footFrag_holder, fragmentFooter);
        ft.commit();
    
    }


    // MainCallback implementation (receiving messages coming from Fragments)
    @Override
    public void onMsgFromFragToMain(String sender, String btn){
        if (sender.equals("Footer")){
            if (btn.equals("Photos")){
                ft = getSupportFragmentManager().beginTransaction();
                fragmentFrame2 = FragmentFrame2.newInstance();
                ft.replace(R.id.mainFrag_holder, fragmentFrame2);
                ft.commit();
            }
            else if (btn.equals("Albums")){
                ft = getSupportFragmentManager().beginTransaction();
                fragmentFrame1 = FragmentFrame1.newInstance();
                ft.replace(R.id.mainFrag_holder, fragmentFrame1);
                ft.commit();
            }
            else if (btn.equals("Search")){
                ft = getSupportFragmentManager().beginTransaction();
                fragmentSearch = FragmentSearch.newInstance();
                ft.replace(R.id.mainFrag_holder, fragmentSearch);
                ft.commit();
            }

        }
    }

}

    /*
    GridView coursesGV;
    Button opendialog;
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
    public void Opendialog(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.activity_fragment_frame7);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        opendialog=(Button)findViewById(R.id.btn);
        opendialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opendialog();
            }
        });
    }
}
*/
