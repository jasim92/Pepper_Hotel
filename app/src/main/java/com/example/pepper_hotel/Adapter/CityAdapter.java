package com.example.pepper_hotel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.R;
import com.example.pepper_hotel.Model.CityModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewModel> {
    public Context context;
    public ArrayList<CityModel> cityModelArrayList;
    final public ListItemClickListener cityItemListener;

    public CityAdapter(ArrayList<CityModel> cityModelArrayList, ListItemClickListener cityItemListener) {
        this.cityModelArrayList = cityModelArrayList;
        this.cityItemListener = cityItemListener;
    }

    @NonNull
    @Override
    public CityViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_items,parent,false);
        return new CityViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewModel holder, int position) {
        CityModel cityModel = cityModelArrayList.get(position);
        ImageView imageView = holder.imageView;
        Picasso.get().load(cityModel.getCityImgUrl()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return cityModelArrayList.size();
    }

    public class CityViewModel extends RecyclerView.ViewHolder{

        ImageView imageView;

        public CityViewModel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.city_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CityModel cityModel = cityModelArrayList.get(getAdapterPosition());
                    cityItemListener.onListItemClick(cityModel.getCityId());
                }
            });
        }
    }
}
