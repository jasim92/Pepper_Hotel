package com.example.pepper_hotel.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.MainActivity;
import com.example.pepper_hotel.Model.HotelModel;
import com.example.pepper_hotel.R;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder>{
    ArrayList<HotelModel> hotelModelArrayList;
    Context context;

    public final ListItemClickListener hotelItemClickListener;

    public HotelAdapter(ListItemClickListener hotelItemClickListener, ArrayList<HotelModel> hotelModelArrayList) {
        this.hotelItemClickListener = hotelItemClickListener;
        this.hotelModelArrayList = hotelModelArrayList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_item, parent, false);
        return new HotelViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        HotelModel hotelModel = hotelModelArrayList.get(position);
        TextView hotel_tv = holder.textView;
        hotel_tv.setText(hotelModel.getHotelCat());

    }

    @Override
    public int getItemCount() {
        return hotelModelArrayList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.hotel_item_tv);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HotelModel hotelModel = hotelModelArrayList.get(getAdapterPosition());
                    hotelItemClickListener.onListItemClick(hotelModel.getId());
                }
            });

        }
    }
}
