package com.example.buaaexercise.group;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.Tools;
import com.example.buaaexercise.backend.db.User;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.blog.sensitive_detect.SensitiveWordFilter;
import com.example.buaaexercise.homepagefragments.GroupFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupDetailForRecActivity extends AppCompatActivity {

    GroupItem groupItem = null;

    private ArrayAdapter<GroupComment> commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        // 获取从上一个Activity传递的组局数据
        String groupItemString = getIntent().getStringExtra("groupItem");
        groupItem = new Gson().fromJson(groupItemString, GroupItem.class);

        // 显示组局详细信息
        TextView organizerTextView = findViewById(R.id.detailOrganizerTextView);
        organizerTextView.setText("组织者: " + groupItem.getOrganizer());

        TextView joinerLimitTextView = findViewById(R.id.detailJoinerLimitTextView);
        joinerLimitTextView.setText("已加入人数: " + groupItem.getJoiners().size()
                + "/" + groupItem.getJoinerLimit());

        TextView sportTextView = findViewById(R.id.detailSportTextView);
        sportTextView.setText(groupItem.getSport());

        // Add more TextViews and set their values based on groupItem
        RecyclerView joinersRecyclerView = findViewById(R.id.joinersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        joinersRecyclerView.setLayoutManager(layoutManager);
        List<JoinerItem> joiners = new ArrayList<>();

        for (String joinerName : groupItem.getJoiners()) {
            int image = DBFunction.getUserHeadImage(joinerName);
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), image);
            Bitmap circularBitmap = Tools.getCircularBitmap(originalBitmap);
            JoinerItem joinerItem = new JoinerItem(joinerName, circularBitmap);
            joiners.add(joinerItem);
        }

        JoinersAdapter joinersAdapter = new JoinersAdapter(joiners);
        joinersRecyclerView.setAdapter(joinersAdapter);

        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setText("活动简述: \n" + groupItem.getDescription());

        TextView planStartTimeTextView = findViewById(R.id.planStartTimeTextView);
        planStartTimeTextView.setText("开始时间: " + groupItem.getPlanStartTime());

        TextView planEndTimeTextView = findViewById(R.id.planEndTimeTextView);
        planEndTimeTextView.setText("结束时间: " + groupItem.getPlanEndTime());

        TextView locationTextView = findViewById(R.id.locationTextView);
        locationTextView.setText("活动地点: " + groupItem.getLocation());

        Button btnJoinGroup = findViewById(R.id.btnJoinGroup);
        Button btnCancelGroup = findViewById(R.id.btnCancelGroup);
        Button btnCompleteGroup = findViewById(R.id.btnCompleteGroup);

        //todo 获取当前用户的用户名，你可能需要从登录状态中获取
        String currentUser = MainActivity.getCurrentUsername();
        if (groupItem.hasJoiner(currentUser)) {
            btnJoinGroup.setText("💧 退出活动");
        } else if (groupItem.getJoiners().size() >= groupItem.getJoinerLimit()) {
            btnJoinGroup.setText("人数已满");
        }

        btnJoinGroup.setOnClickListener(new View.OnClickListener() {

            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onClick(View view) {

                // 判断用户是否已加入组局
                if (groupItem.hasJoiner(currentUser)) {
                    boolean removed = groupItem.removeJoiner(currentUser);

                    if (removed) {
                        // 退出成功，更新界面 todo
                        DBFunction.removeJoinerFromGroup(currentUser, groupItem.getGroupKey());
                        List<JoinerItem> joinerItems = new ArrayList<>();
                        for (User joiner : DBFunction.getJoinersFromGroup(groupItem.getGroupKey())) {
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), joiner.getHeadImage());
                            Bitmap circularBitmap = Tools.getCircularBitmap(originalBitmap);
                            JoinerItem joinerItem = new JoinerItem(joiner.getUsername(), circularBitmap);
                            joinerItems.add(joinerItem);
                        }
                        joinersAdapter.updateData(joinerItems);

                        if (Objects.equals(groupItem.getOrganizer(), "Wait for an organizer...")) {
                            organizerTextView.setText("组织者: " + groupItem.getOrganizer());
                        }
                        joinerLimitTextView.setText("已加入人数: " + groupItem.getJoiners().size()
                                + "/" + groupItem.getJoinerLimit());
                        // 更新按钮文字
                        btnJoinGroup.setText("🔥 加入活动");
                        btnCancelGroup.setVisibility(View.INVISIBLE);
                        btnCompleteGroup.setVisibility(View.INVISIBLE);
                        GroupFragment.groupItemList = DBFunction.getAllGroups();
//                        RecommendGroupActivity.recGroupAdapter.updateAdapter(GroupFragment.groupItemList);
//                        MyGroupActivity.searchResultsAdapter.updateAdapter(GroupFragment.groupItemList);
//                        SearchResultsGroupActivity.searchResultsAdapter.updateAdapter(GroupFragment.groupItemList);
                    } else {
                        // 退出失败，显示提示信息或执行相应操作
                        Toast.makeText(GroupDetailForRecActivity.this, "退出活动失败", Toast.LENGTH_SHORT).show();
                    }
                } else if (groupItem.getJoiners().size() >= groupItem.getJoinerLimit()) {
                    btnJoinGroup.setText("人数已满");
                    Toast.makeText(GroupDetailForRecActivity.this, "该活动人数已满", Toast.LENGTH_SHORT).show();
                } else {
                    boolean newOrg = false;
                    if (Objects.equals(groupItem.getOrganizer(), "Wait for an organizer...")) {
                        newOrg = true;
                    }

                    // 用户未加入，尝试加入组局 //todo 修改数据库
                    boolean joined = groupItem.addJoiner(currentUser);

                    if (joined) {
                        DBFunction.addJoinerToGroup(currentUser, groupItem.getGroupKey());
                        // 加入成功，更新界面
                        if (newOrg) {
                            organizerTextView.setText("组织者: " + groupItem.getOrganizer());
                            btnCancelGroup.setVisibility(View.VISIBLE);

                            btnCancelGroup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // TODO: 在数据库中删除该活动
                                    DBFunction.removeGroupFromDB(groupItem.getGroupKey());
                                    Toast.makeText(GroupDetailForRecActivity.this, "活动已取消", Toast.LENGTH_SHORT).show();
                                    List<GroupItem> newlist = DBFunction.getAllGroups();
                                    GroupFragment.groupAdapter.updateAdapter(newlist);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });

                            btnCompleteGroup.setVisibility(View.VISIBLE);
                            btnCompleteGroup.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // TODO: 在数据库中删除该活动
                                    DBFunction.removeGroupFromDB(groupItem.getGroupKey());
                                    Toast.makeText(GroupDetailForRecActivity.this, "活动已完成", Toast.LENGTH_SHORT).show();
                                    List<GroupItem> newlist = DBFunction.getAllGroups();
                                    GroupFragment.groupAdapter.updateAdapter(newlist);
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            });
                        }
                        joinerLimitTextView.setText("已加入人数: " + groupItem.getJoiners().size()
                                + "/" + groupItem.getJoinerLimit());

                        List<JoinerItem> joinerItems = new ArrayList<>();
                        for (User joiner : DBFunction.getJoinersFromGroup(groupItem.getGroupKey())) {
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), joiner.getHeadImage());
                            Bitmap circularBitmap = Tools.getCircularBitmap(originalBitmap);
                            JoinerItem joinerItem = new JoinerItem(joiner.getUsername(), circularBitmap);
                            joinerItems.add(joinerItem);
                        }
                        joinersAdapter.updateData(joinerItems);

                        // 更新按钮文字
                        btnJoinGroup.setText("💧 退出活动");
                        GroupFragment.groupItemList = DBFunction.getAllGroups();
//                        RecommendGroupActivity.recGroupAdapter.updateAdapter(GroupFragment.groupItemList);
//                        MyGroupActivity.searchResultsAdapter.updateAdapter(GroupFragment.groupItemList);
//                        SearchResultsGroupActivity.searchResultsAdapter.updateAdapter(GroupFragment.groupItemList);
                    } else {
                        // 人数已满，显示提示信息或执行相应操作
                        Toast.makeText(GroupDetailForRecActivity.this, "该活动人数已满", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (currentUser.equals(groupItem.getOrganizer())) {
            // 当前用户是组织者，显示取消活动按钮
            btnCancelGroup.setVisibility(View.VISIBLE);

            btnCancelGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 在数据库中删除该活动
                    DBFunction.removeGroupFromDB(groupItem.getGroupKey());
                    Toast.makeText(GroupDetailForRecActivity.this, "活动已取消", Toast.LENGTH_SHORT).show();
                    List<GroupItem> newlist = DBFunction.getAllGroups();
                    GroupFragment.groupAdapter.updateAdapter(newlist);
                    setResult(RESULT_OK);
                    finish();
                }
            });

            btnCompleteGroup.setVisibility(View.VISIBLE);
            btnCompleteGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 在数据库中删除该活动
                    DBFunction.removeGroupFromDB(groupItem.getGroupKey());
                    Toast.makeText(GroupDetailForRecActivity.this, "活动已完成", Toast.LENGTH_SHORT).show();
                    List<GroupItem> newlist = DBFunction.getAllGroups();
                    GroupFragment.groupAdapter.updateAdapter(newlist);
                    setResult(RESULT_OK);
                    finish();
                }
            });

        }


        List<GroupComment> groupComments = DBFunction.getAllCommentsOfGroup(groupItem.getGroupKey());

//        Log.d("-----", groupComments.size() + " " + groupComments);

        commentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupComments);
        ListView commentListView = findViewById(R.id.groupCommentListView);
        commentListView.setAdapter(commentAdapter);

        // 初始化添加评论的按钮
        ImageButton addCommentButton = findViewById(R.id.addGroupCommentButton);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCommentButtonClick(v);
            }
        });

    }

    public void onAddCommentButtonClick(View view) {
        // 获取评论内容
        EditText commentEditText = findViewById(R.id.groupCommentEditText);
        String commentContent = commentEditText.getText().toString();

        if (!commentContent.isEmpty()) {
            commentContent = SensitiveWordFilter.Filter(commentContent);
            // 创建新的评论对象
            GroupComment newComment = new GroupComment(commentContent, MainActivity.getCurrentUsername()
                    , groupItem.getGroupKey(), -1);

            //Fixme: 将评论加入数据库中
            DBFunction.addCommentToDBForGroup(newComment);
            // 刷新评论列表
            commentAdapter.clear();
            commentAdapter.addAll(DBFunction.getAllCommentsOfGroup(groupItem.getGroupKey()));
            // 清空评论输入框
            commentEditText.getText().clear();
        }
    }

}
