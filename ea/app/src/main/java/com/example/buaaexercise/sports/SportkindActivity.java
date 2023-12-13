package com.example.buaaexercise.sports;

import static com.example.buaaexercise.backend.dbFunction.DBFunction.addStar;
import static com.example.buaaexercise.backend.dbFunction.DBFunction.cancelStar;
import static com.example.buaaexercise.backend.dbFunction.DBFunction.ifStared;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SportkindActivity extends AppCompatActivity {

    // run walk fit
    // td swim basketball
    // tennis tabletennis billiard
    // badminton volleyball soccer

    private RelativeLayout mLLrun;
    private RelativeLayout mLLwalk;
    private RelativeLayout mLLfit;
    private RelativeLayout mLLtd;
    private RelativeLayout mLLswim;
    private RelativeLayout mLLbasketball;
    private RelativeLayout mLLtennis;
    private RelativeLayout mLLtabletennis;
    private RelativeLayout mLLbilliard;
    private RelativeLayout mLLbadminton;
    private RelativeLayout mLLvolleyball;
    private RelativeLayout mLLsoccer;
    private RelativeLayout mLLfrisbee;

    private ImageButton mIbrun, mIbwalk, mIbfit, mIbtd, mIbswim, mIbbasketball
            , mIbtennis, mIbtabletennis, mIbbilliard, mIbbadminton, mIbvolleyball, mIbsoccer
            , mIbfrisbee;

    private ImageView mIvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sportkind);
        initial();


        mIvReturn = findViewById(R.id.Iv_return);
        mIvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void initial() {
        setRun();
        setWalk();
        setFit();
        setTd();
        setSwim();
        setBall();
    }

    Bundle sendBundle(String sport_name) {
        Bundle bundle = new Bundle();
        bundle.putString("sport_name", sport_name);
        return bundle;
    }

    void setRun() {
        mLLrun = findViewById(R.id.ll_run);
        mLLrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, RunSportActivity.class);
                intent.putExtras(sendBundle("run"));
                startActivity(intent);
            }
        });

        mIbrun = findViewById(R.id.heart_run);
        if (ifStared(MainActivity.getCurrentUsername(), "run")) {
            mIbrun.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbrun.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "run")) {
                    cancelStar(MainActivity.getCurrentUsername(), "run");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "run");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "run")) {
                    mIbrun.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbrun.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }

    void setWalk() {
        mLLwalk = findViewById(R.id.ll_walk);
        mLLwalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, WalkSportActivity.class);
                intent.putExtras(sendBundle("walk"));
                startActivity(intent);
            }
        });

        mIbwalk = findViewById(R.id.heart_walk);
        if (ifStared(MainActivity.getCurrentUsername(), "walk")) {
            mIbwalk.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbwalk.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbwalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "walk")) {
                    cancelStar(MainActivity.getCurrentUsername(), "walk");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "walk");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "walk")) {
                    mIbwalk.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbwalk.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }

    void setFit() {
        mLLfit = findViewById(R.id.ll_fit);
        mLLfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, FitSportActivity.class);
                intent.putExtras(sendBundle("fit"));
                startActivity(intent);
            }
        });

        mIbfit = findViewById(R.id.heart_fit);
        if (ifStared(MainActivity.getCurrentUsername(), "fit")) {
            mIbfit.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbfit.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "fit")) {
                    cancelStar(MainActivity.getCurrentUsername(), "fit");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "fit");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "fit")) {
                    mIbfit.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbfit.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }

    void setTd() {
        mLLtd = findViewById(R.id.ll_td);
        mLLtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, TdSportActivity.class);
                intent.putExtras(sendBundle("td"));
                startActivity(intent);
            }
        });

        mIbtd = findViewById(R.id.heart_td);
        if (ifStared(MainActivity.getCurrentUsername(), "td")) {
            mIbtd.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbtd.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbtd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "td")) {
                    cancelStar(MainActivity.getCurrentUsername(), "td");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "td");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "td")) {
                    mIbtd.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbtd.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }

    void setSwim() {
        mLLswim = findViewById(R.id.ll_swim);
        mLLswim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, SwimSportActivity.class);
                intent.putExtras(sendBundle("swim"));
                startActivity(intent);
            }
        });

        mIbswim = findViewById(R.id.heart_swim);
        if (ifStared(MainActivity.getCurrentUsername(), "swim")) {
            mIbswim.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbswim.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbswim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "swim")) {
                    cancelStar(MainActivity.getCurrentUsername(), "swim");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "swim");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "swim")) {
                    mIbswim.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbswim.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }

    void setBall() {
        mLLbasketball = findViewById(R.id.ll_basketball);
        mLLbasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("basketball"));
                startActivity(intent);
            }
        });

        mIbbasketball = findViewById(R.id.heart_basketball);
        if (ifStared(MainActivity.getCurrentUsername(), "basketball")) {
            mIbbasketball.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbbasketball.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbbasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "basketball")) {
                    cancelStar(MainActivity.getCurrentUsername(), "basketball");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "basketball");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "basketball")) {
                    mIbbasketball.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbbasketball.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLtennis = findViewById(R.id.ll_tennis);
        mLLtennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("tennis"));
                startActivity(intent);
            }
        });
        mIbtennis = findViewById(R.id.heart_tennis);
        if (ifStared(MainActivity.getCurrentUsername(), "tennis")) {
            mIbtennis.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbtennis.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbtennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "tennis")) {
                    cancelStar(MainActivity.getCurrentUsername(), "tennis");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "tennis");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "tennis")) {
                    mIbtennis.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbtennis.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLtabletennis = findViewById(R.id.ll_tabletennis);
        mLLtabletennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("tabletennis"));
                startActivity(intent);
            }
        });
        mIbtabletennis = findViewById(R.id.heart_tabletennis);
        if (ifStared(MainActivity.getCurrentUsername(), "tabletennis")) {
            mIbtabletennis.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbtabletennis.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbtabletennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "tabletennis")) {
                    cancelStar(MainActivity.getCurrentUsername(), "tabletennis");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "tabletennis");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "tabletennis")) {
                    mIbtabletennis.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbtabletennis.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLbilliard = findViewById(R.id.ll_billiard);
        mLLbilliard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("billiard"));
                startActivity(intent);
            }
        });
        mIbbilliard = findViewById(R.id.heart_billiard);
        if (ifStared(MainActivity.getCurrentUsername(), "billiard")) {
            mIbbilliard.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbbilliard.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbbilliard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "billiard")) {
                    cancelStar(MainActivity.getCurrentUsername(), "billiard");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "billiard");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "billiard")) {
                    mIbbilliard.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbbilliard.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLbadminton = findViewById(R.id.ll_badminton);
        mLLbadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("badminton"));
                startActivity(intent);
            }
        });
        mIbbadminton = findViewById(R.id.heart_badminton);
        if (ifStared(MainActivity.getCurrentUsername(), "badminton")) {
            mIbbadminton.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbbadminton.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbbadminton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "badminton")) {
                    cancelStar(MainActivity.getCurrentUsername(), "badminton");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "badminton");

                }
                if (ifStared(MainActivity.getCurrentUsername(), "badminton")) {
                    mIbbadminton.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbbadminton.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLvolleyball = findViewById(R.id.ll_volleyball);
        mLLvolleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("volleyball"));
                startActivity(intent);
            }
        });
        mIbvolleyball = findViewById(R.id.heart_volleyball);
        if (ifStared(MainActivity.getCurrentUsername(), "volleyball")) {
            mIbvolleyball.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbvolleyball.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbvolleyball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "volleyball")) {
                    cancelStar(MainActivity.getCurrentUsername(), "volleyball");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "volleyball");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "volleyball")) {
                    mIbvolleyball.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbvolleyball.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });

        mLLsoccer = findViewById(R.id.ll_soccer);
        mLLsoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("soccer"));
                startActivity(intent);
            }
        });
        mIbsoccer = findViewById(R.id.heart_soccer);
        if (ifStared(MainActivity.getCurrentUsername(), "soccer")) {
            mIbsoccer.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbsoccer.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbsoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "soccer")) {
                    cancelStar(MainActivity.getCurrentUsername(), "soccer");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "soccer");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "soccer")) {
                    mIbsoccer.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbsoccer.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });


        mLLfrisbee = findViewById(R.id.ll_frisbee);
        mLLfrisbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportkindActivity.this, BallSportActivity.class);
                intent.putExtras(sendBundle("frisbee"));
                startActivity(intent);
            }
        });
        mIbfrisbee = findViewById(R.id.heart_frisbee);
        if (ifStared(MainActivity.getCurrentUsername(), "frisbee")) {
            mIbfrisbee.setBackgroundResource(R.drawable.ic_heart);
        } else {
            mIbfrisbee.setBackgroundResource(R.drawable.ic_heart_empty);
        }
        mIbfrisbee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifStared(MainActivity.getCurrentUsername(), "frisbee")) {
                    cancelStar(MainActivity.getCurrentUsername(), "frisbee");
                } else {
                    addStar(MainActivity.getCurrentUsername(), "frisbee");
                }
                if (ifStared(MainActivity.getCurrentUsername(), "frisbee")) {
                    mIbfrisbee.setBackgroundResource(R.drawable.ic_heart);
                } else {
                    mIbfrisbee.setBackgroundResource(R.drawable.ic_heart_empty);
                }
            }
        });
    }
}