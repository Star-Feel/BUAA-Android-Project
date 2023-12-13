package com.example.buaaexercise.sports;


import static com.example.buaaexercise.backend.recommend.Recommend.recommendFitPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;
import com.example.buaaexercise.backend.recommend.Recommend;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FitSportActivity extends AppCompatActivity {

    private ImageButton mIBStart;
    private ImageButton mIBPause;
    private ImageButton mIBStop;
    private Timer timer;
    private LinearLayout mLLFitBg;
    private TextView mTvTitle;
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_sport);
        mIBStart = findViewById(R.id.btn_start);
        mIBPause = findViewById(R.id.btn_pause);
        mIBStop = findViewById(R.id.btn_stop);
        timer = new Timer(findViewById(R.id.timer));
        mLLFitBg = findViewById(R.id.ll_fit_bg);
        mTvTitle = findViewById(R.id.tv_title);
        mTvContent = findViewById(R.id.tv_content);

        Date date = new Date();
        CalendarDay calendarDay = new CalendarDay(date);

        Log.d("FitActivity", "getPlan--------------------------------------------------");
        ArrayList<String> plan = recommendFitPlan(MainActivity.getCurrentUsername()
                , calendarDay.getYear()
                , calendarDay.getMonth() + 1
                , calendarDay.getDay());
        mTvTitle.setText(plan.get(0) + "——" + plan.get(1));
        mTvContent.setText(plan.get(2));


        clickStart();
        clickPause();
        clickStop();
    }

    public void clickStart() {
        mIBStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();

                mIBStart.setVisibility(View.INVISIBLE);
                mIBPause.setVisibility(View.VISIBLE);
                mIBStop.setVisibility(View.VISIBLE);
            }
        });
    }

    public void clickPause() {
        mIBPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pause();

                mIBPause.setVisibility(View.GONE);
                mIBStart.setVisibility(View.VISIBLE);
            }
        });
    }

    public void clickStop() {
        mIBStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pause();
                Bundle bundle = new Bundle();
                bundle.putInt("sport_time", timer.getSeconds());
                bundle.putString("sport_name", "fit");
                timer.stop();
                Tools.toastMessageShort(FitSportActivity.this, "运动结束!");
                mIBStop.setVisibility(View.GONE);
                mIBStart.setVisibility(View.VISIBLE);
                mIBPause.setVisibility(View.GONE);
                Intent intent = new Intent(FitSportActivity.this, CompleteSportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}