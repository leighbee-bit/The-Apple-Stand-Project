package com.example.javamachinery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppleListAdapter extends RecyclerView.Adapter<AppleListAdapter.AppleViewHolder> {

    private final List<Apple> appleList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Apple apple);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AppleListAdapter(List<Apple> appleList) {
        this.appleList = appleList;
    }

    public static class AppleViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView color;
        TextView weight;
        ImageView thumb;



        public AppleViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.appleTypeTv);
            color = itemView.findViewById(R.id.appleColorTv);
            weight = itemView.findViewById(R.id.appleWeightTv);
            thumb = itemView.findViewById(R.id.appleImage);
        }
    }

    @NonNull
    @Override
    public AppleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apple_card_fragment, parent, false);
        return new AppleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppleViewHolder holder, int position) {
        Apple apple = appleList.get(position);
        String aColor = apple.getColor().toLowerCase().strip();
        switch (aColor) {
            case "yellow":
            case "gold":
            case "orange":
                holder.thumb.setImageResource(R.drawable.yellow_apple_transparent);
                break;
            case "green":
                holder.thumb.setImageResource(R.drawable.green_apple_transparent);
                break;
            case "blue":
            case "purple":
                holder.thumb.setImageResource(R.drawable.blue_apple_transparent);
                break;
            default:
                holder.thumb.setImageResource(R.drawable.red_apple_transparent);
                break;
        }
        holder.type.setText(apple.getType());
        holder.color.setText("Color: " + apple.getColor().substring(0,1).toUpperCase() + apple.getColor().substring(1));
        holder.weight.setText("Weight: " + apple.getWeight() + " grams");

        holder.itemView.setOnClickListener(view -> {
            int adapterPosition = holder.getBindingAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && listener != null) {
                listener.onItemClick(appleList.get(adapterPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appleList.size();
    }

    public void updateApples(List<Apple> newApples) {
        appleList.clear();
        appleList.addAll(newApples);
        notifyDataSetChanged();
    }
}
