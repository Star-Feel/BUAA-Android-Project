package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.buaaexercise.blog.sensitive_detect.SensitiveWordFilter;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(this, HomepageActivity.class));

        final View view = View.inflate(this, R.layout.activity_welcome, null);
        // setContentView(R.layout.activity_welcome);
        setContentView(view);
        // 设置透明度渐变动画
        AlphaAnimation welcomeEffect = new AlphaAnimation(0.3f, 1.0f);
        // 设置动画持续时间
        welcomeEffect.setDuration(1600);
        // 绑定动画效果, view带有启动图片信息, welcomeEffect带有图片的效果
        view.startAnimation(welcomeEffect);
        // 给这个图片的效果加上监听器
        welcomeEffect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goToMain(); // 这个方法会在动画结束时被调用, 结束后回到main界面
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void goToMain() {
        SensitiveWordFilter.loadWordFromFile(this, "SensitiveWordList.txt");
        startActivity(new Intent(this, MainActivity.class));
        finish(); // 回到main的同时将本开机动画销毁
    }
}
