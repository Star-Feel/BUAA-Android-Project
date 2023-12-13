package com.example.buaaexercise.blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buaaexercise.R;
import com.example.buaaexercise.homepagefragments.PublishFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultsBlogActivity extends AppCompatActivity {

    private ListView searchListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blog_results);

        String searchString = getIntent().getStringExtra("searchString");
        List<BlogPost> searchResults = filterBlogPosts(searchString);

        searchListView = findViewById(R.id.searchBlogListView);
        BlogListAdapter adapter = new BlogListAdapter(this, searchResults);
        searchListView.setAdapter(adapter);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 获取点击的帖子
                final BlogPost selectedBlogPost = PublishFragment.blogPosts.get(position);

                // 创建一个Intent，将帖子信息传递给帖子详情页面
                Intent intent = new Intent(SearchResultsBlogActivity.this, BlogDetailActivity.class);
                intent.putExtra("blogPost", new Gson().toJson(selectedBlogPost));
                startActivity(intent);
            }
        });

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "没有找到符合条件的活动", Toast.LENGTH_SHORT).show();
        }
    }

    private List<BlogPost> filterBlogPosts(String searchString) {

        if (!searchString.toLowerCase().contains("citywalk") && !searchString.toLowerCase().contains("city walk")) {
            List<BlogPost> filteredList = new ArrayList<>();
            String[] tokens = searchString.split(" ");
            for (BlogPost blogPost : PublishFragment.blogPosts) {
                String blogStr = new Gson().toJson(blogPost);
                for (String token : tokens) {
                    if (blogStr.toLowerCase().contains(token.toLowerCase())) {
                        filteredList.add(blogPost);
                        continue;
                    }
                }
            }
            return filteredList;
        } else {
            String[] tokens = searchString.split(" ");
            List<String> tokenIn = new ArrayList<>();
            for (String token : tokens) {
                if (token.equalsIgnoreCase("city") ||
                        token.equalsIgnoreCase("walk") ||
                        token.equalsIgnoreCase("citywalk")) {
                    continue;
                }
                tokenIn.add(token);
            }

            List<BlogPost> filteredList = new ArrayList<>();
            for (BlogPost blogPost : PublishFragment.blogPosts) {
                String blogStr = new Gson().toJson(blogPost);
                if (!blogStr.toLowerCase().contains("citywalk") &&
                        !blogStr.toLowerCase().contains("city walk")) {
                    continue;
                }
                for (String token : tokenIn) {
                    if (blogStr.toLowerCase().contains(token.toLowerCase())) {
                        filteredList.add(blogPost);
                        continue;
                    }
                }
            }
            return filteredList;
        }
    }
}
