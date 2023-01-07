package com.example.myapplication;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Environment.isExternalStorageManager;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements MainCallbacks {

    FragmentTransaction ft;
    FooterLayout footerLayout;
    SearchLayout searchLayout;

    HomeLayout homeLayout;
    AllPhotosLayout allPhotosLayout;
    AllAlbumLayout allAlbumLayout;
    private ActivityResultCallback<Boolean> result;
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result);
    SpecificAlbumLayout specificAlbumLayout;
    //Tài
    GridView gridPhoto;

    //Đức Anh
    Context context = this;
    FloatingActionButton btnFloat, btnCamera, btnSlideshow;
    Uri cam_uri;

    Animation rotateOpen, rotateClose, show, hide;
    private boolean isClicked = false;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();

        ft = getSupportFragmentManager().beginTransaction();
        homeLayout = HomeLayout.newInstance();
        ft.replace(R.id.mainFrag_holder, homeLayout);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        footerLayout = FooterLayout.newInstance();
        ft.replace(R.id.footFrag_holder, footerLayout);
        ft.commit();


        // Permission
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 001);
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 002);
        }


        //Float button
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloat);
        btnCamera = (FloatingActionButton) findViewById(R.id.btnCamera);
        btnSlideshow = (FloatingActionButton) findViewById(R.id.btnSlideshow);

        // Animation
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_animation);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_animation);
        show = AnimationUtils.loadAnimation(this, R.anim.show_animation);
        hide = AnimationUtils.loadAnimation(this, R.anim.hide_animation);

        btnFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked = !isClicked;

                //Visibility & Animation

                if (isClicked){
                    btnCamera.setClickable(true);
                    btnCamera.setVisibility(View.VISIBLE);

                    btnSlideshow.setClickable(true);
                    btnSlideshow.setVisibility(View.VISIBLE);

                    btnFloat.startAnimation(rotateOpen);
                    btnCamera.startAnimation(show);
                    btnSlideshow.startAnimation(show);
                } else {
                    btnCamera.setClickable(false);
                    btnCamera.setVisibility(View.INVISIBLE);

                    btnSlideshow.setClickable(false);
                    btnSlideshow.setVisibility(View.INVISIBLE);

                    btnFloat.startAnimation(rotateClose);
                    btnCamera.startAnimation(hide);
                    btnSlideshow.startAnimation(hide);
                }

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 003);
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                }
            }
        });

        btnSlideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add slideshow
            }
        });
    }


    // Yêu cầu: Mở activity camera đồng thời trả về ảnh chụp được từ người dùng
    // Khó khăn: API mới cập nhật, ko thể sử dụng putExtra và ko thể sử dụng startActivityForResult
    //           Đồng thời cũng có quá ít hướng dẫn và kiến thức để làm
    private void openCamera(){
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Tạo path, file cho ảnh được chụp từ camera
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
        if(!directory.exists()){
            directory.mkdirs();
        }

        String fileName = "IMG" + System.currentTimeMillis() + ".jpg";
        File file = new File(directory, fileName);
        cam_uri = Uri.fromFile(file); // Có thể có vấn đề

        // Lý thuyết cũ, dùng putExtra và startActivityForResult để lưu ảnh, nhưng đã ngừng hỗ trợ
        //camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        // Phải dùng hàm phía dưới nhưng hiện tại ko thể nên thay thế bằng hàm này
        startActivity(camera_intent);

        // Chắc chắn có vấn đề
        /*
        ActivityResultLauncher<Intent> takeCamera = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //Intent data = result.getData();
                        //Bitmap photo = (Bitmap) data.getExtras().get("data");
                    }
                }
        );
        takeCamera.launch(camera_intent);
        */
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

    public void requestPermissions (){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!hasAllFilesAccess()) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
    }
    public boolean hasAllFilesAccess() {
        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        try {
            ApplicationInfo app  = getPackageManager().getApplicationInfo(getPackageName(), 0);

            if (SDK_INT < Build.VERSION_CODES.Q) {
                return true;
            }
            else {
                return appOpsManager.unsafeCheckOpNoThrow(
                        "android:manage_external_storage",
                        app.uid,
                        getPackageName()
                ) == AppOpsManager.MODE_ALLOWED;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }




}
