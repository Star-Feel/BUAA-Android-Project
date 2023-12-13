package com.example.buaaexercise;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.example.buaaexercise.homepagefragments.MyFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Tools {
    public static final int MAX_SIGN_LENGTH = 20;
    private static final String FRAGMENT_PARAM1 = "param1";
    public static final String SRC_ACTIVITY = "src";
    public static final String ACCOUNT_SAFE_ACTIVITY = "AccountSafeActivity";
    public static final String MY_FRAGMENT = "myFragment";
    public static final HashMap<String, String> sportNameMapChinese = new HashMap<>();
    public static final HashMap<String, Integer> sportNameMapLogoId = new HashMap<>();
    public static final HashMap<String, String> sportEnglishNameMapType = new HashMap<>();
    public static final HashMap<String, String> sportChineseNameMapEnglish = new HashMap<>();


    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    static {
        sportNameMapChinese.put("run", "跑步");
        sportNameMapChinese.put("walk", "徒步");
        sportNameMapChinese.put("fit", "健身");
        sportNameMapChinese.put("td", "Td");
        sportNameMapChinese.put("swim", "游泳");
        sportNameMapChinese.put("basketball", "篮球");
        sportNameMapChinese.put("tennis", "网球");
        sportNameMapChinese.put("tabletennis", "乒乓球");
        sportNameMapChinese.put("billiard", "台球");
        sportNameMapChinese.put("badminton", "羽毛球");
        sportNameMapChinese.put("volleyball", "排球");
        sportNameMapChinese.put("soccer", "足球");
        sportNameMapChinese.put("frisbee", "飞盘");

        for (String key : sportNameMapChinese.keySet()) {
            sportChineseNameMapEnglish.put(sportNameMapChinese.get(key), key);
        }

        sportNameMapLogoId.put("run", R.drawable.ic_run);
        sportNameMapLogoId.put("walk", R.drawable.ic_walk);
        sportNameMapLogoId.put("fit", R.drawable.ic_fit);
        sportNameMapLogoId.put("td", R.drawable.ic_td);
        sportNameMapLogoId.put("swim", R.drawable.ic_swim);
        sportNameMapLogoId.put("basketball", R.drawable.ic_basketball);
        sportNameMapLogoId.put("tennis", R.drawable.ic_tennis);
        sportNameMapLogoId.put("tabletennis", R.drawable.ic_tabletennis);
        sportNameMapLogoId.put("billiard", R.drawable.ic_billiard);
        sportNameMapLogoId.put("badminton", R.drawable.ic_badminton);
        sportNameMapLogoId.put("volleyball", R.drawable.ic_volleyball);
        sportNameMapLogoId.put("soccer", R.drawable.ic_soccer);
        sportNameMapLogoId.put("frisbee", R.drawable.ic_frisbee);
        
        sportEnglishNameMapType.put("run", "室外");
        sportEnglishNameMapType.put("walk", "室外");
        sportEnglishNameMapType.put("fit", "室内");
        sportEnglishNameMapType.put("td", "室外");
        sportEnglishNameMapType.put("swim", "室内");
        sportEnglishNameMapType.put("basketball", "室外");
        sportEnglishNameMapType.put("tennis", "室外");
        sportEnglishNameMapType.put("tabletennis", "室外");
        sportEnglishNameMapType.put("billiard", "室内");
        sportEnglishNameMapType.put("badminton", "室内");
        sportEnglishNameMapType.put("volleyball", "室外");
        sportEnglishNameMapType.put("soccer", "室外");
        sportEnglishNameMapType.put("frisbee", "飞盘");
    }

    public static void toastMessageShort(Context context, String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }

    public static MyFragment newMyFragmentInstance(String param1) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int diameter = Math.min(width, height);
        Bitmap output = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius = diameter / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        return output;
    }

    // 摆了, 先不管优雅了, 这个函数就单纯用来展示性别选择
    public static void showTwoRadioSelectDialog(String title, String radioText1, String radioText2, Context context, String buttonText, final TextView textView) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_dialog_select_radio, null);
        Button button = v.findViewById(R.id.button);
        TextView titleTextView = v.findViewById(R.id.title);
        final RadioButton radioButton1 = v.findViewById(R.id.radio_1);
        RadioButton radioButton2 = v.findViewById(R.id.radio_2);
        // 创建builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // 将函数参数设置给布局
        titleTextView.setText(title);
        radioButton1.setText(radioText1);
        radioButton2.setText(radioText2);
        // FIXME-1 ok 获取用户的当前性别并且初始化显示对应的性别 String getGender(String username)
        String currentGender = DBFunction.getGender(MainActivity.getCurrentUsername());
        if ("男".equals(currentGender)) {
            radioButton1.setChecked(true);
        } else if ("女".equals(currentGender)) {
            radioButton2.setChecked(true);
        }
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME-1 ok 需要将性别设置为参数内容 void setUserGender(String username, String newGender)
                String newGender = "女";
                if (radioButton1.isChecked()) {
                    newGender = "男";
                }
                DBFunction.setUserGender(MainActivity.getCurrentUsername(), newGender);
                // 回显
                textView.setText(newGender);
                alertDialog.dismiss();
            }
        });
    }

    public static AlertDialog showOneButtonDialog(String title, String content, Context context, String buttonText) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_dialog, null);
        Button button = v.findViewById(R.id.button);
        TextView titleTextView = v.findViewById(R.id.title);
        TextView contentTextView = v.findViewById(R.id.content);
        // 创建 AlertDialog.Builder 实例
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);
        // 设置标题和内容
        // 创建并显示弹窗
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        titleTextView.setText(title);
        contentTextView.setText(content);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

//    public static String getSportLocType(String sportName) {
//        // 在下面枚举所有可能会被收藏的活动名字.
//        switch (sportName) {
//        }
//    }
}
