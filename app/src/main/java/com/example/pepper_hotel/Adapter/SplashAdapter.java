package com.example.pepper_hotel.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SplashAdapter extends RecyclerView.Adapter<SplashAdapter.SplashViewHolder>{
    private ArrayList<Integer> arrayList;

    public SplashAdapter(ArrayList<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SplashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_items,parent,false);
        return new SplashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SplashViewHolder holder, int position) {
        Picasso.get().load(arrayList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class SplashViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public SplashViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewMain);
        }
    }
}
