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

public class AnhAdapter extends RecyclerView.Adapter<AnhAdapter.AnhViewHolder> {
    private ArrayList<String> mListAnh;
    private LayoutInflater mInflater;
    private int mImageWidth , mImageHeight;
    private Context mContext;

    public AnhAdapter(Context context, ArrayList<String> listAnh, LayoutInflater inflater) {
        this.mListAnh = listAnh;
        this.mInflater = inflater;
        this.mContext = context;
        mImageWidth = getImage();
        mImageHeight = getImage();
    }

    @NonNull
    @Override
    public AnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnhViewHolder(mInflater.from(parent.getContext()).inflate(R.layout.item_anh,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull AnhViewHolder holder, int position) {
        String path = mListAnh.get(position);
        File file = new File(path);
        Picasso.with(mContext)
                .load(file)
                .resize(mImageWidth, mImageHeight)
                .centerCrop()
                .into(holder.mIcAnh);
    }
    private int getImage(){
        return (mContext.getResources().getDisplayMetrics().widthPixels)/2;
    }

    public void setListAnh(ArrayList<String> mListAnh) {
        this.mListAnh = mListAnh;
    }

    @Override
    public int getItemCount() {
        return mListAnh.size();
    }


    class AnhViewHolder extends RecyclerView.ViewHolder{
        ImageView mIcAnh;

        public AnhViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcAnh = itemView.findViewById(R.id.ic_anh);
        }
    }
}
