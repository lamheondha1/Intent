package com.framgia.lam.intent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int COUNT =2;
    private static final int REQUEST =1;
    private RecyclerView mRclAnh;
    private AnhAdapter mAdapter;
    private ArrayList<String> mListAnh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRclAnh = findViewById(R.id.rcl_anh);
        mListAnh = new ArrayList<>();
        mAdapter = new AnhAdapter(this,mListAnh,getLayoutInflater());
        GridLayoutManager manager = new GridLayoutManager(this,COUNT);
        mRclAnh.setLayoutManager(manager);
        mRclAnh.setAdapter(mAdapter);
        initPermission();

    }

    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST);
            } else {
                CreateAnh();
            }
        } else {
            CreateAnh();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CreateAnh();
        }
    }
    private void CreateAnh(){
        File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String[] format = getResources().getStringArray(R.array.image_format);
        if(imageDirectory != null) {
            mListAnh = LoadFile.loadImageFileName(imageDirectory,format);
            mAdapter.setListAnh(mListAnh);
            mAdapter.notifyDataSetChanged();
        }
    }


}
