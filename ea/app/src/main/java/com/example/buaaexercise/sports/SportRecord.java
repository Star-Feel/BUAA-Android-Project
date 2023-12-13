package com.example.buaaexercise.sports;

import java.util.Date;

public class SportRecord {
    private String sportName;
    private int sumTime;
    private Date endTime;
    private int score;
    private String partner;
    private String note;
    private int rate;

    public SportRecord(String sportName, int sumTime, Date endTime
            , int score, String partner, String note, int rate) {
        this.sportName = sportName;
        this.sumTime = sumTime;
        this.endTime = endTime;
        this.score = score;
        this.partner = partner;
        this.note = note;
        this.rate = rate;
    }

    public String getNote() {
        return note;
    }

    public String getSportName() {
        return sportName;
    }

    public String getPartner() {
        return partner;
    }

    public int getSumTime() {
        return sumTime;
    }

    public int getScore() {
        return score;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getRate() {
        return rate;
    }
}