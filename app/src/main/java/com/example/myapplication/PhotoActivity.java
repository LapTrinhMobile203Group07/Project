package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PhotoActivity extends Activity {
    private Context context;

    ImageButton btnShare, btnFavorite, btnInfo, btnDelete;
    Button btnEdit, button_clickback;
    ImageView img_photoActivity;
    TextView photoDateCreate, photoTimeCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_item_page);
        assignViewByFindId();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT);
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
        SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm:ss", Locale.ROOT);

        Photos photo = (Photos) getIntent().getExtras().get("photo");
        setImage(photo);

        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(Paths.get(photo.getPath()), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        photoDateCreate.setText(sdf_date.format(attr.creationTime().toMillis()));
        photoTimeCreate.setText(sdf_time.format(attr.creationTime().toMillis()));

        button_clickback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
            }
        });

        BasicFileAttributes finalAttr = attr;
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    opendialog_Info(photo, sdf, sdf.format(finalAttr.creationTime().toMillis()));
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
        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnFavorite = (ImageButton) findViewById(R.id.btnFavorite);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        button_clickback = (Button) findViewById(R.id.button_clickback);
        img_photoActivity = (ImageView) findViewById(R.id.img_photoActivity);
        photoDateCreate = (TextView) findViewById(R.id.photoDateCreate);
        photoTimeCreate = (TextView) findViewById(R.id.photoTimeCreate);
    }

    private void setImage(Photos photo){
        img_photoActivity.setImageURI(Uri.parse(photo.getPath()));
    }

    private void opendialog_Info(Photos photo, SimpleDateFormat sdf, String last_Access) throws IOException {
        File current_file = new File(photo.getPath());
        Log.e("onClick: ", current_file.getName());

        View photo_InfoView = LayoutInflater.from(this).inflate(R.layout.photo_info, null);
        TextView filename = photo_InfoView.findViewById(R.id.info_filename);
        TextView filepath = photo_InfoView.findViewById(R.id.info_filepath);
        TextView lastAccess = photo_InfoView.findViewById(R.id.info_lastAccess);
        TextView lastModified = photo_InfoView.findViewById(R.id.info_lastModified);
        TextView filesize = photo_InfoView.findViewById(R.id.info_filesize);

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
        dialog.show();
    }
}
