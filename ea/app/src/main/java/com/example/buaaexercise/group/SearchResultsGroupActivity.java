package com.example.buaaexercise.group;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buaaexercise.R;
import com.example.buaaexercise.homepagefragments.GroupFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsGroupActivity extends AppCompatActivity {

    public static RecyclerView searchResultsRecyclerView;
    public static GroupAdapter searchResultsAdapter;

    private String searchString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group_results);
        
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);
        // Retrieve the search query from the intent
        searchString = getIntent().getStringExtra("searchString");

        // Filter the groupItemList based on the search query
        List<GroupItem> searchResults = filterGroupItems(searchString);

        // Display search results using the same adapter
        searchResultsAdapter = new GroupAdapter(searchResults, 1);
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);
        searchResultsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Set ItemDecoration for spacing
        int spacingDimen = R.dimen.spacing_between_items;
        int cornerRadiusDimen = R.dimen.corner_radius;
        searchResultsRecyclerView.addItemDecoration(new SpaceItemDecoration(this, spacingDimen, cornerRadiusDimen));

        // Display a message if no results were found
        if (searchResults.isEmpty()) {
            Toast.makeText(this, "没有找到符合条件的活动", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<GroupItem> searchResults = filterGroupItems(searchString);

        // Display search results using the same adapter
        searchResultsAdapter = new GroupAdapter(searchResults, 1);
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);
    }

    private List<GroupItem> filterGroupItems(String searchString) {
        List<GroupItem> filteredList = new ArrayList<>();

        for (GroupItem groupItem : GroupFragment.groupItemList) {
            // Check if the groupItem's title contains the search string (case-insensitive)
            String groupStr = new Gson().toJson(groupItem);
            if (groupStr.toLowerCase().contains(searchString.toLowerCase())) {
                filteredList.add(groupItem);
            }
        }
        return filteredList;
    }
}