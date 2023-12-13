package com.example.buaaexercise.sports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.buaaexercise.HomepageActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;

public class TdSportActivity extends AppCompatActivity {

    private ImageButton mIBStart;
    private ImageButton mIBPause;
    private ImageButton mIBStop;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_td_sport);
        mIBStart = findViewById(R.id.btn_start);
        mIBPause = findViewById(R.id.btn_pause);
        mIBStop = findViewById(R.id.btn_stop);
        timer = new Timer(findViewById(R.id.timer));
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
                bundle.putString("sport_name", "td");
                timer.stop();
                Tools.toastMessageShort(TdSportActivity.this, "运动结束!");
                mIBStop.setVisibility(View.GONE);
                mIBStart.setVisibility(View.VISIBLE);
                mIBPause.setVisibility(View.GONE);
                Intent intent = new Intent(TdSportActivity.this, CompleteSportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}