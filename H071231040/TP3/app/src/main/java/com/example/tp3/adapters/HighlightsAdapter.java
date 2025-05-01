package com.example.tp3.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3.R;
import com.example.tp3.models.StoryHighlight;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.VH> {
    public interface Listener { 
        void onHighlightClick(StoryHighlight s); 
    }

    private final List<StoryHighlight> items;
    private final Listener listener;

    public HighlightsAdapter(List<StoryHighlight> items, Listener listener) {
        this.items = items; 
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(
            LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_highlight, p, false)
        );
    }

    @Override 
    public void onBindViewHolder(@NonNull VH h, int i) {
        StoryHighlight s = items.get(i);
        h.title.setText(s.getTitle());

        Picasso.get()
                .load(s.getImageUrl())
                .into(h.image);
//        h.image.setImageResource(s.getImageUrl());

        // Set click listener on the whole item view
        h.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHighlightClick(s);
            }
        });
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        VH(View v) {
            super(v);
            image = v.findViewById(R.id.highlight_image);
            title = v.findViewById(R.id.highlight_title);
        }
    }
}
