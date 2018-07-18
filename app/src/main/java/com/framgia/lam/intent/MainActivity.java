package com.framgia.lam.intent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.framgia.lam.intent.utils.LoadingFile;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int COUNT =2;
    private static final int REQUEST =1;
    private RecyclerView mRclImage;
    private ImageAdapter mAdapter;
    private ArrayList<String> mListImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRclImage = findViewById(R.id.rcl_anh);
        mListImage= new ArrayList<>();
        mAdapter = new ImageAdapter(this,mListImage,getLayoutInflater());
        GridLayoutManager manager = new GridLayoutManager(this,COUNT);
        mRclImage.setLayoutManager(manager);
        mRclImage.setAdapter(mAdapter);
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
            mListImage = LoadingFile.loadImageFileName(imageDirectory,format);
            mAdapter.setmListImage(mListImage);
            mAdapter.notifyDataSetChanged();
        }
    }


}
