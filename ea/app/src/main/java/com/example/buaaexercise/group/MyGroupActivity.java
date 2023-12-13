package com.example.buaaexercise.group;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buaaexercise.MainActivity;
import com.example.buaaexercise.R;
import com.example.buaaexercise.backend.db._Group;
import com.example.buaaexercise.backend.dbFunction.DBFunction;

import java.util.ArrayList;
import java.util.List;

public class MyGroupActivity extends AppCompatActivity {

    public static RecyclerView myGroupRecyclerView;

    public static GroupAdapter myResultsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);

        myGroupRecyclerView = findViewById(R.id.myGroupRecyclerView);

        // Filter the groupItemList based on the search query
        List<_Group> myResults = DBFunction.findUserByName(MainActivity.getCurrentUsername()).getGroups();
        List<GroupItem> searchResults = new ArrayList<>();
        for (_Group group : myResults) {
            GroupItem groupItem = new GroupItem(group.getOrganizerName(), group.getJoinerLimit(),
                    group.getSport(), group.getDescription(), group.getPlanStartTime(),
                    group.getPlanEndTime(), group.getLocation(), group.getGroupKey());
            searchResults.add(groupItem);
        }

        // Display search results using the same adapter
        myResultsAdapter = new GroupAdapter(searchResults, 2);
        myGroupRecyclerView.setAdapter(myResultsAdapter);
        myGroupRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Set ItemDecoration for spacing
        int spacingDimen = R.dimen.spacing_between_items;
        int cornerRadiusDimen = R.dimen.corner_radius;
        myGroupRecyclerView.addItemDecoration(new SpaceItemDecoration(this, spacingDimen, cornerRadiusDimen));

        // Display a message if no results were found
        if (searchResults.isEmpty()) {
            Toast.makeText(this, "你暂时还没有参加活动哦~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<_Group> myResults = DBFunction.findUserByName(MainActivity.getCurrentUsername()).getGroups();
        List<GroupItem> searchResults = new ArrayList<>();
        for (_Group group : myResults) {
            GroupItem groupItem = new GroupItem(group.getOrganizerName(), group.getJoinerLimit(),
                    group.getSport(), group.getDescription(), group.getPlanStartTime(),
                    group.getPlanEndTime(), group.getLocation(), group.getGroupKey());
            searchResults.add(groupItem);
        }

        // Display search results using the same adapter
        myResultsAdapter = new GroupAdapter(searchResults, 2);
        myGroupRecyclerView.setAdapter(myResultsAdapter);
    }
}