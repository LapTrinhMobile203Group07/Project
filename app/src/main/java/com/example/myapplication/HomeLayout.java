package com.example.myapplication;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




//Tai
//
import java.util.ArrayList;
import java.util.List;

public class HomeLayout extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context;
    Button btnGo, btnSeeAll;
    //Tai
    private View view;
    private List<MyImage> listImage;
    private RecyclerView ryc_album;
    private AlbumAdapter albumAdapter;
    private List<Album> listAlbum;
    private AlbumsAdapter albumsAdapter;
    //

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
        //Tai
        listImage = GetAllPhotoFromGallery.getAllImageFromGallery(this.getContext());
//        Log.d("list type", "============================================ ");
//        Log.d("list type", String.valueOf(listImage));
//        Log.d("list type", "============================================ ");
        setViewRyc();
        albumAdapter.setData(listAlbum);
        //

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opendialog();
            }
        });

        btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("Home_Layout", "ALBUM-FLAG");
            }
        });
        return home_layout;
    }// Oncreat
    @Override
    public void onStart() {
        super.onStart();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }//Onstart

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listImage = GetAllPhotoFromGallery.getAllImageFromGallery(getContext());
            listAlbum = getListAlbum(listImage);
            Log.d("List","");

            return null;
        }

        @NonNull
        private List<Album> getListAlbum(List<MyImage> listImage) {
            List<String> ref = new ArrayList<>();
            List<Album> listAlbum = new ArrayList<>();

            for (int i = 0; i < listImage.size(); i++) {
                String[] _array = listImage.get(i).getThumb().split("/");
                String _pathFolder = listImage.get(i).getThumb().substring(0, listImage.get(i).getThumb().lastIndexOf("/"));
                String _name = _array[_array.length - 2];
                if (!ref.contains(_pathFolder)) {
                    ref.add(_pathFolder);
                    Album token = new Album(listImage.get(i), _name);
                    token.setPathFolder(_pathFolder);
                    token.addItem(listImage.get(i));
                    listAlbum.add(token);
                } else {
                    listAlbum.get(ref.indexOf(_pathFolder)).addItem(listImage.get(i));
                }
            }
            return listAlbum;
        }//Get List Album

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            albumAdapter.setData(listAlbum);
//            progressDialog.cancel();
        }
    }

    private void setViewRyc() {
        ryc_album.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        albumAdapter = new AlbumAdapter(listAlbum, this.getContext());
        ryc_album.setAdapter(albumAdapter);
    }

    private void assignViewByFindId_HomeLayout(LinearLayout layout){
        btnGo = (Button) layout.findViewById(R.id.btnGo);
        btnSeeAll = (Button) layout.findViewById(R.id.btnSeeAll);
        //Tai
        ryc_album = layout.findViewById(R.id.ryc_album);
        //
    }

    @Override
    public void onMsgFromMainToFragment(String btn) {

    }
    private void addNewAlbum() {
        View addNewAlbumForm = LayoutInflater.from(context).inflate(R.layout.add_album_form, null);
        EditText edtAlbumName = addNewAlbumForm.findViewById(R.id.edtAlbumName);

        AlertDialog.Builder addDialog = new AlertDialog.Builder(context, R.style.AlertDialog);
        addDialog.setView(addNewAlbumForm);
        addDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newAlbumName = edtAlbumName.getText().toString();
                if (newAlbumName.length() != 0) {
                    if (AlbumUtility.getInstance(context).addNewAlbum(newAlbumName)) {
                        albumsAdapter.addAlbum(newAlbumName);
                        Toast.makeText(context, "New album created successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Error: Failed to create new album!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Empty Album Name!", Toast.LENGTH_LONG).show();
                }
            }
        });
        addDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        addDialog.create();
        addDialog.show();
    }

    public void Opendialog(){
        Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_home_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Tài
        EditText edtAlbumName = dialog.findViewById(R.id.AlbumName);

        //


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
                //Create Album here
                String newAlbumName = edtAlbumName.getText().toString();
                if (newAlbumName.length() != 0) {
                    if (AlbumUtility.getInstance(context).addNewAlbum(newAlbumName)) {
                        albumsAdapter.addAlbum(newAlbumName);
                        Toast.makeText(context, "New album created successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Error: Failed to create new album!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Empty Album Name!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }
}