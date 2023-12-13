package com.example.buaaexercise.group;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

public class CreateGroupActivity extends AppCompatActivity {
    private EditText editJoinerLimit;
    private EditText editSport;
    private EditText editDescription;
    private EditText editLocation;
    private DatePicker datePicker1, datePicker2;
    private TimePicker timePicker1, timePicker2;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        editJoinerLimit = findViewById(R.id.editJoinerLimit);
        editSport = findViewById(R.id.editSport);
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        datePicker1 = findViewById(R.id.datePicker1);
        timePicker1 = findViewById(R.id.timePicker1);
        datePicker2 = findViewById(R.id.datePicker2);
        timePicker2 = findViewById(R.id.timePicker2);
        calendar = Calendar.getInstance();

        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int joinerLimit = Integer.parseInt(editJoinerLimit.getText().toString());
                String sport = editSport.getText().toString();
                String description = editDescription.getText().toString();
                String location = editLocation.getText().toString();

                if (TextUtils.isEmpty(sport)) {
                    // 显示提示信息
                    Toast.makeText(CreateGroupActivity.this,
                            "活动内容不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(location)) {
                    Toast.makeText(CreateGroupActivity.this,
                            "活动地点不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(description)) {
                    Toast.makeText(CreateGroupActivity.this,
                            "活动描述不能为空", Toast.LENGTH_SHORT).show();
                } else if (joinerLimit < 1 || joinerLimit >100000) {
                    Toast.makeText(CreateGroupActivity.this,
                            "人数限制范围不合理 (至少为 1 人, 至多为 10w 人)", Toast.LENGTH_SHORT).show();
                } else {
                    // 获取开始时间的日期和时间
                    calendar.set(datePicker1.getYear(), datePicker1.getMonth(), datePicker1.getDayOfMonth(),
                            timePicker1.getCurrentHour(), timePicker1.getCurrentMinute());
                    Date planStartTime = calendar.getTime();

                    // 获取结束时间的日期和时间
                    calendar.set(datePicker2.getYear(), datePicker2.getMonth(), datePicker2.getDayOfMonth(),
                            timePicker2.getCurrentHour(), timePicker2.getCurrentMinute());
                    Date planEndTime = calendar.getTime();

                    sport = addIconToSport(sport);

                    GroupItem groupItem = new GroupItem(MainActivity.getCurrentUsername()
                            , joinerLimit, sport,
                            description, planStartTime, planEndTime, location, -1);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newGroup", new Gson().toJson(groupItem));
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }


    private String addIconToSport(String sport) {
        sport = sport.trim();
        if (sport.contains("跑步"))
            return sport + "🏃‍";
        if (sport.contains("徒步"))
            return sport + "🚶‍♀️";
        if (sport.contains("健身"))
            return sport + "💪";
        if (sport.toLowerCase().contains("td"))
            return sport + "🤸‍♂️";
        if (sport.contains("游泳"))
            return sport + "🏊‍";
        if (sport.contains("篮球"))
            return sport + "🏀";
        if (sport.contains("网球"))
            return sport + "🎾";
        if (sport.contains("乒乓球"))
            return sport + "🏓";
        if (sport.contains("台球"))
            return sport + "🎱";
        if (sport.contains("羽毛球"))
            return sport + "🏸";
        if (sport.contains("排球"))
            return sport + "🏐";
        if (sport.contains("足球"))
            return sport + "⚽";
        if (sport.toLowerCase().contains("citywalk") || sport.toLowerCase().contains("city walk"))
            return sport + "🌇";
        if (sport.contains("飞盘"))
            return sport + "🌌";
        return sport + "🚩";
    }
}
