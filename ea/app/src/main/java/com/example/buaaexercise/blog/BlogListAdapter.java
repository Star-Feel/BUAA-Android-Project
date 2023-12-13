package com.example.buaaexercise.blog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;
import com.example.buaaexercise.backend.dbFunction.DBFunction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlogListAdapter extends ArrayAdapter<BlogPost> {
    public BlogListAdapter(Context context, List<BlogPost> blogPosts) {
        super(context, 0, blogPosts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlogPost blogPost = getItem(position);      //Fixme：改成从数据库存的帖子中取第 position 条

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.blog_list_item, parent, false);
        }

        ImageView userAvatarImageView = convertView.findViewById(R.id.userAvatarImageView);
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView authorTextView = convertView.findViewById(R.id.authorTextView);
        TextView postTimeTextView = convertView.findViewById(R.id.postTimeTextView);

        titleTextView.setText(blogPost.getTitle());
        authorTextView.setText(blogPost.getAuthor());

        // Fixme: 设置用户头像，可以从 blogPost 中获取用户信息
        Bitmap originMap = BitmapFactory.decodeResource(getContext().getResources() ,
                DBFunction.getUserHeadImage(blogPost.getAuthor()));
        Bitmap circleMap = Tools.getCircularBitmap(originMap);
        userAvatarImageView.setImageBitmap(circleMap);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String postTimeStr = sdf.format(blogPost.getPostTime());
        postTimeTextView.setText(postTimeStr);

        return convertView;
    }


}
