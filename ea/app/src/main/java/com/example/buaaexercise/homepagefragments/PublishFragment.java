package com.example.buaaexercise.homepagefragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.buaaexercise.R;
import com.example.buaaexercise.backend.db.User;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.blog.BlogDetailActivity;
import com.example.buaaexercise.blog.BlogListAdapter;
import com.example.buaaexercise.blog.BlogNewActivity;
import com.example.buaaexercise.blog.BlogPost;
import com.example.buaaexercise.blog.SearchResultsBlogActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PublishFragment extends Fragment {
    private ListView listView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static List<BlogPost> blogPosts = new ArrayList<>();
    private boolean openRecently = true;

    public static PublishFragment newInstance(String param1) {
        PublishFragment fragment = new PublishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.blogListView);

        if (openRecently) {
            LitePal.getDatabase();
//            new User("ikun");
//            new User("Mr. Strong");
//            new User("asadfg");
//            new User("current_user");
//            new User("123456");
//            List<User> users = DBFunction.getAllUsers();
//            for (User user : users) {
//                Log.d("TAG", user.toString() + "-----" + user.getBirthday() + "------" + user.getHeight() + "cm");
//            }

//            BlogPost blogPost = new BlogPost("CityWalk Beijing", "jimei", new Date(), "集美们，这个路线真是绝绝子，\\(^o^)/~", -1);
//            blogPost.setImageID(R.drawable._1);
//            DBFunction.addBlogToDB(blogPost);
            
            openRecently = false;
        }

        blogPosts = DBFunction.getAllBlogs();
        // 使用自定义适配器将帖子列表显示在ListView中
        BlogListAdapter adapter = new BlogListAdapter(getActivity(), blogPosts);
        listView.setAdapter(adapter);

        // 设置列表项点击事件，跳转到帖子详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // 获取点击的帖子
                final BlogPost selectedBlogPost = blogPosts.get(position);

                // 创建一个Intent，将帖子信息传递给帖子详情页面
                Intent intent = new Intent(getActivity(), BlogDetailActivity.class);
                intent.putExtra("blogPost", new Gson().toJson(selectedBlogPost));
                startActivity(intent);
            }
        });

        //新建帖子按钮
        FloatingActionButton addPostButton = view.findViewById(R.id.addPostButton);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮跳转到 NewPostActivity
                Intent intent = new Intent(getActivity(), BlogNewActivity.class);
                startActivityForResult(intent, 1); // 使用 startActivityForResult 启动活动，以便获取返回结果
            }
        });

        SearchView searchView = view.findViewById(R.id.searchPubView);
        ImageButton searchButton = view.findViewById(R.id.searchPubButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the search view
                String searchString = searchView.getQuery().toString().trim();

                Intent intent = new Intent(requireContext(), SearchResultsBlogActivity.class);
                intent.putExtra("searchString", searchString);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        blogPosts = DBFunction.getAllBlogs();
        // 更新 ListView 或适配器
        ArrayAdapter<BlogPost> adapter = (ArrayAdapter<BlogPost>) listView.getAdapter();
        adapter.clear();
        adapter.addAll(blogPosts);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish, container, false);
    }
}