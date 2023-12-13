package com.example.buaaexercise.group;

import android.icu.text.SimpleDateFormat;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Locale;

public class GroupComment {
    private String text;
    private String author;
    private Date postTime;
    private int belongKey;
    private int commentKey;

    public GroupComment(String text, String author, int belongKey, int commentKey) {
        this.text = text;
        this.author = author;
        this.postTime = new Date();
        this.commentKey = commentKey;
        this.belongKey = belongKey;
    }


    public String getAuthor() {
        return author;
    }

    public Date getPostTime() {
        return postTime;
    }

    public String getText() {
        return text;
    }

    public static String formatDateTimeShort(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    @NonNull
    public String toString() {
        return  author + ": \n" + text + "\n                                       " +
                formatDateTimeShort(postTime);
    }

    public int getBelongKey() {
        return belongKey;
    }

    public int getCommentKey() {
        return commentKey;
    }
}