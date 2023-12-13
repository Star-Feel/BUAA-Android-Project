package com.example.buaaexercise.sports;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.buaaexercise.HomepageActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;

import java.util.HashMap;

public class BallSportActivity extends AppCompatActivity {

    private ImageButton mIBStart;
    private ImageButton mIBPause;
    private ImageButton mIBStop;
    private Timer timer;

    private HashMap<String, Integer> sportRMap = new HashMap<>();

    private ImageView mIvSport;

    private String sport_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_sport);
        initialMap();
        mIvSport = findViewById(R.id.iv_sport);
        Bundle bundle = getIntent().getExtras();
        sport_name = bundle.getString("sport_name");
        mIvSport.setImageResource(sportRMap.get(sport_name));
        mIBStart = findViewById(R.id.btn_start);
        mIBPause = findViewById(R.id.btn_pause);
        mIBStop = findViewById(R.id.btn_stop);
        timer = new Timer(findViewById(R.id.timer));
        clickStart();
        clickPause();
        clickStop();
    }

    private void initialMap() {
        sportRMap.put("run", R.drawable.ic_run);
        sportRMap.put("walk", R.drawable.ic_walk);
        sportRMap.put("fit", R.drawable.ic_fit);
        sportRMap.put("td", R.drawable.ic_td);
        sportRMap.put("swim", R.drawable.ic_swim);
        sportRMap.put("basketball", R.drawable.ic_basketball);
        sportRMap.put("tennis", R.drawable.ic_tennis);
        sportRMap.put("tabletennis", R.drawable.ic_tabletennis);
        sportRMap.put("billiard", R.drawable.ic_billiard);
        sportRMap.put("badminton", R.drawable.ic_badminton);
        sportRMap.put("volleyball", R.drawable.ic_volleyball);
        sportRMap.put("soccer", R.drawable.ic_soccer);
        sportRMap.put("frisbee", R.drawable.ic_frisbee);
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
                bundle.putString("sport_name", sport_name);
                timer.stop();
                Tools.toastMessageShort(BallSportActivity.this, "运动结束!");
                mIBStop.setVisibility(View.GONE);
                mIBStart.setVisibility(View.VISIBLE);
                mIBPause.setVisibility(View.GONE);
                Intent intent = new Intent(BallSportActivity.this, CompleteSportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}