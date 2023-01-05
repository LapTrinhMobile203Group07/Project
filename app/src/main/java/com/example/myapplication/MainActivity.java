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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
//public class FragmentActivity extends AppCompatActivity{}


public class MainActivity extends FragmentActivity implements MainCallbacks{

    FragmentTransaction ft;
    FooterLayout footerLayout;
    SearchLayout searchLayout;
    HomeLayout homeLayout;
    AllPhotosLayout allPhotosLayout;
//    AllAlbumLayout allAlbumLayout;
    SpecificAlbumLayout specificAlbumLayout;
    PicturesFragment picturesFragment;
    //Tài
    GridView gridPhoto;
    ActionBar actionBar;
    public AlbumsFragment albumsFragment;
    Fragment selectedFragment;
    public FoldersFragment foldersFragment;
//    public FoldersFragment foldersFragment;
    public TrashedFragment trashedFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 1);
        setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction();
        homeLayout = HomeLayout.newInstance();
        ft.replace(R.id.mainFrag_holder, homeLayout);


        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        footerLayout = FooterLayout.newInstance();
        ft.replace(R.id.footFrag_holder, footerLayout);
        ft.commit();

//        foldersFragment = FoldersFragment.getInstance(MainActivity.this);
        picturesFragment = null;
        trashedFragment = null;


        albumsFragment = AlbumsFragment.getInstance(MainActivity.this);
        foldersFragment = FoldersFragment.getInstance(MainActivity.this);
    
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
//            else if (btn.equals("FOLDER-FLAG")){
//                ft = getSupportFragmentManager().beginTransaction();
//                picturesFragment = PicturesFragment.getInstance(foldersFragment.getContext(), btn, "FOLDER");
//                selectedFragment = picturesFragment;
//                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
//            }


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
//                AllAlbumLayout allAlbumLayout;
                albumsFragment= AlbumsFragment.getInstance(MainActivity.this);
                ft.replace(R.id.mainFrag_holder, albumsFragment);
                ft.commit();
            }
        }
        if (sender.equals("ALBUM-FLAG")) {
            try {
//                ft = getSupportFragmentManager().beginTransaction();
//                picturesFragment = PicturesFragment.getInstance(picturesFragment.getContext(), btn, "ALBUM");
//                ft.replace(R.id.mainFrag_holder, picturesFragment);
//                ft.commit();
//
                picturesFragment = PicturesFragment.getInstance(albumsFragment.getContext(), btn, "ALBUM");
                selectedFragment = picturesFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Can't call picture fragment!", Toast.LENGTH_SHORT).show();
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
