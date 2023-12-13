package com.example.buaaexercise.backend.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.buaaexercise.backend.dbFunction.DBFunction;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;

public class BlogCommentTable extends LitePalSupport {
    //表中自动生成列名为id的自增key,此处拿出来是为了方便子表查询
    private long id;        //     private int commentKey; //primary
    private String text;
    private Date postTime;

    //
    private long user_id;
    private User user; // String author; //foreign key refer to User
    private int blog_id;
    private Blog blog; //int belongKey; //foreign key refer to Blog

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

    public int getBlogId() {
        return (int) blog_id;
    }

    public Blog getBlog() {
        Blog blog = LitePal.find(Blog.class, blog_id);
        if (blog != null) {
            return blog;
        } else {
            throw new NullPointerException("外键约束异常：没有找到对应的blog");
        }
    }

    public boolean setBlogById(int blogKey) {
        Blog blog = LitePal.find(Blog.class, blogKey);
        if (blog != null) {
            this.blog = blog;
            return true;
        } else {
            Log.w(DBFunction.TAG, "数据不符合外键约束！插入数据失败");
            return false;
        }
    }

    // 普通 get & set 方法
    public int getCommentKey() {
        return (int)id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Blog getBlogPost() {
        return blog;
    }

    public void setBlogPost(Blog blog) {
        this.blog = blog;
    }

    @NonNull
    public String toString() {
        return user.getUsername() + "\n" + text + "\n                      " + postTime;
    }
}