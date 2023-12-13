package com.example.buaaexercise.group;

import android.icu.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GroupItem {
    //Fixme: 改成 User 类型 ?
    private String organizer;
    private List<String> joiners = new ArrayList<>();
    private int joinerLimit;
    private String sport;
    private String description;
    private Date planStartTime;
    private Date planEndTime;
    private String location;
    private Date createTime;

    private int groupKey;

    public GroupItem(String organizer, int joinerLimit, String sport, String description,
                     Date planStartTime, Date planEndTime, String location, int groupKey) {
        this.organizer = organizer;
        joiners.add(organizer);
        this.joinerLimit = joinerLimit;
        this.sport = sport;
        this.description = description;
        this.planStartTime = planStartTime;
        this.planEndTime = planEndTime;
        this.location = location;
        this.createTime = new Date();
        this.groupKey = groupKey;
    }


    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getJoinerLimit() {
        return joinerLimit;
    }

    public String getSport() {
        return sport;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getJoiners() {
        return joiners;
    }

    public void setJoiners(List<String> joiners) {
        this.joiners = joiners;
    }

    public boolean addJoiner(String joiner) {
        if (this.joiners.size() >= this.joinerLimit) {
            return false;
        }
        if (Objects.equals(this.organizer, "Wait for an organizer...")) {
            this.organizer = joiner;
        }
        this.joiners.add(joiner);
        return true;
    }

    public boolean removeJoiner(String joiner) {
        if (Objects.equals(joiner, this.organizer)) {
            if (this.joiners.size() > 1) {
                this.organizer = joiners.get(0);
            } else {
                this.organizer = "Wait for an organizer...";
            }
        }
        return this.joiners.remove(joiner);
    }

    public boolean hasJoiner(String joiner) {
        return joiners.contains(joiner);
    }

    public static String formatDateTimeShort(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    public String getPlanStartTime() {
        return formatDateTimeShort(planStartTime);
    }

    public Date getPlanStartTimeForDB() {
        return this.planStartTime;
    }

    public String getPlanEndTime() {
        return formatDateTimeShort(planEndTime);
    }

    public Date getPlanEndTimeForDB() {
        return this.planEndTime;
    }

    public String getCreateTime() {
        return formatDateTimeShort(createTime);
    }

    public Date getCreateTimeForDB() {
        return this.createTime;
    }

    public String getLocation() {
        return location;
    }

    public int getGroupKey() {
        return groupKey;
    }
}
