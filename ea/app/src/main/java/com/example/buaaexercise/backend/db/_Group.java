package com.example.buaaexercise.backend.db;

import android.util.Log;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class _Group extends LitePalSupport {
    private long id;        //    private int groupKey;
    private int joinerLimit = -1;
    private String sport;
    private String description;
    private Date planStartTime;
    private Date planEndTime;
    private String location;
    private Date createTime;
    //
    private ArrayList<Joiner_Group> joinerGroupList = new ArrayList<>(); // foriegn key
    public List<GroupCommentTable> groupCommentTables = new ArrayList<>();

    public int getCurrentPersonNum() {
        return LitePal.where("_group_id=?", String.valueOf(id)).count(Joiner_Group.class);
    }

    //  外键约束
    private User getOrganizer() {    // Fixme:调用者小心，返回值为空情况！
        List<Joiner_Group> joinerGroupList = LitePal.where("_group_id = ? and isorganizer = ?", String.valueOf(id), String.valueOf(1)).find(Joiner_Group.class);
        if (!joinerGroupList.isEmpty()) {
            return joinerGroupList.get(0).getJoiner();
        } else {
            Log.w(DBFunction.TAG, "组局没有组织者,组织者可能已离开");
            return null;
        }
    }

    public String getOrganizerName() {
        // 适配前端逻辑
        User user = getOrganizer();
        if (user == null) {
            return "Wait for an organizer...";
        } else {
            return user.getUsername();
        }
    }

    public boolean addJoinerByName(String joinerName, boolean isOrganizer) { //已保证，不加入重复的数据
        Joiner_Group joiner_group = new Joiner_Group();
        joiner_group.setIsOrganizer(isOrganizer ? 1 : 0);
        boolean foreignKeyOK = joiner_group.setJoinerByName(joinerName);
        //
        joiner_group.setGroup(this);
        if (foreignKeyOK) {
            //防止加入重复数据
            User user = DBFunction.findUserByName(joinerName);
            if (!LitePal.isExist(Joiner_Group.class, "user_id=? and _group_id=?", String.valueOf(user.getId()), String.valueOf(id))) {
                joiner_group.save();
            }
        }
        return foreignKeyOK;
    }
//    public boolean setOrganizerByName(String organizerName) {     //已保证，不加入重复的数据
//        Joiner_Group joiner_group = new Joiner_Group();
//        joiner_group.setIsOrganizer(1);
//        boolean foreignKeyOK = joiner_group.setJoinerByName(organizerName);
//        joiner_group.setGroup(this);
//        if (foreignKeyOK) {
//            //防止加入重复数据
//            User user = DBFunction.findUserByName(organizerName);
//            if (!LitePal.isExist(Joiner_Group.class, "user_id=? and _group_id=?", String.valueOf(user.getId()), String.valueOf(id))) {
//                joiner_group.save();
//            }
//        }
//        return foreignKeyOK;
//    }


    public List<User> getJoiners() {
        List<Joiner_Group> joinerGroupList = LitePal.where("_group_id = ?", String.valueOf(id)).find(Joiner_Group.class);
        List<User> joiners = new ArrayList<>();
        for (Joiner_Group joinerGroup : joinerGroupList) {
            joiners.add(joinerGroup.getJoiner());
        }
        return joiners;
    }

    public List<User> getJoinersExceptOrganizer() {
        List<Joiner_Group> joinerGroupList = LitePal.where("_group_id = ? and isorganizer = ?", String.valueOf(id), String.valueOf(0)).find(Joiner_Group.class);
        List<User> joiners = new ArrayList<>();
        for (Joiner_Group joinerGroup : joinerGroupList) {
            joiners.add(joinerGroup.getJoiner());
        }
        return joiners;
    }

    // 普通 get & set 方法

    public int getGroupKey() {
        return (int) id;
    }

    public int getJoinerLimit() {
        return joinerLimit;
    }

    public void setJoinerLimit(int joinerLimit) {
        this.joinerLimit = joinerLimit;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPlanStartTime() {
        if (planStartTime == null) {
            return new Date();
        }
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public Date getPlanEndTime() {
        if (planEndTime == null) {
            return new Date();
        }
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreateTime() {
        if (createTime == null) {
            return new Date();
        }
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
