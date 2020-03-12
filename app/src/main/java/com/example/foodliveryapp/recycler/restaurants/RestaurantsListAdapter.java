package com.example.foodliveryapp.recycler.restaurants;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodliveryapp.R;

import java.util.List;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.ViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantsListAdapter(List<Restaurant> restaurantList, Context context){
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);

        return new RestaurantsListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantsListAdapter.ViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);

        holder.restaurantNameTextView.setText(restaurant.getName());
        holder.callButton.setOnClickListener(v -> {
            Intent diallIntent = new Intent(Intent.ACTION_DIAL);
            diallIntent.setData(Uri.parse("tel:" + restaurant.getPhoneNumber()));
            context.startActivity(diallIntent);
        });

        holder.navButton.setOnClickListener(v -> {
            Uri mapUri =
                    Uri.parse("geo:0,0?q=" + Uri.encode(restaurant.getAddress()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantNameTextView;
        ImageButton callButton;
        ImageButton navButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantNameTextView = itemView.findViewById(R.id.rest_li_rest_name);
            callButton = itemView.findViewById(R.id.rest_li_phone_ib);
            navButton = itemView.findViewById(R.id.rest_li_nav_ib);
        }
    }

}
