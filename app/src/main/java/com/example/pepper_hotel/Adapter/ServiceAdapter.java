package com.example.pepper_hotel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pepper_hotel.ListItemClickListener;
import com.example.pepper_hotel.Model.ServiceModel;
import com.example.pepper_hotel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{
    ArrayList<ServiceModel> serviceModelArrayList;
    final public ListItemClickListener itemClickListener;

    public ServiceAdapter(ArrayList<ServiceModel> serviceModelArrayList, ListItemClickListener itemClickListener) {
        this.serviceModelArrayList = serviceModelArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        ServiceModel serviceModel = serviceModelArrayList.get(position);
        holder.textView.setText(serviceModel.getServiceText());
        Picasso.get().load(serviceModel.getServiceImg()).into( holder.imageView);

    }

    @Override
    public int getItemCount() {
        return serviceModelArrayList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.service_name);
            imageView = itemView.findViewById(R.id.service_image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServiceModel serviceModel = serviceModelArrayList.get(getAdapterPosition());
                    itemClickListener.onListItemClick(serviceModel.getId());
                }
            });
        }
    }
}
