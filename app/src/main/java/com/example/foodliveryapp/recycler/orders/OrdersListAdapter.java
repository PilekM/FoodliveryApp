package com.example.foodliveryapp.recycler.orders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodliveryapp.OrderDetailActivity;
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
        final Order order = orderList.get(position);

        holder.orderAddressTextView.setText(order.getOrderAddress());
        holder.orderTimeTextView.setText(order.getOrderTime());
        holder.restaurantNameTextView.setText(order.getRestaurantName());
        holder.restaurantAddresTextView.setText(order.getRestaurantAddress());
        holder.priceTypeTextView.setText(order.getPriceType());
        holder.priceTextView.setText(order.getPrice());

        holder.frameLayout.setBackgroundColor(getOrderColor(order.getOrderStatus()));

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderId", order.getOrderNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderAddressTextView;
        TextView orderTimeTextView;
        TextView restaurantNameTextView;
        TextView restaurantAddresTextView;
        TextView priceTypeTextView;
        TextView priceTextView;
        LinearLayout itemLayout;
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderAddressTextView = itemView.findViewById(R.id.li_order_address);
            orderTimeTextView = itemView.findViewById(R.id.li_order_time);
            restaurantNameTextView = itemView.findViewById(R.id.li_restaurant_name);
            restaurantAddresTextView = itemView.findViewById(R.id.li_restaurant_address);
            priceTextView = itemView.findViewById(R.id.li_payment_value);
            priceTypeTextView = itemView.findViewById(R.id.li_payment_type);
            itemLayout = itemView.findViewById(R.id.li_main_lin_layout);
            frameLayout = itemView.findViewById(R.id.li_frame);
        }
    }

    private int getOrderColor(int orderStatus){

        int color = 0;
        switch(orderStatus) {

            case 0: {
                color = Color.BLUE;
                break;
            }

            case 1:

            case 2:

            case 3:

            case 4: {
                color = Color.YELLOW;
                break;
            }

            case 5: {
                color = Color.GREEN;
                break;
            }

            case 6: {
                color = Color.RED;
                break;
            }

        }

        return color;
    }
}
