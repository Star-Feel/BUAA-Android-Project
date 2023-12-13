package com.example.buaaexercise.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buaaexercise.R;
import com.google.gson.Gson;

import java.util.List;

// Inside GroupFragment class
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private List<GroupItem> groupItemList;

    private int op = 0;

    public GroupAdapter(List<GroupItem> groupItemList, int op) {
        this.groupItemList = groupItemList;
        this.op = 0;
    }


    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupItem groupItem = groupItemList.get(position);

        // Set random color to colorView
        int color = generateLightColorWithAlpha(100);

        // Bind other data to views
        holder.itemView.setBackgroundColor(color);
        holder.sportTextView.setText(groupItem.getSport());
        holder.limitTextView.setText("加入人数: " + groupItem.getJoiners().size() + "/" + groupItem.getJoinerLimit());
        holder.timeTextView.setText("开始时间:\n" + groupItem.getPlanStartTime() + "\n结束时间:\n" + groupItem.getPlanEndTime());
        holder.createTimeTextView.setText("地点: " + groupItem.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start GroupDetailActivity and pass the groupItem
                Intent intent = new Intent(v.getContext(), GroupDetailActivity.class);
                if(op == 1) {
                    intent = new Intent(v.getContext(), GroupDetailForSearchActivity.class);
                } else if (op == 2) {
                    intent = new Intent(v.getContext(), GroupDetailForMyActivity.class);
                } else if (op == 3) {
                    intent = new Intent(v.getContext(), GroupDetailForRecActivity.class);
                }
                intent.putExtra("groupItem", new Gson().toJson(groupItem));
                ((Activity)v.getContext()).startActivityForResult(intent, 2);
            }
        });
    }



    @Override
    public int getItemCount() {
        return groupItemList.size();
    }

    // Inside GroupAdapter.ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        //View colorView;
        TextView sportTextView, limitTextView, timeTextView, createTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //colorView = itemView.findViewById(R.id.colorView);
            sportTextView = itemView.findViewById(R.id.sportTextView);
            limitTextView = itemView.findViewById(R.id.limitTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            createTimeTextView = itemView.findViewById(R.id.createTimeTextView);
        }
    }


    // Method to generate a random color
    public int generateLightColorWithAlpha(int alpha) {
        // 生成随机的亮度
        float[] hsv = new float[3];
        hsv[0] = (float) Math.random() * 360; // 色调
        hsv[1] = (float) (Math.random() * 0.2 + 0.8); // 饱和度，确保饱和度较高
        hsv[2] = (float) (Math.random() * 0.2 + 0.8); // 亮度，确保亮度较高

        // 将透明度添加到颜色中
        int color = Color.HSVToColor(hsv);
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public void updateAdapter(List<GroupItem> groupItems) {
        this.groupItemList.clear();
        this.groupItemList.addAll(groupItems);
        notifyDataSetChanged();
    }


}
