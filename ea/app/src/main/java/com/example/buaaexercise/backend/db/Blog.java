package com.example.buaaexercise.backend.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Blog extends LitePalSupport {
    //表中自动生成列名为id的自增key,此处拿出来是为了方便子表查询
    private long id;       //private int blogKey; //primary
    private String title; //
    private Date postTime; //
    private String content; //
    private String imageId;
    //
    private long user_id;
    private User user;  //String author; //foreign key refer to User
    private ArrayList<BlogCommentTable> comments = new ArrayList<>(); //int blogKey; //primary
    //TODO : ArrayList需要改成List吗？

    @NonNull
    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", author_id='" + user_id + '\'' +
                ", author='" + this.getAuthor() + '\'' +
                ", title='" + title + '\'' +
                ", postTime=" + postTime +
                ", content='" + content + '\'' +
                '}';
    }

    //  外键约束
    public int getAuthorId() {
        return (int) user_id;
    }

    public User getAuthor() {
        User user = LitePal.find(User.class, user_id);
        if (user != null) {
            return user;
        } else {
            throw new NullPointerException("外键约束异常：没有找到对应的user");
        }
    }

    public boolean setAuthorByName(String authorName) {
        User user = DBFunction.findUserByName(authorName);
        if (user != null) {
            this.user = user;
            return true;
        } else {
            Log.w(DBFunction.TAG, "数据不符合外键约束！插入数据失败");
            return false;
        }
    }

    public List<BlogCommentTable> getBlogCommentTableList() {
        return LitePal.where("blog_id = ?", String.valueOf(id)).find(BlogCommentTable.class);
    }

    // 普通 get & set 方法


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getBlogKey() {
        return (int) id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
