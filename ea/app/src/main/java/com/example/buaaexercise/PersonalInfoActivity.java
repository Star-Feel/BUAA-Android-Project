package com.example.buaaexercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PersonalInfoActivity extends AppCompatActivity {
    private final String HEIGHT_UNIT = "cm";
    private final String WEIGHT_UNIT = "kg";
    private String parentActivity;
    private ImageView returnButton;
    private RelativeLayout personalSignButton;
    private RelativeLayout genderSelectButton;
    private RelativeLayout birthdaySetButton;
    private TextView birthdayText;
    private RelativeLayout heightPickButton;
    private RelativeLayout weightPickButton;
    private TextView heightTextView;
    private TextView weightTextView;
    private TextView genderTextView;
    private TextView registerTimeTextView;
    private TextView usernameTextView;
    private int currentHeight;
    private int currentWeight;

    // 个人主页
    public void initAttribute() {
        usernameTextView = findViewById(R.id.username_text_view);
        usernameTextView.setText(MainActivity.getCurrentUsername());
        registerTimeTextView = findViewById(R.id.register_time_text);
        // FIXME-1 ok 拿到注册时间
        // String registerTime = "2023-12-04";
        String registerTime = DBFunction.getUserRegisterTime(MainActivity.getCurrentUsername());
        registerTimeTextView.setText(registerTime);
        returnButton = findViewById(R.id.return_button);
        parentActivity = getIntent().getStringExtra(Tools.SRC_ACTIVITY);
        personalSignButton = findViewById(R.id.card_personal_sign);
        genderSelectButton = findViewById(R.id.card_gender);
        birthdaySetButton = findViewById(R.id.card_birthday);
        birthdayText = findViewById(R.id.birthday_text);
        // FIXME-1 ok 拿到用户的出生日期, 给初始化 String getUserBirthday(String username), 以"yyyy-mm-dd"的格式返回
        String getBirthday = DBFunction.getUserBirthday(MainActivity.getCurrentUsername());
        if (getBirthday == null || getBirthday.equals("null")) {
            birthdayText.setText("未设置");
        } else {
            birthdayText.setText(getBirthday);
        }
        heightPickButton = findViewById(R.id.card_height);
        weightPickButton = findViewById(R.id.card_weight);
        heightTextView = findViewById(R.id.height_text);
        weightTextView = findViewById(R.id.weight_text);
        genderTextView = findViewById(R.id.gender_text);
        // 拿到这些的初始值进行设置
        int initWeight = DBFunction.getUserHeight(MainActivity.getCurrentUsername());
        int initHeight = DBFunction.getUserHeight(MainActivity.getCurrentUsername());
        String initGender = DBFunction.getGender(MainActivity.getCurrentUsername());
        if (initWeight == -1) {
            weightTextView.setText("未设置");
        } else {
            weightTextView.setText(String.valueOf(initWeight));
        }
        if (initHeight == -1) {
            heightTextView.setText("未设置");
        } else {
            heightTextView.setText(String.valueOf(initHeight));
        }
        if (initGender == null || initGender.equals("null")) {
            genderTextView.setText("未设置");
        } else {
            genderTextView.setText(initGender);
        }
    }

    public void setOnClickListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        personalSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalInfoActivity.this, PersonalSignActivity.class));
            }
        });

        genderSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出选择性别的弹窗
                Tools.showTwoRadioSelectDialog("设置性别", "男", "女", PersonalInfoActivity.this, "确定", genderTextView);
            }
        });

        birthdaySetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 弹出日期选择框
                showDatePicker();
            }
        });

        heightPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> args = new HashMap<>();
                args.put("unit", "cm");
                args.put("min", "100");
                args.put("max", "240");
                args.put("initPos", "60");
                args.put("buttonText", "保存");
                args.put("type", "height");
                showNumberPickerDialog(args);
            }
        });

        weightPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> args = new HashMap<>();
                args.put("unit", "kg");
                args.put("min", "30");
                args.put("max", "120");
                // 这里的initPos应该根据用户当前的身高确定
                args.put("initPos", "30");
                args.put("buttonText", "保存");
                args.put("type", "weight");
                showNumberPickerDialog(args);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initAttribute();
        setOnClickListeners();
    }

    public void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            // 当用户点击某个日期时, 会调这个函数, 然后后三个参数自动传进来用户点的日期
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                // 好像日期选择器的月份是从0开始算的, 所以这里要加1来让他符合习惯
                String newDate = String.format("%04d-%02d-%02d", i, i1 + 1, i2);
                birthdayText.setText(newDate);
                // FIXME-1 ok 将用户的出生日期修改为目标日期 void setUserBirthday(String username, String date)
                DBFunction.setUserBirthday(MainActivity.getCurrentUsername(), newDate);
            }
        }, year, month, day); // 这里的三个年月日是在显示时日历的初始年月日
        datePickerDialog.show();
    }


    public void showNumberPickerDialog(final HashMap<String, String> args) {
        LayoutInflater inflater = LayoutInflater.from(PersonalInfoActivity.this);
        View v = inflater.inflate(R.layout.my_dialog_number_picker, null);
        Button button = v.findViewById(R.id.button);
        TextView unit = v.findViewById(R.id.unit);
        final WheelPicker wheelPicker = v.findViewById(R.id.number_picker);
        // 创建 AlertDialog.Builder 实例
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);
        builder.setView(v);
        // 设置标题和内容
        // 创建并显示弹窗
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        unit.setText(args.get("unit"));
        int minNum = Integer.parseInt(args.get("min"));
        int maxNum = Integer.parseInt(args.get("max"));
        final ArrayList<Integer> showNumbers = new ArrayList<>();
        // 构造显示的数字的数组
        for (int i = minNum; i <= maxNum; ++i) {
            showNumbers.add(i);
        }
        wheelPicker.setData(showNumbers);
        wheelPicker.setSelectedItemPosition(Integer.parseInt(args.get("initPos")));
        button.setText(args.get("buttonText"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 读取轮子上的显示的数字
                int curNumber = showNumbers.get(wheelPicker.getCurrentItemPosition());
                if (args.get("type").equals("height")) {
                    pickHeightAction(curNumber);
                } else if (args.get("type").equals("weight")) {
                    pickWeightAction(curNumber);
                }
                alertDialog.dismiss();
            }
        });
    }

    void pickHeightAction(int curNumber) {
        // 设置身高 FIXME-1 ok setHeight(String username, int newHeight)
        // Tools.toastMessageShort(PersonalInfoActivity.this, "这里需要设置身高为: " + curNumber);
        heightTextView.setText(curNumber + "cm");
        DBFunction.setUserHeight(MainActivity.getCurrentUsername(), curNumber);
    }

    void pickWeightAction(int curNumber) {
        // 设置体重 FIXME-1 ok setWeight(String username, int newWeight)
        // Tools.toastMessageShort(PersonalInfoActivity.this, "这里需要设置体重为: " + curNumber);
        weightTextView.setText(curNumber + "kg");
        DBFunction.setUserWeight(MainActivity.getCurrentUsername(), curNumber);
    }
}
