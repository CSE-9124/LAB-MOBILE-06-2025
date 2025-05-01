package com.example.tp3.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tp3.R;
import com.example.tp3.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePostsAdapter extends RecyclerView.Adapter<ProfilePostsAdapter.VH> {
    public interface Listener {
        void onPostClick(Post post);
    }
    private final List<Post> items;
    private final Listener listener;

    public ProfilePostsAdapter(List<Post> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    @NonNull
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Post p = items.get(i);
//        Picasso.get()
//                .load(p.getPostImageUrl())
//                .placeholder(R.drawable.placeholder_image)
//                .into(h.postImage);

        if (p.getPostImageUrl() instanceof Integer) {
            Picasso.get()
                    .load((Integer) p.getPostImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(h.postImage);
        } else if (p.getPostImageUrl() instanceof Uri) {
            Picasso.get()
                    .load((Uri) p.getPostImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(h.postImage);
        }

        h.itemView.setOnClickListener(v ->
                listener.onPostClick(p)
        );
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView postImage;

        VH(View v) {
            super(v);
            postImage = v.findViewById(R.id.post_image);
        }
    }
}
