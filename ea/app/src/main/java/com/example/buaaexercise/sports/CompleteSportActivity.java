package com.example.buaaexercise.sports;

import static com.example.buaaexercise.backend.dbFunction.DBFunction.addRecordToDB;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buaaexercise.HomepageActivity;
import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;

import java.util.ArrayList;
import java.util.Date;

public class CompleteSportActivity extends AppCompatActivity {

    private String sport_name;
    private int sport_time;
    private int rate = 0;

    private TextView mTvRecord1;
    private EditText mEtScore;
    private EditText mEtPartner;
    private EditText mEtNote;
    private Button mBtnComplete;
    private TextView mTvSportTime;

    private ImageView mIvReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sport);
        mTvRecord1 = findViewById(R.id.tv_record1);
        mEtScore = findViewById(R.id.et_score);
        mEtScore.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEtPartner = findViewById(R.id.et_partner);
        mEtNote = findViewById(R.id.et_note);
        mBtnComplete = findViewById(R.id.button_complete);
        mTvSportTime = findViewById(R.id.tv_sport_time);

        mIvReturn = findViewById(R.id.Iv_return);
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // Intent intent = new Intent(CompleteSportActivity.this, SportkindActivity.class);
                // startActivity(intent);
            }
        });


        Bundle bundle = getIntent().getExtras();
        sport_name = bundle.getString("sport_name");
        sport_time = bundle.getInt("sport_time");

        String str = "%02d:%02d:%02d";
        str = String.format(str, sport_time/3600, sport_time%3600/60, sport_time%60);
        mTvSportTime.setText(str);
        switch (sport_name) {
            case "run":
                setRun();
                break;
            case "fit":
                setFit();
                break;
            case "td":
                setTd();
                break;
            case "walk":
                setWalk();
                break;
            case "swim":
                setSwim();
                break;
            default:
                setBall();
                break;
        }
        setRate();
        readyToSave();
    }

    void readyToSave() {
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                Log.d("rate", "-------------------rate = " + rate + "------------");

                int score = 0;
                if (mEtScore.getText().toString().length() != 0) {
                    score = Integer.valueOf(mEtScore.getText().toString());
                }
                Log.d("score", "----------------------score: " + score + "------------------");

                String partner = "Single";
                if (mEtPartner.getText().toString().length() != 0) {
                    partner = mEtPartner.getText().toString();
                }

                String note = "Default";
                if (mEtNote.getText().toString().length() != 0) {
                    note = mEtNote.getText().toString();
                }

                SportRecord sportRecord = new SportRecord(sport_name, sport_time, date, score
                        , partner, note, rate);
                addRecordToDB(MainActivity.getCurrentUsername(), sportRecord);
                Tools.toastMessageShort(CompleteSportActivity.this, "运动已记录");
                Intent intent = new Intent(CompleteSportActivity.this, HomepageActivity.class);
                startActivity(intent);
            }
        });
    }

    void setRun() {
        mTvRecord1.setText("跑步总公里数");
    }

    void setFit() {
        mTvRecord1.setText("重量级(kg)");
    }

    void setTd() {
        mTvRecord1.setText("本次运动 td 次数");
    }

    void setWalk() {
        mTvRecord1.setText("徒步总公里数");
    }

    void setSwim() {
        mTvRecord1.setText("游泳总公里数");
    }

    void setBall() {
        mTvRecord1.setText("得分");
    }

    private ImageButton mTbStar1;
    private ImageButton mTbStar2;
    private ImageButton mTbStar3;
    private ImageButton mTbStar4;
    private ImageButton mTbStar5;

    private boolean[] isStared = new boolean[6];

    void setStared(ImageButton ib) {
        ib.setImageResource(R.drawable.ic_star);
    }

    void setUnStared(ImageButton ib) {
        ib.setImageResource(R.drawable.ic_star_line);
    }

    void setRate() {
        mTbStar1 = findViewById(R.id.ib_star_1);
        mTbStar2 = findViewById(R.id.ib_star_2);
        mTbStar3 = findViewById(R.id.ib_star_3);
        mTbStar4 = findViewById(R.id.ib_star_4);
        mTbStar5 = findViewById(R.id.ib_star_5);

        ArrayList<ImageButton> stars = new ArrayList<>();
        stars.add(mTbStar1);
        stars.add(mTbStar1);
        stars.add(mTbStar2);
        stars.add(mTbStar3);
        stars.add(mTbStar4);
        stars.add(mTbStar5);

        isStared[1] = false;
        isStared[2] = false;
        isStared[3] = false;
        isStared[4] = false;
        isStared[5] = false;

        mTbStar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStared[1]) {
                    if (rate > 1) {
                        for (int j = 1 + 1; j <= 5; j++) {
                            isStared[j] = false;
                            setUnStared(stars.get(j));
                        }
                        rate = 1;
                    } else {
                        isStared[1] = false;
                        setUnStared(stars.get(1));
                        rate = 1 - 1;
                    }
                } else {
                    for (int j = 1; j <= 1; j++) {
                        isStared[j] = true;
                        setStared(stars.get(j));
                    }
                    rate = 1;
                }
            }
        });
        mTbStar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStared[2]) {
                    if (rate > 2) {
                        for (int j = 2 + 1; j <= 5; j++) {
                            isStared[j] = false;
                            setUnStared(stars.get(j));
                        }
                        rate = 2;
                    } else {
                        isStared[2] = false;
                        setUnStared(stars.get(2));
                        rate = 2 - 1;
                    }
                } else {
                    for (int j = 1; j <= 2; j++) {
                        isStared[j] = true;
                        setStared(stars.get(j));
                    }
                    rate = 2;
                }
            }
        });

        mTbStar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStared[3]) {
                    if (rate > 3) {
                        for (int j = 3 + 1; j <= 5; j++) {
                            isStared[j] = false;
                            setUnStared(stars.get(j));
                        }
                        rate = 3;
                    } else {
                        isStared[3] = false;
                        setUnStared(stars.get(3));
                        rate = 3 - 1;
                    }
                } else {
                    for (int j = 1; j <= 3; j++) {
                        isStared[j] = true;
                        setStared(stars.get(j));
                    }

                    rate = 3;
                }
            }
        });

        mTbStar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStared[4]) {
                    if (rate > 4) {
                        for (int j = 4 + 1; j <= 5; j++) {
                            isStared[j] = false;
                            setUnStared(stars.get(j));
                        }
                        rate = 4;
                    } else {
                        isStared[4] = false;
                        setUnStared(stars.get(4));
                        rate = 4 - 1;
                    }
                } else {
                    for (int j = 1; j <= 4; j++) {
                        isStared[j] = true;
                        setStared(stars.get(j));
                    }
                    rate = 4;
                }
            }
        });

        mTbStar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStared[5]) {
                    if (rate > 5) {
                        for (int j = 5 + 1; j <= 5; j++) {
                            isStared[j] = false;
                            setUnStared(stars.get(j));
                        }
                        rate = 5;
                    } else {
                        isStared[5] = false;
                        setUnStared(stars.get(5));
                        rate = 5 - 1;
                    }
                } else {
                    for (int j = 1; j <= 5; j++) {
                        isStared[j] = true;
                        setStared(stars.get(j));
                    }
                    rate = 5;
                }
            }
        });


    }

}