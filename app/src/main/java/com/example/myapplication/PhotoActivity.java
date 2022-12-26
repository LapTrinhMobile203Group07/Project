package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
=======
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
>>>>>>> 6caaee0ac43a5e7eee39140219fe3eff6cb5e2c9
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhotoActivity extends Activity {
    private Context context;

    ImageButton btnShare, btnFavorite, btnInfo, btnDelete;
    Button btnEdit, button_clickback;
    Photos photoCur;
    TextView photoDateCreate, photoTimeCreate;
    ViewPager viewPager;
    Integer position = 0;
    ArrayList<Photos> arrayList = new ArrayList<>();
    BasicFileAttributes attr = null;
    String ext = "";
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_item_page);
        assignViewByFindId();

        GetAllPhotos();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT);
        position = (Integer) getIntent().getExtras().get("position");
        photoCur =  arrayList.get(position);
        setTime();
        String path = photoCur.getPath();
        ext = path.substring(path.lastIndexOf('.'));

        ImageAdapter adapter = new ImageAdapter(this, arrayList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                photoCur = arrayList.get(position);
                setTime();
            }
        });



        button_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(PhotoActivity.this,EditPhoto.class);
                intent.putExtra("ImageUri", photo.getPath());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String image_path = photoCur.getPath();
                File file = new File(image_path);
                Uri imageUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        BuildConfig.APPLICATION_ID +".provider", //(use your app signature + ".provider" )
                        file);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                intent.setFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION );
                startActivity(intent);
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    opendialog_Info(photoCur, sdf, sdf.format(attr.creationTime().toMillis()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

    }

    private void assignViewByFindId(){
        viewPager = findViewById(R.id.viewPager);
        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnFavorite = (ImageButton) findViewById(R.id.btnFavorite);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        button_clickback = (Button) findViewById(R.id.button_clickback);
        photoDateCreate = (TextView) findViewById(R.id.photoDateCreate);
        photoTimeCreate = (TextView) findViewById(R.id.photoTimeCreate);
    }


    private void opendialog_Info(Photos photo, SimpleDateFormat sdf, String last_Access) throws IOException {
        File current_file = new File(photo.getPath());
        Log.e("onClick: ", current_file.getName());

        View photo_InfoView = LayoutInflater.from(this).inflate(R.layout.photo_info, null);
        EditText filename = photo_InfoView.findViewById(R.id.info_filename);
        TextView filepath = photo_InfoView.findViewById(R.id.info_filepath);
        TextView lastAccess = photo_InfoView.findViewById(R.id.info_lastAccess);
        TextView lastModified = photo_InfoView.findViewById(R.id.info_lastModified);
        TextView filesize = photo_InfoView.findViewById(R.id.info_filesize);
        Button btnEditInfo = photo_InfoView.findViewById(R.id.btnEditInfo);

        filename.setText(current_file.getName());
        filepath.setText(current_file.getAbsolutePath());
        lastAccess.setText(last_Access);
        lastModified.setText(sdf.format(current_file.lastModified()));

        long fileSizeNumber = Math.round(current_file.length() * 1.0 / 1000);
        String fileSizeResult;
        if (fileSizeNumber > 2000)
            fileSizeResult = String.format(Locale.ROOT, "%.2f MB", fileSizeNumber * 1.0 / 1000);
        else
            fileSizeResult = String.format(Locale.ROOT, "%d KB", fileSizeNumber);
        filesize.setText(fileSizeResult);

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(photo_InfoView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = current_file.getName();

                if(btnEditInfo.getText() == "Save"){
                    String image_path = photoCur.getPath();
                    String pathDirectory = image_path.substring(0, image_path.lastIndexOf('/')+1);
                    File directory = new File(pathDirectory);
                    File file = new File(directory, oldName);
                    File fileNew = new File(directory, filename.getText() + ext);

                    boolean renamed = file.renameTo(fileNew);
                    if (renamed) {
                        Toast.makeText(getBaseContext(), "Success!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getBaseContext(), "Fail!", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    return;
                }
                filename.setText(oldName.substring(0, oldName.lastIndexOf('.')));
                filename.setBackgroundResource(R.drawable.background_edittext);
                filename.setEnabled(true);
                filename.requestFocus();
                filename.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        if(filename.getText()+ext != oldName){
                            btnEditInfo.setText("Save");
                        }
                    }
                });

            }
        });
        dialog.show();
    }

    public void GetAllPhotos(){
        String[] projection = new String[]{
                MediaStore.Images.Media.DATA,
        };

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.e("getImages: ", images.toString());

        Cursor cur = getApplicationContext().getContentResolver().query(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );
        if (cur.moveToFirst()) {

            String dataImage;
            int dataColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA);
            do {
                // Get the field values
                dataImage = cur.getString(dataColumn);
                // Do something with the values.
                Log.e("ListingImages", " Data path Image=" + dataImage);
//                arrayList.add(new Photos(dataImage));
                Log.e("Images", " Data Image=" + new Photos(dataImage));
                arrayList.add(new Photos(dataImage));

            } while (cur.moveToNext());

        }
    }

    public void setTime(){
        try {
            attr = Files.readAttributes(Paths.get(photoCur.getPath()), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss", Locale.ROOT);
        photoDateCreate.setText(sdf_date.format(attr.creationTime().toMillis()));
        photoTimeCreate.setText(sdf_time.format(attr.creationTime().toMillis()));
    }

}
