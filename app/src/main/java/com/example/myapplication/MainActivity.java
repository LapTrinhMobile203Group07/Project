package com.example.myapplication;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements MainCallbacks {

    FragmentTransaction ft;
    FooterLayout footerLayout;
    SearchLayout searchLayout;

    
    HomeLayout homeLayout;
    AllPhotosLayout allPhotosLayout;
    AllAlbumLayout allAlbumLayout;
    SpecificAlbumLayout specificAlbumLayout;
    //Tài
    GridView gridPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction();
        homeLayout = HomeLayout.newInstance();
        ft.replace(R.id.mainFrag_holder, homeLayout);


        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        footerLayout = FooterLayout.newInstance();
        ft.replace(R.id.footFrag_holder, footerLayout);
        ft.commit();
    
    }


    // MainCallback implementation (receiving messages coming from Fragments)
    @Override
    public void onMsgFromFragToMain(String sender, String btn){
        if (sender.equals("Footer_Layout")){
            if (btn.equals("All_Photo_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
                allPhotosLayout = AllPhotosLayout.newInstance();
                ft.replace(R.id.mainFrag_holder, allPhotosLayout);


                ft.commit();
            }

            else if (btn.equals("Home_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
                homeLayout = HomeLayout.newInstance();
                ft.replace(R.id.mainFrag_holder, homeLayout);
                ft.commit();
            }
            else if (btn.equals("Search_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
                searchLayout = SearchLayout.newInstance();
                ft.replace(R.id.mainFrag_holder, searchLayout);
                ft.commit();
            }
        }
        if (sender.equals("Home_Layout")){
            if (btn.equals("All_Album_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
                allAlbumLayout = AllAlbumLayout.newInstance();
                ft.replace(R.id.mainFrag_holder, allAlbumLayout);
                ft.commit();
            }
        }
    }





}//Main activity



    /*
    GridView coursesGV;
    Button opendialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_photos_layout);
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
