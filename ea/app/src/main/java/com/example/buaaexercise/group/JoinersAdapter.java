package com.example.buaaexercise.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buaaexercise.R;

import java.util.List;

public class JoinersAdapter extends RecyclerView.Adapter<JoinersAdapter.ViewHolder> {
    private List<JoinerItem> joiners;

    public JoinersAdapter(List<JoinerItem> joiners) {
        this.joiners = joiners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_joiner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JoinerItem joinerItem = joiners.get(position);
        holder.joinerTextView.setText(joinerItem.getUsername());
        holder.avatarImageView.setImageBitmap(joinerItem.getAvatarResId());
    }

    @Override
    public int getItemCount() {
        return joiners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView joinerTextView;
        ImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            joinerTextView = itemView.findViewById(R.id.joinerTextView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }

    public void updateData(List<JoinerItem> newJoinerItems) {
        this.joiners.clear();
        this.joiners.addAll(newJoinerItems);
        notifyDataSetChanged();
    }
}
