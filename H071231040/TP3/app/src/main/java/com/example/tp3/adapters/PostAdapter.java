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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.VH> {
    public interface Listener {
        void onProfileClick(String username);
        void onPostClick(Post post);
    }
    private final List<Post> items;
    private final Listener listener;

    public PostAdapter(List<Post> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int i) {
        Post p = items.get(i);
        h.username.setText(p.getUsername());
        h.username_caption.setText(p.getUsername());
        h.caption.setText(p.getCaption());

        // small circle
        Picasso.get().load(p.getProfileImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(h.profileImage);

        // big post
        // Load post image based on type
        if (p.getPostImageUrl() instanceof Integer) {
            Picasso.get().load((Integer) p.getPostImageUrl()).into(h.postImage);
        } else if (p.getPostImageUrl() instanceof Uri) {
            Picasso.get().load((Uri) p.getPostImageUrl()).into(h.postImage);
        }

        h.profileImage.setOnClickListener(v -> listener.onProfileClick(p.getUsername()));
        h.username.setOnClickListener(v -> listener.onProfileClick(p.getUsername()));
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView profileImage, postImage;
        TextView username, username_caption, caption;
        VH(View v) {
            super(v);
            profileImage = v.findViewById(R.id.profile_image);
            postImage    = v.findViewById(R.id.post_image);
            username     = v.findViewById(R.id.username);
            username_caption = v.findViewById(R.id.username_caption);
            caption      = v.findViewById(R.id.caption);
        }
    }
}