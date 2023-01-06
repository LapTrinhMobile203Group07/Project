package com.example.myapplication;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
//public class FragmentActivity extends AppCompatActivity{}


public class MainActivity extends AppCompatActivity implements MainCallbacks{

    FragmentTransaction ft;
    FooterLayout footerLayout;
    SearchLayout searchLayout;
    HomeLayout homeLayout;
    AllPhotosLayout1 allPhotosLayout;
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
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA}, 1);
        setContentView(R.layout.activity_main);
        //Main
        ft = getSupportFragmentManager().beginTransaction();
        albumsFragment= AlbumsFragment.getInstance(MainActivity.this);
        ft.replace(R.id.mainFrag_holder, albumsFragment);

        ft.commit();
        //Footer
        ft = getSupportFragmentManager().beginTransaction();
        footerLayout = FooterLayout.newInstance();
        ft.replace(R.id.footFrag_holder, footerLayout);
        ft.commit();


//        foldersFragment = FoldersFragment.getInstance(MainActivity.this);
        picturesFragment = null;
        trashedFragment = null;
        actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.picture);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        albumsFragment = AlbumsFragment.getInstance(MainActivity.this);
        foldersFragment = FoldersFragment.getInstance(MainActivity.this);
    
    }


    // MainCallback implementation (receiving messages coming from Fragments)
    @Override
    public void onMsgFromFragToMain(String sender, String btn){
        if (sender.equals("Footer_Layout")){
            if (btn.equals("All_Photo_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
//                allPhotosLayout = foldersFragment;
                ft.replace(R.id.mainFrag_holder,foldersFragment);


                ft.commit();
            }

            else if (btn.equals("ALBUM-FLAG")){
                ft = getSupportFragmentManager().beginTransaction();
                albumsFragment= AlbumsFragment.getInstance(MainActivity.this);
                ft.replace(R.id.mainFrag_holder, albumsFragment);
                ft.commit();
            }


            else if (btn.equals("Search_Layout")){
                ft = getSupportFragmentManager().beginTransaction();
                searchLayout = SearchLayout.newInstance();
                ft.replace(R.id.mainFrag_holder, searchLayout);
                ft.commit();
            }
        }
            if (sender.equals("ALBUM-FLAG")) {
                try {
                    if (btn.equals("Trashed")) {
                        trashedFragment = TrashedFragment.getInstance(albumsFragment.getContext());
                        selectedFragment = trashedFragment;
                    }  else{
                        Log.d("Tag", "Albums FLAG ");
                        picturesFragment = PicturesFragment.getInstance(albumsFragment.getContext(), btn, "ALBUM");
                        selectedFragment = picturesFragment;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Can't call picture fragment!", Toast.LENGTH_SHORT).show();
                }
        }
        if (sender.equals("FOLDER-FLAG")) {
            try {
                Log.d("Tag", "FOLDER FLAG ");
                picturesFragment = PicturesFragment.getInstance(foldersFragment.getContext(), btn, "FOLDER");
                selectedFragment = picturesFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
            }
            catch (Exception e) {
                Toast.makeText(MainActivity.this, "Can't call picture fragment!", Toast.LENGTH_SHORT).show();
            }
        }
        if (sender.equals("PICTURES-FLAG")) {
//                if (btn.contains("Open Url Dialog"))
//                    new UrlDialogFragment().show(getSupportFragmentManager(), UrlDialogFragment.Tag);
//                else
            if (btn.contains("Turn back folder")) {
                selectedFragment = foldersFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
            } else if (btn.contains("Turn back album")) {
                selectedFragment = albumsFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrag_holder, selectedFragment).commit();
            }
        }




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
