package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buaaexercise.backend.dbFunction.DBFunction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AccountSafeActivity extends AppCompatActivity {
    private RelativeLayout cancelAccountButton;
    private RelativeLayout changePasswordButton;
    private ImageView returnButton;

    private void initAttribute() {
        cancelAccountButton = findViewById(R.id.card_cancel_account);
        changePasswordButton = findViewById(R.id.card_change_password);
        returnButton = findViewById(R.id.return_button);
    }

    public void setOnClickListeners() {
        cancelAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSafeActivity.this, MainActivity.class);
                // FIXME-1 ok 将username用户从数据库中删除 void cancelUser(String username)
                DBFunction.cancelUser(MainActivity.getCurrentUsername());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSafeActivity.this, ChangePasswordActivity.class);
                intent.putExtra(Tools.SRC_ACTIVITY, Tools.ACCOUNT_SAFE_ACTIVITY);
                startActivity(intent);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountSafeActivity.this, SettingActivity.class));
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        initAttribute();
        setOnClickListeners();
    }
}
