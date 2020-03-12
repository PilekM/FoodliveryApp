package com.example.foodliveryapp.recycler.workers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodliveryapp.R;

import java.util.List;

public class WorkersListAdapter extends RecyclerView.Adapter<WorkersListAdapter.ViewHolder> {

    private List<Worker> workerList;
    private Context context;

    public WorkersListAdapter(List<Worker> workerList, Context context){
        this.workerList = workerList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_list_item, parent, false);

        return new WorkersListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkersListAdapter.ViewHolder holder, int position) {
        final Worker worker = workerList.get(position);

        String fullName = worker.getName() + " " + worker.getSurname();
        holder.workerNameTextView.setText(fullName);
        holder.callButton.setOnClickListener(v -> {
            Intent diallIntent = new Intent(Intent.ACTION_DIAL);
            diallIntent.setData(Uri.parse("tel:" + worker.getPhoneNumber()));
            context.startActivity(diallIntent);
        });

    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView workerNameTextView;
        ImageView callButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            workerNameTextView = itemView.findViewById(R.id.work_li_work_name);
            callButton = itemView.findViewById(R.id.work_li_ib);
        }
    }

}
