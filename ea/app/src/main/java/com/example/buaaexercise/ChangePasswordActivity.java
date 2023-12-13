package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button changeButton;
    private ImageView returnButton;
    private EditText inputCurrentPassword;
    private EditText inputPassword1;
    private EditText inputPassword2;
    private final int PASSWORD_MIN_LENGTH = 8;
    private final String NO_CURRENT_PASSWORD_TOAST = "请输入当前密码";
    private final String CURRENT_PASSWORD_ERROR_TOAST = "输入的当前密码错误, 请重新输入";
    private final String NO_PASSWORD_TOAST = "请输入新密码";
    private final String NO_AGAIN_PASSWORD_TOAST = "请确认新密码";
    private final String PASSWORD_NOT_SAME_TOAST = "两次输入的新密码不一致, 请重新输入";
    private final String PASSWORD_TOO_SHORT_TOAST = "新密码长度应至少为8个字符";

    private enum ChangePasswordCheckResult {
        NO_CURRENT_PASSWORD,
        CURRENT_PASSWORD_ERROR,
        NO_PASSWORD,
        NO_AGAIN_PASSWORD,
        PASSWORD_NOT_SAME,
        PASSWORD_TOO_SHORT, // 密码长度至少为8
        SUCCESS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initAttribute();
        setOnClickListeners();
    }

    public void setOnClickListeners() {
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = inputCurrentPassword.getText().toString();
                String password1 = inputPassword1.getText().toString();
                String password2 = inputPassword2.getText().toString();
                tryChange(currentPassword, password1, password2);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = getIntent().getStringExtra(Tools.SRC_ACTIVITY);
                if (Tools.MY_FRAGMENT.equals(to)) { // 用这种 常量字符串==变量的方式可以避免变量是空指针还调用其equals方法导致异常的情况
                    Intent intent = new Intent(ChangePasswordActivity.this, HomepageActivity.class);
                    intent.putExtra("fragmentToShow", "我的");
                    startActivity(intent);
                } else if (Tools.ACCOUNT_SAFE_ACTIVITY.equals(to)) {
                    Intent intent = new Intent(ChangePasswordActivity.this, AccountSafeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public ChangePasswordCheckResult checkChangePassword(String currentPassword, String password1, String password2) {
        if (currentPassword.equals("")) {
            return ChangePasswordCheckResult.NO_CURRENT_PASSWORD;
        } else if (!checkCurrentPassword(MainActivity.getCurrentUsername(), currentPassword)) {
            return ChangePasswordCheckResult.CURRENT_PASSWORD_ERROR;
        } else if (password1.equals("")) {
            return ChangePasswordCheckResult.NO_PASSWORD;
        } else if (password2.equals("")) {
            return ChangePasswordCheckResult.NO_AGAIN_PASSWORD;
        } else if (!password1.equals(password2)) {
            return ChangePasswordCheckResult.PASSWORD_NOT_SAME;
        } else if (password1.length() < PASSWORD_MIN_LENGTH) {
            return ChangePasswordCheckResult.PASSWORD_TOO_SHORT;
        }
        return ChangePasswordCheckResult.SUCCESS;
    }

    public boolean checkCurrentPassword(String username, String inputCurrentPassword) {
        // 检查输入的当前密码是否正确
        // FIXME-1 ok 验证输入的密码的正确性的对接
        return DBFunction.isPasswordCorrect(username, inputCurrentPassword);
        // return isPasswordCorrect(username, inputCurrentPassword)
        // return true;
    }

    public void toastThis(String mes) {
        Tools.toastMessageShort(ChangePasswordActivity.this, mes);
    }

    public void tryChange(String currentPassword, String password1, String password2) {
        ChangePasswordCheckResult res = checkChangePassword(currentPassword, password1, password2);
        switch (res) {
            case NO_CURRENT_PASSWORD: toastThis(NO_CURRENT_PASSWORD_TOAST); break;
            case CURRENT_PASSWORD_ERROR: toastThis(CURRENT_PASSWORD_ERROR_TOAST); break;
            case NO_PASSWORD: toastThis(NO_PASSWORD_TOAST); break;
            case NO_AGAIN_PASSWORD: toastThis(NO_AGAIN_PASSWORD_TOAST); break;
            case PASSWORD_NOT_SAME: toastThis(PASSWORD_NOT_SAME_TOAST); break;
            case PASSWORD_TOO_SHORT: toastThis(PASSWORD_TOO_SHORT_TOAST); break;
            default: {
                // FIXME-1 ok 修改密码成功, 将数据库中的用户对应的密码同步更新
                DBFunction.changePassword(MainActivity.getCurrentUsername(), password1);
                // 跳转到登录界面
                Intent intent = new Intent(ChangePasswordActivity.this, HomepageActivity.class);
                intent.putExtra("fragmentToShow", "我的");
                startActivity(intent);
                toastThis("修改成功!");
            }
        }
    }

    public void initAttribute() {
        this.changeButton = (Button) findViewById(R.id.change);
        this.returnButton = (ImageView) findViewById(R.id.return_button);
        this.inputCurrentPassword = (EditText) findViewById(R.id.change_inputCurrentPassword);
        this.inputPassword1 = (EditText) findViewById(R.id.change_inputPassword);
        this.inputPassword2 = (EditText) findViewById(R.id.change_inputPassword_again);
    }
}
