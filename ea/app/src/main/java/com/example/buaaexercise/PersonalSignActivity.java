package com.example.buaaexercise;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.homepagefragments.MyFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalSignActivity extends AppCompatActivity {
    private EditText inputEditText;
    private ImageView returnButton;
    private Button saveButton;
    private TextView restLengthText;

    private void initAttribute() {
        inputEditText = findViewById(R.id.input);
        restLengthText = findViewById(R.id.rest_length_text);
        setInitEditContent();
        returnButton = findViewById(R.id.return_button);
        saveButton = findViewById(R.id.save_button);
    }

    void setInitEditContent() {
        // FIXME-1 ok 获取username的个性签名用于初始文字设置 String getUserPersonalSign(String username), 如果用户没设置, 返回空串
//        String initContent = "生命不息, 运动不止~";
        String initContent = DBFunction.getPersonalSign(MainActivity.getCurrentUsername());
        int len;
        if (!("null".equals(initContent) || initContent == null)) {
            inputEditText.setText(initContent);
            // inputEditText.setTextColor(Color.parseColor("#000000"));
            len = initContent.length();
        } else {
            // inputEditText.setText("未设置");
            len = 0;
        }
        int restLen = Tools.MAX_SIGN_LENGTH - len;
        // setText需要传入字符串参数
        restLengthText.setText(String.valueOf(restLen));
    }

    private void setListeners() {
        // 给文本框加上一个内容改变监听器, 一旦内容有改变就会触发里面的动作
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 在改变之前操作
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 在改变的时候操作
                int nowLength = Tools.MAX_SIGN_LENGTH - charSequence.length();
                restLengthText.setText(String.valueOf(nowLength));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 在改变之后操作
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 返回操作其实用一个 finish(); 语句来实现就好了
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FIXME-1 ok 将某个用户的个性签名改成这个 void updatePersonalSign(String username, String newSign)
                String newSign = inputEditText.getText().toString();
                DBFunction.setPersonalSign(MainActivity.getCurrentUsername(), newSign);
                // 回显新的个性签名
                MyFragment.setPersonalSignTextView(newSign);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_sign);
        initAttribute();
        setListeners();
    }
}
