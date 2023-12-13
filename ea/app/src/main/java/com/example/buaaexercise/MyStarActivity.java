package com.example.buaaexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buaaexercise.MyStarActivity.StarShowItem;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.sports.SportkindActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyStarActivity extends AppCompatActivity {
    private ImageView returnButton;
    // 先find这个starsView, 再为之设置布局管理器, 再set适配器
    private RecyclerView starsView;
    // 先new一个适配器, 往里面加入要展示的列表
    private StarAdapter starsViewAdapter;
    private ArrayList<StarShowItem> starItems;
    private RelativeLayout emptyShow;

    private void initAttribute() {
        returnButton = findViewById(R.id.return_button);
        starItems = new ArrayList<>();
        emptyShow = findViewById(R.id.empty_show);
        starsView = findViewById(R.id.stars_view);
        starsView.setLayoutManager(new LinearLayoutManager(MyStarActivity.this));
        // FIXME-1 ok 对接, 需要从数据库中爬下来一个username的收藏列表
        starItems = new ArrayList<>(DBFunction.getUserStars(MainActivity.getCurrentUsername()));
        // (String title, int count, int id, String type)
//        starItems.add(new StarShowItem("跑步", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("羽毛球", 18, R.drawable.buaa_logn, "室内"));
//        starItems.add(new StarShowItem("篮球", 18, R.drawable.buaa_logn, "室内"));
//        starItems.add(new StarShowItem("台球", 18, R.drawable.buaa_logn, "室内"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
//        starItems.add(new StarShowItem("td", 18, R.drawable.buaa_logn, "室外"));
        setEmptyOrNot();
        // starsViewAdapter = new StarAdapter(starItems);
        // starsView.setAdapter(starsViewAdapter);
    }

    private void setOnClickListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_star);
        initAttribute();
        setOnClickListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("退出了收藏界面！");
        // 这里要保存收藏的设置
        ArrayList<StarShowItem> afterItems = starsViewAdapter.getShowItems();
        // 找到其中取消收藏的项目(以运动名为唯一标志)
        String cancelNames = "";
        for (StarShowItem item : afterItems) {
            if (!item.isRed()) {
                // FIXME-1 ok void cancelStar(String username, String sportName)
                DBFunction.cancelStar(MainActivity.getCurrentUsername(), Tools.sportChineseNameMapEnglish.get(item.getItemTitle()));
                // cancelNames += item.getItemTitle() + " ";
            }
        }
        // Tools.toastMessageShort(MyStarActivity.this, "取消收藏: " + cancelNames);
    }

    public void setEmptyOrNot() { // before调用这个函数, need to
        starsViewAdapter = new StarAdapter(starItems);
        starsView.setAdapter(starsViewAdapter);
        if (starItems.size() == 0) { // 没有项
            starsView.setVisibility(View.INVISIBLE);
            emptyShow.setVisibility(View.VISIBLE);
        } else {
            for (StarShowItem item : starItems) {
                item.setItemImageId(Tools.sportNameMapLogoId.get(item.getItemTitle()));
                item.setItemType(Tools.sportEnglishNameMapType.get(item.getItemTitle()));
                item.setItemTitle(Tools.sportNameMapChinese.get(item.getItemTitle()));
            }
            emptyShow.setVisibility(View.INVISIBLE);
            starsView.setVisibility(View.VISIBLE); // visible 可见的
        }
    }

    public static class StarShowItem {
        private String itemTitle;
        private int itemCount;
        private int itemImageId;
        private String itemType; // 室外 or 室内
        private boolean isRed;

        public StarShowItem(String title, int count, int id, String type) {
            this.itemTitle = title;
            this.itemCount = count;
            this.itemImageId = id;
            this.itemType = type;
            this.isRed = true;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public void setItemImageId(int itemImageId) {
            this.itemImageId = itemImageId;
        }

        public void setRed(boolean red) {
            isRed = red;
        }

        public boolean isRed() {
            return isRed;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public int getItemCount() {
            return itemCount;
        }

        public int getItemImageId() {
            return itemImageId;
        }

        public String getItemType() {
            return itemType;
        }
    }

    class StarAdapter extends RecyclerView.Adapter<StarAdapter.ViewHolder> {
        private ArrayList<StarShowItem> showItems;

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView itemImage;
            private TextView itemCount;
            private TextView itemTitle;
            private TextView itemType;
            private ImageView heartImage;
            private ImageView planeImage;

            public ViewHolder(View view) {
                super(view);
                itemImage = view.findViewById(R.id.star_item_image);
                itemCount = view.findViewById(R.id.desc_mid);
                itemTitle = view.findViewById(R.id.star_item_title);
                itemType = view.findViewById(R.id.star_item_type);
                heartImage = view.findViewById(R.id.star_item_heart);
                planeImage = view.findViewById(R.id.star_item_go);
            }

            public TextView getItemTitle() {
                return itemTitle;
            }

            public ImageView getItemImage() {
                return itemImage;
            }

            public TextView getItemCount() {
                return itemCount;
            }

            public TextView getItemType() {
                return itemType;
            }

            public ImageView getHeartImage() {
                return heartImage;
            }

            public ImageView getPlaneImage() {
                return planeImage;
            }
        }

        public StarAdapter(ArrayList<StarShowItem> items) {
            this.showItems = items;
        }

        // 这个函数创建一个ViewHolder并且对其中的属性进行初始化, 让ViewHolder找到布局里各个控件
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.star_item, parent, false);
            // 把布局给到view holder
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        // 这个用来设置某一项的显示, 让ViewHolder对找到位置的各个控件的内容进行设置
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            // 在这里会对holder和位置position进行绑定
            final StarShowItem item = showItems.get(position);
            holder.getItemImage().setImageResource(item.getItemImageId());
            // 在对TextView设置text的时候传进去的参数必须是String类型的
            holder.getItemCount().setText(String.valueOf(item.getItemCount()));
            holder.getItemTitle().setText(item.getItemTitle());
            holder.getItemType().setText(item.getItemType());

            holder.getHeartImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Tools.toastMessageShort(holder.itemView.getContext(), "点击了红心");
                    if (item.isRed()) {
                        holder.getHeartImage().setImageResource(R.drawable.empty_heart);
                    } else {
                        holder.getHeartImage().setImageResource(R.drawable.heart);
                    }
                    item.setRed(!item.isRed());
                }
            });

            holder.getPlaneImage().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 跳转到去运动的页面
                    // Tools.toastMessageShort(holder.itemView.getContext(), "应该跳转到去进行<" + item.getItemTitle() + ">运动的界面");
                    startActivity(new Intent(MyStarActivity.this, SportkindActivity.class));
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return showItems.size();
        }

        public ArrayList<StarShowItem> getShowItems() {
            return showItems;
        }
    }
}


