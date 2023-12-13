package com.example.buaaexercise.backend.db;

import android.util.Log;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Calendar;
import java.util.Date;

public class ExerciseRecord extends LitePalSupport {
    private long id;        //    private int recordKey;
    private String sportName;   // 运动类型
    private int sumTime;
    private int year;
    private int month;
    private int day;
    private Date endTime;
    private int score;  // 由于公里数和得分不同时出现，所以只需要存一种就行，根据类型决定
    private String partner;
    private String note;
    private int rate;

    //
    private long user_id;
    private User user; //foreign key refer to User	（该记录的用户）

    //  外键约束
    public int getUserId() {
        return (int) user_id;
    }

    public User getUser() {
        User user = LitePal.find(User.class, user_id);
        if (user != null) {
            return user;
        } else {
            throw new NullPointerException("外键约束异常：没有找到对应的user");
        }
    }

    public boolean setUserByName(String userName) {
        User user = DBFunction.findUserByName(userName);
        if (user != null) {
            this.user = user;
            return true;
        } else {
            Log.w(DBFunction.TAG, "数据不符合外键约束！插入数据失败");
            return false;
        }
    }

    // 普通 get & set 方法
    public long getId() {
        return id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getSumTime() {
        return sumTime;
    }

    public void setSumTime(int sumTime) {
        this.sumTime = sumTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要加1
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}