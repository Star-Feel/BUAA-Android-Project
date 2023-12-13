package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

/*
    用FIXME-1 ok来标注需要和队友工作进行对接的地方
*/
public class MainActivity extends AppCompatActivity {
    // 全局变量, 用于存当前登录着的用户名
    private static String currentUsername;
    private Button loginButton;
    private TextView clickToRegister;
    private EditText inputUsername;
    private EditText inputPassword;

    public static String getCurrentUsername() {
        return currentUsername;
    }

    private enum LoginCheckResult {
        // 优先级自上至下
        NO_USERNAME,
        NO_PASSWORD,
        USER_PASSWORD_NOT_MATCH,
        SUCCESS
    }

    // 不能写构造器
    // public MainActivity() {
    //
    // }

    public LoginCheckResult checkLoginUser(String username, String password) {
        if (username.equals("")) {
            return LoginCheckResult.NO_USERNAME;
        } else if (password.equals("")) {
            return LoginCheckResult.NO_PASSWORD;
        }
        // FIXME-1 ok 对接用户名和密码合法性检查函数
        // isAllowLogin()
        boolean isAllow = DBFunction.isAllowLogin(username, password);
        if ((username.equals("1") && password.equals("1")) || isAllow) // 假定当前有1 1这个用户
            return LoginCheckResult.SUCCESS;
        else
            return LoginCheckResult.USER_PASSWORD_NOT_MATCH;
    }

    public void tryLogin(String username, String password) {
        LoginCheckResult res = checkLoginUser(username, password);
        switch (res) {
            case NO_USERNAME: Tools.toastMessageShort(MainActivity.this, "请输入用户名"); break;
            case NO_PASSWORD: Tools.toastMessageShort(MainActivity.this, "请输入密码"); break;
            case USER_PASSWORD_NOT_MATCH: Tools.toastMessageShort(MainActivity.this, "用户名和密码不匹配"); break;
            default: {
                // 登录成功, 跳转到目标界面
                // FIXME-1 ok 对接到要跳转到的页面名
                currentUsername = username;
                startActivity(new Intent(MainActivity.this, HomepageActivity.class));
                Tools.toastMessageShort(MainActivity.this, "登录成功!");
                finish();
            }
        }
    }

    private void initAttribute() {
        // 令java程序识别到这些控件
        this.loginButton = (Button)findViewById(R.id.login);
        this.clickToRegister = (TextView)findViewById(R.id.text_djzc);
        this.inputUsername = (EditText)findViewById(R.id.inputUserName);
        this.inputPassword = (EditText)findViewById(R.id.inputPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAttribute();
        setOnClickListeners();
    }

    public void setOnClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Intent intent1 = new Intent(MainActivity.this,doctor_personalcentre.class); // 这个写死,先写医生的;录完视频再写病人的.
                 Intent intent2 = new Intent(MainActivity.this,activity_patient.class);

                 if ("lidongjie".equals(inputUsername.getText().toString())) {
                    startActivity(intent1);
                } else {
                    startActivity(intent2);
                }*/
                // 身份合法性检验
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                tryLogin(username, password);
            }
        });

        clickToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
