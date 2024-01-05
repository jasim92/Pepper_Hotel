package com.example.pepper_hotel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.Model.RoomModel;
import com.example.pepper_hotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{
    ArrayList<RoomModel> roomModels;

    public RoomAdapter(ArrayList<RoomModel> roomModels) {
        this.roomModels = roomModels;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item,parent,false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.roomText.setText(roomModels.get(position).getRoomDescription());
        Picasso.get().load(roomModels.get(position).getRoomImg()).into(holder.roomImg);

    }

    @Override
    public int getItemCount() {
        return roomModels.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{
        ImageView roomImg;
        TextView roomText;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomImg = itemView.findViewById(R.id.room_image);
            roomText = itemView.findViewById(R.id.room_name);

        }
    }
}
