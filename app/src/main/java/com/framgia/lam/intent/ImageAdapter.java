package com.framgia.lam.intent;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private ArrayList<String> mListImage;
    private LayoutInflater mInflater;
    private int mImageWidth , mImageHeight;
    private Context mContext;

    public ImageAdapter(Context context, ArrayList<String> listImage, LayoutInflater inflater) {
        this.mListImage = listImage;
        this.mInflater = inflater;
        this.mContext = context;
        mImageWidth = getImage();
        mImageHeight = getImage();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(mInflater.from(parent.getContext()).inflate(R.layout.item_anh,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String path = mListImage.get(position);
        File file = new File(path);
        Picasso.with(mContext)
                .load(file)
                .resize(mImageWidth, mImageHeight)
                .centerCrop()
                .into(holder.mIcImage);
    }
    private int getImage(){
        return (mContext.getResources().getDisplayMetrics().widthPixels)/2;
    }

    public void setmListImage(ArrayList<String> mListImage) {
        this.mListImage = mListImage;
    }

    @Override
    public int getItemCount() {
        if (mListImage.size()==0){
            return 0;
        }
        return mListImage.size();
    }
    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mIcImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcImage = itemView.findViewById(R.id.ic_anh);
        }
    }
}
