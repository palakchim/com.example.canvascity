package com.example.canvascity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canvascity.EventModel;
import com.example.canvascity.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    Context context;
    ArrayList<EventModel> eventList;

    // Constructor
    public EventAdapter(Context context, ArrayList<EventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventModel model = eventList.get(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtDate.setText(model.getDate());

        Glide.with(context)
                .load(model.getImageUrl())
               // .placeholder(R.drawable.placeholder) // optional
                .into(holder.imgEvent);

        // Click event
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra("title", model.getTitle());
            intent.putExtra("date", model.getDate());
            intent.putExtra("price", model.getPrice());
            intent.putExtra("location", model.getLocation());
            intent.putExtra("description", model.getDescription());
            intent.putExtra("image", model.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        ImageView imgEvent;
        TextView txtTitle, txtDate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEvent = itemView.findViewById(R.id.imgEvent);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}


