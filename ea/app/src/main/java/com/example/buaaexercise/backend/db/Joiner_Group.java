package com.example.buaaexercise.backend.db;

import android.util.Log;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class Joiner_Group extends LitePalSupport {
    private int isOrganizer = 0;
    //
    private long user_id;
    private User joiner;
    private long _group_id;
    private _Group group;

    //  外键约束
    public int getJoinerId() {
        return (int) user_id;
    }

    public User getJoiner() {
        User user = LitePal.find(User.class, user_id);
        if (user != null) {
            return user;
        } else {
            throw new NullPointerException("外键约束异常：没有找到对应的user");
        }
    }

    public boolean setJoinerByName(String JoinerName) {
        User user = DBFunction.findUserByName(JoinerName);
        if (user != null) {
            this.joiner = user;
            return true;
        } else {
            Log.w(DBFunction.TAG, "数据不符合外键约束！插入数据失败");
            return false;
        }
    }

    public int getGroupId() {
        return (int) _group_id;
    }

    public _Group getGroup() {
        _Group group = LitePal.find(_Group.class, _group_id);
        if (group != null) {
            return group;
        } else {
            throw new NullPointerException("外键约束异常：没有找到对应的group");
        }
    }

    public void setGroup(_Group group) {
        this.group = group;
    }

    public boolean setGroupById(int groupKey) {
        _Group group = LitePal.find(_Group.class, groupKey);
        if (group != null) {
            this.group = group;
            return true;
        } else {
            Log.w(DBFunction.TAG, "数据不符合外键约束！插入数据失败");
            return false;
        }
    }

    // 普通 get & set 方法

    public int getIsOrganizer() {
        return isOrganizer;
    }

    public void setIsOrganizer(int isOrganizer) {
        this.isOrganizer = isOrganizer;
    }
}
