package com.framgia.lam.intent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;
import com.framgia.lam.intent.utils.LoadingFile;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final int COUNT =2;
    private static final int REQUEST =1;
    private RecyclerView mRclImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRclImage = findViewById(R.id.rcl_anh);
        GridLayoutManager manager = new GridLayoutManager(this,COUNT);
        mRclImage.setLayoutManager(manager);
        initPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mRclImage.setAdapter(new LoadImage().execute().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
                createAnh();
            }
        } else {
            createAnh();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createAnh();
        }
    }

    private ArrayList<String> createAnh(){
        File imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        String[] format = getResources().getStringArray(R.array.image_format);
        if(imageDirectory != null) {
            return LoadingFile.loadImageFileName(imageDirectory,format);
        }
        return new ArrayList<>();
    }


    class LoadImage extends AsyncTask<Void,Void, ImageAdapter>{
        @Override
        protected ImageAdapter doInBackground(Void... images) {

            return new ImageAdapter(MainActivity.this,createAnh(),getLayoutInflater());
        }
        @Override
        protected void onPostExecute(ImageAdapter imageAdapter) {
            super.onPostExecute(imageAdapter);
        }
    }



}
