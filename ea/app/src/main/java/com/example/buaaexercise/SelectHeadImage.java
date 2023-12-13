package com.example.buaaexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.homepagefragments.MyFragment;

import java.util.ArrayList;

public class SelectHeadImage extends AppCompatActivity {
    private ImageView returnButton;
    private RecyclerView recyclerView;
    private Button saveButton;
    private ArrayList<HeadImageItem> items;

    public void initAttribute() {
        returnButton = findViewById(R.id.return_button);
//        saveButton = findViewById(R.id.save_button);
        recyclerView = findViewById(R.id.select_head_image_recycler_view);
        // 设置和瀑布流有关的布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        initAddItems();
        HeadImageAdapter adapter = new HeadImageAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    public void initAddItems() {
        // 初始化加items
        items = new ArrayList<>();
        items.add(new HeadImageItem(R.drawable._1));
        items.add(new HeadImageItem(R.drawable._2));
        items.add(new HeadImageItem(R.drawable._3));
        items.add(new HeadImageItem(R.drawable._4));
        items.add(new HeadImageItem(R.drawable._5));
        items.add(new HeadImageItem(R.drawable._6));
        items.add(new HeadImageItem(R.drawable._7));
        items.add(new HeadImageItem(R.drawable._8));
        items.add(new HeadImageItem(R.drawable._9));
        items.add(new HeadImageItem(R.drawable._10));
        items.add(new HeadImageItem(R.drawable._11));
        items.add(new HeadImageItem(R.drawable._12));
        items.add(new HeadImageItem(R.drawable._13));
        items.add(new HeadImageItem(R.drawable._14));
        items.add(new HeadImageItem(R.drawable._15));
        items.add(new HeadImageItem(R.drawable._16));
        items.add(new HeadImageItem(R.drawable._17));
        items.add(new HeadImageItem(R.drawable._18));
        items.add(new HeadImageItem(R.drawable._19));
        items.add(new HeadImageItem(R.drawable._20));
        items.add(new HeadImageItem(R.drawable._21));
        items.add(new HeadImageItem(R.drawable._22));
        items.add(new HeadImageItem(R.drawable._23));
        items.add(new HeadImageItem(R.drawable._25));
        items.add(new HeadImageItem(R.drawable._26));
        items.add(new HeadImageItem(R.drawable._27));
        items.add(new HeadImageItem(R.drawable._28));
        items.add(new HeadImageItem(R.drawable._29));
        items.add(new HeadImageItem(R.drawable._30));
        items.add(new HeadImageItem(R.drawable._31));
        items.add(new HeadImageItem(R.drawable._32));
        items.add(new HeadImageItem(R.drawable._33));
        items.add(new HeadImageItem(R.drawable._34));
        items.add(new HeadImageItem(R.drawable._35));
        items.add(new HeadImageItem(R.drawable._36));
        items.add(new HeadImageItem(R.drawable._37));
        items.add(new HeadImageItem(R.drawable._38));
        items.add(new HeadImageItem(R.drawable._39));
        items.add(new HeadImageItem(R.drawable._40));
        items.add(new HeadImageItem(R.drawable._41));
        items.add(new HeadImageItem(R.drawable._42));
        items.add(new HeadImageItem(R.drawable._43));
        items.add(new HeadImageItem(R.drawable._44));
    }

    public void setClickListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_head_image);
        initAttribute();
        setClickListeners();
    }
}

class HeadImageAdapter extends RecyclerView.Adapter<HeadImageAdapter.ViewHolder> {
    private ArrayList<HeadImageItem> headImageItems;
    private SelectHeadImage parent;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemHeadImage;
        private ImageView selectImage;

        public ViewHolder(View view) {
            super(view);
            itemHeadImage = view.findViewById(R.id.head_image_item);
        }

        public ImageView getItemHeadImage() {
            return itemHeadImage;
        }

        public ImageView getSelectImage() {
            return selectImage;
        }
    }

    public HeadImageAdapter(ArrayList<HeadImageItem> items, SelectHeadImage selectHeadImage) {
        this.headImageItems = items;
        this.parent = selectHeadImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.head_image_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HeadImageItem headImageItem = headImageItems.get(position);
        holder.getItemHeadImage().setImageResource(headImageItem.getItemImageId());
        holder.getItemHeadImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME-1 将此用户的头像更新为点击到的 void setHeadImage(String username, int imageId)
                DBFunction.setHeadImage(MainActivity.getCurrentUsername(), headImageItem.getItemImageId());
                Bitmap originalBitmap = BitmapFactory.decodeResource(parent.getResources(), headImageItem.getItemImageId());
                Bitmap circularBitmap = Tools.getCircularBitmap(originalBitmap);
                MyFragment.getHeadImage().setImageBitmap(circularBitmap);
                parent.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return headImageItems.size();
    }

    public ArrayList<HeadImageItem> getHeadImageItems() {
        return headImageItems;
    }
}

class HeadImageItem {
    private int itemImageId;
    private boolean isSelected;

    public HeadImageItem(int imageId) {
        this.itemImageId = imageId;
        this.isSelected = false;
    }

    public int getItemImageId() {
        return itemImageId;
    }
}
