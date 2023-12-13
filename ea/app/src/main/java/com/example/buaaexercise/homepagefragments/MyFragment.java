package com.example.buaaexercise.homepagefragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.buaaexercise.CalorieCheckActivity;
import com.example.buaaexercise.ChangePasswordActivity;
import com.example.buaaexercise.group.MyGroupActivity;
import com.example.buaaexercise.homepagefragments.MyFragment.HistoryShowItem;
import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.MyStarActivity;
import com.example.buaaexercise.PersonalInfoActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.SelectHeadImage;
import com.example.buaaexercise.SettingActivity;
import com.example.buaaexercise.Tools;
import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** 个人主页要有的内容
 * 用户名
 * 头像
 * 个人签名
 * 注册时间
 * 设置(退出登录, 修改密码)
 * 修改资料(性别, 生日, 身高, 体重, 个人签名)
 * 我的收藏
 */

public class MyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String tag = "我的";
    private View view;
    private Button detailInfoButton;
    private Button changePasswordButton;
    private ImageView settingButton;
    private ImageView starButton;
    private RecyclerView historiesView;
    private static ImageView headImage;
    private static TextView dateTextView;
    private RelativeLayout datePickLayout;
    private ArrayList<HistoryShowItem> historyShowItems;
    private HistoryAdapter historyAdapter;
    private RelativeLayout emptyHistoryShow;
    private ImageView calorieButton;
    private TextView registerTimeTextView;
    private static TextView personalSignTextView;
    private TextView usernameTextView;
    private ImageView goToMyGroup;

    public static void setPersonalSignTextView(String newSign) {
        personalSignTextView.setText(newSign);
    }

    public static ImageView getHeadImage() {
        return headImage;
    }

    public void initAttribute() {
        goToMyGroup = view.findViewById(R.id.my_group_button);
        calorieButton = view.findViewById(R.id.calorie_button);
        registerTimeTextView = view.findViewById(R.id.registerTime);
        // 获取注册时间
        registerTimeTextView.setText("注册于: " + DBFunction.getUserRegisterTime(MainActivity.getCurrentUsername()));
        personalSignTextView = view.findViewById(R.id.personalitySign);
        String personalSign = DBFunction.getPersonalSign(MainActivity.getCurrentUsername());
        if (personalSign == null) {
            personalSignTextView.setText("快设置属于你的个性签名吧～");
        } else {
            personalSignTextView.setText(personalSign);
        }
        usernameTextView = view.findViewById(R.id.username);
        usernameTextView.setText(MainActivity.getCurrentUsername());
        detailInfoButton = view.findViewById(R.id.detailInfoButton);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        settingButton = view.findViewById(R.id.settingButton);
        headImage = view.findViewById(R.id.head_image);
        // 头像是需要具体化的
        // FIXME-1 ok int getUserHeadImage(String imageId)
        int imageId = DBFunction.getUserHeadImage(MainActivity.getCurrentUsername());
        // headImage.setImageResource(imageId);
        dateTextView = view.findViewById(R.id.date_text_view);
        emptyHistoryShow = view.findViewById(R.id.empty_show);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imageId);
        Bitmap circularBitmap = Tools.getCircularBitmap(originalBitmap);
        headImage.setImageBitmap(circularBitmap);
        starButton = view.findViewById(R.id.star_button);

        historiesView = view.findViewById(R.id.histories_view);
        historiesView.setLayoutManager(new LinearLayoutManager(getActivity()));

        datePickLayout = view.findViewById(R.id.date_picker_layout);

        // 获取历史记录 FIXME-1 ok 获取到的历史记录的logo传入的是-1这里要修改, 以及返回的这里是英文的运动名字，需要进行映射
        initHistoryShow();
        // historyShowItems.add(new HistoryShowItem("跑步", R.drawable.buaa_logn, "12:30", "28min", "破纪录了！"));
        // historyShowItems.add(new HistoryShowItem("羽毛球", R.drawable.buaa_logn, "13:40", "39min", "感觉状态有点差.."));
        // historyShowItems.add(new HistoryShowItem("羽毛球", R.drawable.buaa_logn, "15:46", "26min", "练习了杀球"));
        // historyShowItems.add(new HistoryShowItem("羽毛球", R.drawable.buaa_logn, "16:23", "10min", "期末考核练习"));
        // setEmptyOrNot();
        // 设置适配器
        // historyAdapter = new HistoryAdapter(historyShowItems);
        // historiesView.setAdapter(historyAdapter);
    }

    public void initHistoryShow() {
        String currentTime = Tools.getCurrentTime(); // 默认时间为当前时间
        updateHistoryUi(currentTime);
    }

    public void updateCurrentShowTimeHistory() {
        String currentTime = dateTextView.getText().toString();
        updateHistoryUi(currentTime);
    }

    public void updateHistoryUi(String date) {
        // 根据日期date更新 dateTextView 和 历史记录容器
        dateTextView.setText(date); // 默认时间为当前时间
        List<MyFragment.HistoryShowItem> items = DBFunction.getUserHistory(MainActivity.getCurrentUsername(), date);
        historyShowItems = new ArrayList<>(items);
        // 更新里面的imageId
        for (HistoryShowItem item : historyShowItems) {
            item.setItemImageId(Tools.sportNameMapLogoId.get(item.getItemTitle()));
            if (item.getItemDesc().equals("Default")) {
                item.setItemDesc("暂无备注");
            }
            item.setItemTitle(Tools.sportNameMapChinese.get(item.getItemTitle()));
            item.setItemTotalTime(translate(item.getItemTotalTime()));
        }
        setEmptyOrNot();
    }

    public String translate(String numberS) {
        // 将 s 的数值转换为 min s
        int s = Integer.parseInt(numberS);
        int min = s / 60;
        int modS = s - min * 60;
        return min + "min " + modS + "s";
    }

    public void setEmptyOrNot() { // 数组更新后调用这个函数
        if (historyShowItems.size() == 0) {
            historiesView.setVisibility(View.INVISIBLE);
            emptyHistoryShow.setVisibility(View.VISIBLE);
        } else {
            historyAdapter = new HistoryAdapter(historyShowItems);
            historiesView.setAdapter(historyAdapter);
            emptyHistoryShow.setVisibility(View.INVISIBLE);
            historiesView.setVisibility(View.VISIBLE);
        }
    }

    public MyFragment() {
        // Required empty public constructor
    }

    public void setOnClickListeners() {
        goToMyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyGroupActivity.class));
            }
        });

        calorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进行卡路里检测
                startActivity(new Intent(getActivity(), CalorieCheckActivity.class));
            }
        });

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到设置界面
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到收藏界面
                Intent intent = new Intent(getActivity(), MyStarActivity.class);
                startActivity(intent);
            }
        });

        detailInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到详细资料界面
                Intent intent = new Intent(getActivity(), PersonalInfoActivity.class);
                intent.putExtra("src", "myFragment");
                startActivity(intent);
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到修改密码界面
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra(Tools.SRC_ACTIVITY, Tools.MY_FRAGMENT);
                startActivity(intent);
            }
        });

        datePickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 在碎片中想用context的话就直接调用getActivity就ok了
                showDatePicker();
            }
        });

        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置头像
                startActivity(new Intent(getActivity(), SelectHeadImage.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // 本方法在onCreateView之后调用
        super.onViewCreated(view, savedInstanceState);
        initAttribute();
        setOnClickListeners();
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            // 当用户点击某个日期时, 会调这个函数, 然后后三个参数自动传进来用户点的日期
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // 好像日期选择器的月份是从0开始算的, 所以这里要加1来让他符合习惯
                String newDate = String.format("%04d-%02d-%02d", i, i1 + 1, i2);
                dateTextView.setText(newDate);
                // 要更新适配器
                // FIXME-1 ok getUserHistory(String username, String date)
                // FIXME 在别的碎片改动了历史记录
                // historyShowItems = new ArrayList<>(DBFunction.getUserHistory(MainActivity.getCurrentUsername(), newDate));
                updateHistoryUi(newDate);
                // setEmptyOrNot();
            }
        }, year, month, day); // 这里的三个年月日是在显示时日历的初始年月日
        datePickerDialog.show();
    }

    public static class HistoryShowItem {
        // 展示出的历史记录 项
        private String itemTitle;
        private int itemImageId;
        private String itemTime; // 运动的时间
        private String itemTotalTime; // 运动的总时间
        private String itemDesc; // 运动备注

        public HistoryShowItem(String title, int id, String time, String totalTime, String desc) {
            this.itemTitle = title;
            this.itemImageId = id;
            this.itemTime= time;
            this.itemTotalTime = totalTime;
            this.itemDesc = desc;
        }

        public void setItemTotalTime(String itemTotalTime) {
            this.itemTotalTime = itemTotalTime;
        }

        public void setItemDesc(String itemDesc) {
            this.itemDesc = itemDesc;
        }

        public void setItemTitle(String itemTitle) {
            this.itemTitle = itemTitle;
        }

        public int getItemImageId() {
            return itemImageId;
        }

        public String getItemTitle() {
            return itemTitle;
        }

        public String getItemTime() {
            return itemTime;
        }

        public String getItemDesc() {
            return itemDesc;
        }

        public String getItemTotalTime() {
            return itemTotalTime;
        }

        public void setItemImageId(int itemImageId) {
            this.itemImageId = itemImageId;
        }
    }

    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        private ArrayList<HistoryShowItem> showItems;
        // 滑到某个子项位置 -> 加载子项布局 -> new子项的holder(其中的具体属性值从数据结构中加载) -> 用holder给子项布局的属性赋具体值
        // 这个holder就像是针对于每一个项的管理器
        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView itemImage;
            TextView itemTitle;
            TextView itemTime;
            TextView itemDesc;
            TextView itemTotalTime;

            // 这个构造器的参数view往往就是RecyclerView子项
            public ViewHolder(View view) {
                super(view);
                itemImage = view.findViewById(R.id.history_item_image);
                itemTitle = view.findViewById(R.id.history_item_title);
                itemTime = view.findViewById(R.id.history_item_time);
                itemDesc = view.findViewById(R.id.history_item_desc);
                itemTotalTime = view.findViewById(R.id.history_item_total_time);
            }

            public ImageView getItemImage() {
                return itemImage;
            }

            public TextView getItemTitle() {
                return itemTitle;
            }

            public TextView getItemTime() {
                return itemTime;
            }

            public TextView getItemDesc() {
                return itemDesc;
            }

            public TextView getItemTotalTime() {
                return itemTotalTime;
            }
        }

        public HistoryAdapter(ArrayList<HistoryShowItem> items) { // 将要展示的数据源传进来
            this.showItems = items;
        }

        // 此方法用于创建ViewHolder实例, 并将加载进来的布局传入构造函数中, 然后将ViewHolder的实例返回
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 这里把我们自定义的每个子项的布局加载进来了
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.history_item, parent, false);
            // viewHolder的构造器中对这个子项的title和image进行赋值
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        // 用于对RecyclerView子项数据进行赋值, 在每个子项被滚动到屏幕内的时候执行
        // 通过position得到当前项的实例, 再将数据设置进去
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HistoryShowItem item = showItems.get(position);
            holder.getItemImage().setImageResource(item.getItemImageId());
            holder.getItemTitle().setText(item.getItemTitle());
            holder.getItemTime().setText(item.getItemTime());
            holder.getItemDesc().setText(item.getItemDesc());
            holder.getItemTotalTime().setText(item.getItemTotalTime());
        }

        @Override
        public int getItemCount() {
            return showItems.size();
        }
    }

}




