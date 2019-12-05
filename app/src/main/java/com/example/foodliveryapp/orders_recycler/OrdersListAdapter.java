package com.example.foodliveryapp.orders_recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodliveryapp.R;

import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrdersListAdapter(List<Order> orderList, Context context){
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.orderAddressTextView.setText(order.getOrderAddress());
        holder.orderTimeTextView.setText(order.getOrderTime());
        holder.restaurantNameTextView.setText(order.getRestaurantName());
        holder.restaurantAddresTextView.setText(order.getRestaurantAddress());
        holder.priceTypeTextView.setText(order.getPriceType());
        holder.priceTextView.setText(order.getPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderAddressTextView;
        public TextView orderTimeTextView;
        public TextView restaurantNameTextView;
        public TextView restaurantAddresTextView;
        public TextView priceTypeTextView;
        public TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderAddressTextView = (TextView) itemView.findViewById(R.id.li_order_address);
            orderTimeTextView = itemView.findViewById(R.id.li_order_time);
            restaurantNameTextView = itemView.findViewById(R.id.li_restaurant_name);
            restaurantAddresTextView = itemView.findViewById(R.id.li_restaurant_address);
            priceTextView = itemView.findViewById(R.id.li_payment_value);
            priceTypeTextView = itemView.findViewById(R.id.li_payment_type);
        }
    }
}
