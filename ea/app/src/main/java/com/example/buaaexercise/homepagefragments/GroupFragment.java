package com.example.buaaexercise.homepagefragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.buaaexercise.R;
import com.example.buaaexercise.backend.db.User;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.group.CreateGroupActivity;
import com.example.buaaexercise.group.GroupAdapter;
import com.example.buaaexercise.group.GroupItem;
import com.example.buaaexercise.group.RecommendGroupActivity;
import com.example.buaaexercise.group.SearchResultsGroupActivity;
import com.example.buaaexercise.group.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;

    public static GroupAdapter groupAdapter;

    static public List<GroupItem> groupItemList = new ArrayList<>();

    private boolean openRecently = true;


    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttentionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static GroupFragment newInstance(String param1) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Assuming you have a RecyclerView with the id "recyclerView" in your fragment_group.xml layout
        recyclerView = view.findViewById(R.id.recyclerView);

//        if(openRecently) {
//            groupItemList.add(new GroupItem("Sam", 10, "篮球🏀",
//                    "只因你太美", new Date(), new Date(), "篮球场", -1));
//            groupItemList.add(new GroupItem("Jamson", 4, "羽毛球🏸",
//                    "巴德明顿", new Date(), new Date(), "羽毛球场", -1));
//            openRecently = false;
//        }

        groupItemList = DBFunction.getAllGroups();

        groupAdapter = new GroupAdapter(groupItemList, 0);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        // 设置 ItemDecoration，添加适当的间距
        int spacingDimen = R.dimen.spacing_between_items; // 从 dimens.xml 获取间距
        int cornerRadiusDimen = R.dimen.corner_radius; // 从 dimens.xml 获取圆角半径
        recyclerView.addItemDecoration(new SpaceItemDecoration(requireContext(), spacingDimen, cornerRadiusDimen));

        FloatingActionButton fabCreateGroup = view.findViewById(R.id.fabCreateGroup);
        fabCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CreateGroupActivity
                Intent intent = new Intent(view.getContext(), CreateGroupActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton fabRecGroup = view.findViewById(R.id.fabRecGroup);

        fabRecGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CreateGroupActivity
                Intent intent = new Intent(view.getContext(), RecommendGroupActivity.class);
                startActivityForResult(intent, 2);
            }
        });


        // Set a click listener for the search button

        SearchView searchView = view.findViewById(R.id.searchView);
        ImageButton searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the search view
                String searchString = searchView.getQuery().toString().trim();

                Intent intent = new Intent(requireContext(), SearchResultsGroupActivity.class);
                intent.putExtra("searchString", searchString);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String newGroupString = data.getStringExtra("newGroup");
            GroupItem groupItem = new Gson().fromJson(newGroupString, GroupItem.class);
            //todo 加入数据库
            DBFunction.addGroupToDB(groupItem);
        }
        groupItemList = DBFunction.getAllGroups();

        Log.d("TAG", "GroupItem in Adapter: " + "-------------------------------");
        for (GroupItem groupItem : groupItemList) {
            Log.d("TAG", "GroupItem in Adapter: " + groupItem.getSport());
        }

        groupAdapter.updateAdapter(groupItemList);
        recyclerView.setAdapter(groupAdapter);
    }
}