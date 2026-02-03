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

import com.example.canvascity.Activity.EventDetailsActivity;
import com.example.canvascity.R;
import com.example.canvascity.model.EventModel;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    ArrayList<EventModel> eventList;
    Context context;

    public EventAdapter(Context context, ArrayList<EventModel> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventModel model = eventList.get(position);

        // ✅ TEXT DATA
        holder.title.setText(model.getTitle());
        holder.locationDate.setText(model.getLocationDate());
        holder.price.setText(model.getPrice());

        // ✅ IMAGE FIX (MOST IMPORTANT)
        if (model.getImage() != 0) {
            holder.eventImage.setImageResource(model.getImage());
        } else {
            holder.eventImage.setImageResource(R.drawable.fashionimage);
            // ek placeholder image add kar lena
        }

        // ✅ CLICK SAFE (NO CRASH)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra("title", model.getTitle());
            intent.putExtra("locationDate", model.getLocationDate());
            intent.putExtra("price", model.getPrice());
            intent.putExtra("description", model.getDescription());
            intent.putExtra("image", model.getImage());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView title, locationDate, price;
        ImageView eventImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvEventTitle);
            locationDate = itemView.findViewById(R.id.tvEventLocationDate);
            price = itemView.findViewById(R.id.tvEventPrice);

            // ✅ CORRECT ID (from item_event.xml)
            eventImage = itemView.findViewById(R.id.imgEventThumb);
        }
    }
}
