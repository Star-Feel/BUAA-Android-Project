package com.example.buaaexercise.blog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.blog.sensitive_detect.SensitiveWordFilter;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlogDetailActivity extends AppCompatActivity {
    private BlogPost blogPost;
    private ArrayAdapter<BlogComment> commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 22);

        // 获取从上一个活动传递过来的帖子信息
        String blogPostString = getIntent().getStringExtra("blogPost");
        blogPost = new Gson().fromJson(blogPostString, BlogPost.class);
        // 在帖子详情页面显示帖子信息
        TextView titleTextView = findViewById(R.id.titleTextView);
        TextView authorTextView = findViewById(R.id.authorTextView);
        TextView contentTextView = findViewById(R.id.contentTextView);
        TextView postTimeTextView = findViewById(R.id.postTimeTextView);
        ImageView postImageView = findViewById(R.id.postImageView);

        titleTextView.setText(blogPost.getTitle());
        authorTextView.setText(blogPost.getAuthor());
        contentTextView.setText(blogPost.getContent());

        if (blogPost.getImageID() == null) {
            postImageView.setVisibility(View.INVISIBLE);
        } else {

            postImageView.setVisibility(View.VISIBLE);
            Uri imageUri = Uri.parse(blogPost.getImageID());
            postImageView.setImageURI(imageUri);
            Glide.with(this)
                    .load(imageUri)
                    .into(postImageView);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String postTimeStr = sdf.format(blogPost.getPostTime());
        postTimeTextView.setText(postTimeStr);

        //Fixme: 从数据库中取帖子对应的评论 foreign: belongKey
        List<BlogComment> comments = DBFunction.getCommentsByBlog(blogPost.getBlogKey());
        commentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, comments);
        ListView commentListView = findViewById(R.id.commentListView);
        commentListView.setAdapter(commentAdapter);

        // 初始化添加评论的按钮
        ImageButton addCommentButton = findViewById(R.id.addCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCommentButtonClick(v);
            }
        });
    }


    // 处理添加评论按钮的点击事件
    public void onAddCommentButtonClick(View view) {
        // 获取评论内容
        EditText commentEditText = findViewById(R.id.commentEditText);
        String commentContent = commentEditText.getText().toString();

        if (!commentContent.isEmpty()) {
            commentContent = SensitiveWordFilter.Filter(commentContent);
            // 创建新的评论对象
            BlogComment newComment = new BlogComment(commentContent, MainActivity.getCurrentUsername()
                    , blogPost.getBlogKey(), -1);

            //Fixme: 将评论加入数据库中
            DBFunction.addCommentToDB(newComment);

            // 将评论添加到帖子
            blogPost.setComments(DBFunction.getCommentsByBlog(blogPost.getBlogKey()));
            // 刷新评论列表
            commentAdapter.clear();
            commentAdapter.addAll(DBFunction.getCommentsByBlog(blogPost.getBlogKey()));
            // 清空评论输入框
            commentEditText.getText().clear();
        }
    }


}
