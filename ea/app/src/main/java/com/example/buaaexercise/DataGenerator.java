package com.example.buaaexercise;

import androidx.fragment.app.Fragment;

import com.example.buaaexercise.homepagefragments.GroupFragment;
import com.example.buaaexercise.homepagefragments.HomeFragment;
import com.example.buaaexercise.homepagefragments.PublishFragment;


public class DataGenerator {

    public static final int []mTabRes = new int[]{R.drawable.ic_home,R.drawable.ic_publish,R.drawable.ic_group,R.drawable.ic_my};
    // public static final int []mTabResPressed = new int[]{R.drawable.ic_tab_strip_icon_feed_selected,R.drawable.ic_tab_strip_icon_category_selected,R.drawable.ic_tab_strip_icon_pgc_selected,R.drawable.ic_tab_strip_icon_profile_selected};
    public static final String []mTabTitle = new String[]{"首页","帖子","组局","我的"};

    public static Fragment[] getFragments(String from) {
        Fragment fragments[] = new Fragment[4];
        fragments[0] = HomeFragment.newInstance(from);
        fragments[1] = PublishFragment.newInstance(from);
        fragments[2] = GroupFragment.newInstance(from);
        fragments[3] = Tools.newMyFragmentInstance(from);
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    /*
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content, null);
        ImageView tabIcon = view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }

     */
}