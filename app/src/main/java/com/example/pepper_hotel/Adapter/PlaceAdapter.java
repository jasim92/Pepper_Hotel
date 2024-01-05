package com.example.pepper_hotel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.Model.PlaceModel;
import com.example.pepper_hotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.placeViewHolder> {
    ArrayList<PlaceModel> placeModelArrayList;
    public final ListItemClickListener placeClickListener;

    public PlaceAdapter(ArrayList<PlaceModel> placeModelArrayList, ListItemClickListener placeClickListener) {
        this.placeModelArrayList = placeModelArrayList;
        this.placeClickListener = placeClickListener;
    }

    @NonNull
    @Override
    public placeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item,parent,false);
        return new placeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull placeViewHolder holder, int position) {
        PlaceModel placeModel = placeModelArrayList.get(position);
        TextView textView = holder.textView;
        ImageView imageView = holder.imageView;
        textView.setText(placeModel.getPlaceName());
        Picasso.get().load(placeModel.getPlaceImgUrl()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return placeModelArrayList.size();
    }

    public class placeViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public placeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.place_image);
            textView = itemView.findViewById(R.id.place_name);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaceModel placeModel = placeModelArrayList.get(getAdapterPosition());
                    placeClickListener.onListItemClick(placeModel.getPlaceId());
                }
            });
        }
    }
}
