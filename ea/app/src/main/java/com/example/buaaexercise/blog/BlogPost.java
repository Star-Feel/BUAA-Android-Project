package com.example.buaaexercise.blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPost {
    private int blogKey;
    private String title;
    private String author;
    private Date postTime;
    private String content;
    private List<BlogComment> comments = new ArrayList<>();

    private String imageID = null;

    public BlogPost(String title, String author, Date postTime, String content, int blogKey) {
        this.title = title;
        this.author = author;
        this.postTime = postTime;
        this.content = content;
        this.blogKey = blogKey;
    }

    public void addComment(BlogComment comment) {
        this.comments.add(0, comment);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getPostTime() {
        return postTime;
    }

    public String getContent() {
        return content;
    }

    public List<BlogComment> getComments() {
        return comments;
    }

    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    public int getBlogKey() {
        return blogKey;
    }

    public String getImageID() {
        return imageID;
    }
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
