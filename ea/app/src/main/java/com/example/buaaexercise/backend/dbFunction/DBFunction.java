package com.example.buaaexercise.backend.dbFunction;

import android.util.Log;

import com.example.buaaexercise.group.GroupComment;
import com.example.buaaexercise.homepagefragments.MyFragment.HistoryShowItem;
import com.example.buaaexercise.backend.db.*;
import com.example.buaaexercise.blog.BlogComment;
import com.example.buaaexercise.blog.BlogPost;
import com.example.buaaexercise.group.GroupItem;
import com.example.buaaexercise.MyStarActivity.StarShowItem;
import com.example.buaaexercise.sports.SportRecord;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DBFunction {
    public static String TAG = "from db";

    /**
     * 数据库内部调用Function
     */
    public static User findUserByName(String username) {
        List<User> users = LitePal.where("username = ?", username).find(User.class);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public static List<User> getAllUsers() { //    以列表形式返回 Blog 表中的所有内容（注意 BlogPost 内的属性）
        return LitePal.findAll(User.class);
    }
    /**
     *
     * 推荐部分调用Function
     *
     */
    /**
     * 前端内部调用Function
     */
    /*
    用户
     */
    // User表
    //注册一个用户，注册时间为registerDate。
    public static void addUser(String username, String password, String registerDate) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRegisterTime(registerDate);
        user.save();
    }


    //给一个非空的用户名和密码，返回在数据库中是否能匹配上。
    public static boolean isAllowLogin(String username, String password) {
        return LitePal.isExist(User.class, "username = ? and password = ?", username, password);
    }

    //判断用户名在数据库中是否已存在，用于保证不同用户的用户名唯一确定

    public static boolean isUsernameExist(String username) {
        return LitePal.isExist(User.class, "username = ?", username);
    }

    //判断参数用户名的真实密码是不是就是输入的inputPassword，是则返回true（用于在修改密码时验证自己知不知道自己现在的密码，不是的话不让修改）
    public static boolean isPasswordCorrect(String username, String inputPassword) {
        return isAllowLogin(username, inputPassword);
    }

    // 将数据库中username用户名对应的用户的密码修改为newPassword
    public static void changePassword(String username, String newPassword) {
        User updateUser = new User();
        updateUser.setPassword(newPassword);
        updateUser.updateAll("username = ?", username);
    }

    //将数据库中username用户名对应的用户彻底删掉，相当于是注销用户的操作。
//    public static void cancelUser(String username) { // BUG!!! 不知道咋回事，cancel完事只有，blog的对应外键值变为了null
//        // 标记删除
//        User user = findUserByName(username);
//        if (user != null) {
//            user.setDeletedFlag();
//            user.save();
//        }
//    }

    public static void cancelUser(String username) {
        long num = 0;
        User user = findUserByName(username);
        if (user != null) {
            User updateUser = new User();
            updateUser.setUsername("用户已注销_" + user.getId());
            updateUser.updateAll("username = ?", username);
        }
    }

    //获取到用户的出生日期, 务必以"yyyy-mm-dd"的格式返回
    public static String getUserBirthday(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getBirthday();
    }


    //拿到username的当前性别，返回"男"或者"女"
    public static String getGender(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getSex();
    }


    public static String getPersonalSign(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getPersonalitySign();
    }


    public static int getUserHeight(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getHeight();
    }


    public static int getUserWeight(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getWeight();
    }

    //    获取用户的头像id号
    public static int getUserHeadImage(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getHeadImage();
    }

    //拿到一个用户的注册时间
    public static String getUserRegisterTime(String username) {
        return LitePal.where("username = ?", username).find(User.class).get(0).getRegisterTime();
    }

    ////set

    //将username用户的性别设置为newGender(只会为"男"或者"女")
    public static void setUserGender(String username, String newGender) {
        User updateUser = new User();
        updateUser.setSex(newGender);
        updateUser.updateAll("username = ?", username);
    }


    //将用户的出生日期设置为参数date（是类似于“2023-11-28”这样的格式的，不需要再进行格式解析，直接set即可）
    public static void setUserBirthday(String username, String date) {
        User updateUser = new User();
        updateUser.setBirthday(date);
        updateUser.updateAll("username = ?", username);
    }

    //    设置一个用户的个性签名为newSign
    public static void setPersonalSign(String username, String newSign) {
        User updateUser = new User();
        updateUser.setPersonalitySign(newSign);
        updateUser.updateAll("username = ?", username);
    }

    //    设置用户的身高
    public static void setUserHeight(String username, int newHeight) {
        User updateUser = new User();
        updateUser.setHeight(newHeight);
        updateUser.updateAll("username = ?", username);
    }

    //    设置用户的体重
    public static void setUserWeight(String username, int newWeight) {
        User updateUser = new User();
        updateUser.setWeight(newWeight);
        updateUser.updateAll("username = ?", username);
    }

    //    将用户的头像以id的形式设置为参数imageId
    public static void setHeadImage(String username, int imageId) {
        User updateUser = new User();
        updateUser.setHeadImage(imageId);
        updateUser.updateAll("username = ?", username);
    }

    /*
     收藏Star
      */

    //  添加用户对某项运动的收藏
    public static void addStar(String username, String sportName) {
        Hobby hobby = new Hobby();
        hobby.setUserByName(username);
        hobby.setSportName(sportName);
        hobby.save();
    }

    //    取消用户对某项运动的收藏，保证这个sportName在收藏列表里
    public static void cancelStar(String username, String sportName) {
        User user = findUserByName(username);
        if (user != null) {
            LitePal.deleteAll(Hobby.class, "user_id=? and sportname=?", String.valueOf(user.getId()), sportName);
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，cancelStar失败");
        }
    }

    //    查询用户是否对某项运动收藏了
    public static boolean ifStared(String username, String sportName) {
        User user = findUserByName(username);
        if (user != null) {
            return LitePal.isExist(Hobby.class, "user_id=? and sportname=?", String.valueOf(user.getId()), sportName);
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，ifStared失败");
            return false;
        }
    }

    //    StarShowItem类在MyStarActivity中有定义，仅需要用构造函数对itemTitle（运动的名称）和itemCount（这项运动运动的次数）
    //    进行初始化即可，构造函数的后两个参数位可以随便传，前端可以把他们初始化anandor
    public static List<StarShowItem> getUserStars(String username) {
        User user = findUserByName(username);
        if (user != null) {
            List<StarShowItem> starShowItems = new ArrayList<>();
            List<Hobby> hobbyList = LitePal.where("user_id=?", String.valueOf(user.getId())).find(Hobby.class);
            for (Hobby hobby : hobbyList) {
                String sportName = hobby.getSportName();
                int itemCount = LitePal.where("user_id=? and sportname=?", String.valueOf(user.getId()), sportName).count(ExerciseRecord.class);
                StarShowItem starShowItem = new StarShowItem(sportName, itemCount, -1, "null");
                starShowItems.add(starShowItem);
            }
            return starShowItems;
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，getUserStars失败");
            return new ArrayList<>();
        }
    }

    /*
    博客
     */
    // Blog表
    public static List<BlogPost> getAllBlogs() { //    以列表形式返回 Blog 表中的所有内容（注意 BlogPost 内的属性）
        List<Blog> blogs = getAllBlogTables();
        List<BlogPost> blogPosts = new ArrayList<>();
        for (Blog blog : blogs) {
            BlogPost blogPost = new BlogPost(blog.getTitle(), blog.getAuthor().getUsername(), blog.getPostTime(), blog.getContent(), blog.getBlogKey());
            blogPost.setImageID(blog.getImageId());
            blogPosts.add(blogPost);
        }
        return blogPosts;
    }

    public static List<Blog> getAllBlogTables() {
        return LitePal.order("postTime desc").find(Blog.class);
    }

    public static List<BlogComment> getCommentsByBlog(int blogKey) { //    以列表形式返回 Blog 表中的所有内容（注意 BlogPost 内的属性）
        List<BlogCommentTable> BlogCommentTables = getBlogCommentTablesByBlog(blogKey);
        List<BlogComment> BlogComments = new ArrayList<>();
        for (BlogCommentTable blogCommentTable : BlogCommentTables) {
            BlogComment BlogComment = new BlogComment(blogCommentTable.getText(), blogCommentTable.getAuthor().getUsername(), blogKey, blogCommentTable.getCommentKey());
            BlogComments.add(BlogComment);
        }
        return BlogComments;
    }

    public static List<BlogCommentTable> getBlogCommentTablesByBlog(int blogKey) {
        return LitePal.where("blog_id = ?", String.valueOf(blogKey)).order("postTime desc").find(BlogCommentTable.class);
    }

    public static void addBlogToDB(BlogPost blogPost) { //    将一个博客加入数据库（注意 BlogPost 内提供的接口）
        Blog blog = new Blog();
        blog.setTitle(blogPost.getTitle());
        boolean foreignKeyOK = blog.setAuthorByName(blogPost.getAuthor());
        blog.setPostTime(blogPost.getPostTime());
        blog.setContent(blogPost.getContent());
        blog.setImageId(blogPost.getImageID());
        if (foreignKeyOK) {
            blog.save();
        }
    }

    public static void addCommentToDB(BlogComment blogComment) { //   将一条评论加入数据库（注意 BlogComment 内提供的接口）
        BlogCommentTable blogCommentTable = new BlogCommentTable();
        blogCommentTable.setText(blogComment.getText());
        boolean foreignKeyOK1 = blogCommentTable.setAuthorByName(blogComment.getAuthor());
        blogCommentTable.setPostTime(blogComment.getPostTime());
        boolean foreignKeyOK2 = blogCommentTable.setBlogById(blogComment.getBelongKey());
        if (foreignKeyOK1 & foreignKeyOK2) {
            blogCommentTable.save();
        }
    }

    /*
    组局
     */

    ////group 评论
    public static List<GroupComment> getAllCommentsOfGroup(int _groupKey) { //    以列表形式返回 _Group 表中的所有内容（注意 _GroupPost 内的属性）
        List<GroupCommentTable> groupCommentTables = getGroupCommentTablesBy_Group(_groupKey);
        List<GroupComment> groupComments = new ArrayList<>();
        for (GroupCommentTable _groupCommentTable : groupCommentTables) {
            GroupComment groupComment = new GroupComment(_groupCommentTable.getText(), _groupCommentTable.getAuthor().getUsername(), _groupKey, _groupCommentTable.getCommentKey());
            groupComments.add(groupComment);
        }
        return groupComments;
    }

    public static List<GroupCommentTable> getGroupCommentTablesBy_Group(int _groupKey) {
        return LitePal.where("_group_id = ?", String.valueOf(_groupKey)).order("postTime desc").find(GroupCommentTable.class);
    }

    public static void addCommentToDBForGroup(GroupComment _groupComment) { //   将一条评论加入数据库（注意 GroupComment 内提供的接口）
        GroupCommentTable _groupCommentTable = new GroupCommentTable();
        _groupCommentTable.setText(_groupComment.getText());
        boolean foreignKeyOK1 = _groupCommentTable.setAuthorByName(_groupComment.getAuthor());
        _groupCommentTable.setPostTime(_groupComment.getPostTime());
        boolean foreignKeyOK2 = _groupCommentTable.set_GroupById(_groupComment.getBelongKey());
        if (foreignKeyOK1 & foreignKeyOK2) {
            _groupCommentTable.save();
        }
    }
    ////

    //    返回所有的组局项目
    public static List<GroupItem> getAllGroups() {
        List<_Group> groups = getAllGroupTables();
        List<GroupItem> groupItems = new ArrayList<>();
        for (_Group group : groups) {
            GroupItem groupItem = new GroupItem(group.getOrganizerName(), group.getJoinerLimit(), group.getSport(), group.getDescription(), group.getPlanStartTime(), group.getPlanEndTime(), group.getLocation(), group.getGroupKey());
            List<User> joiners = group.getJoiners();
            List<String> joinerNames = joiners.stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());
            groupItem.setJoiners(joinerNames);
            groupItems.add(groupItem);
        }
        return groupItems;
    }

    public static List<_Group> getAllGroupTables() {
        return LitePal.order("createTime desc").find(_Group.class);
    }

    //    返回对应组局的参与者列表
    public static List<User> getJoinersFromGroup(int groupKey) {
        _Group group = LitePal.find(_Group.class, groupKey);
        if (group != null) {
            return group.getJoiners();
        } else {
            Log.w(DBFunction.TAG, "不存在该组局 , getJoinersFromGroup失败");
            return new ArrayList<User>();
        }
    }


    //    将新建的组局添加到数据库
    public static void addGroupToDB(GroupItem groupItem) {
        _Group group = new _Group();
        group.setJoinerLimit(groupItem.getJoinerLimit());
        group.setSport(groupItem.getSport());
        group.setDescription(groupItem.getDescription());
        group.setPlanStartTime(groupItem.getPlanStartTimeForDB());
        group.setPlanEndTime(groupItem.getPlanEndTimeForDB());
        group.setLocation(groupItem.getLocation());
        group.setCreateTime(groupItem.getCreateTimeForDB());
        // 组织者
        boolean foreignKeyOK = group.addJoinerByName(groupItem.getOrganizer(), true);
        //
        if (foreignKeyOK) {
            group.save();
        }
    }


    //    为对应组局添加参与者
    public static void addJoinerToGroup(String joiner, int groupKey) {
        _Group group = LitePal.find(_Group.class, groupKey);
        if (group != null) {
            // 没有组织者<==>人数为0。当且仅当这种情况，才会addOrganizer
            boolean foreignKeyOK = group.addJoinerByName(joiner, group.getCurrentPersonNum() == 0);
            if (!foreignKeyOK) {
                Log.w(DBFunction.TAG, "不存在joiner用户 , addJoinerToGroup失败");
            }
        } else {
            Log.w(DBFunction.TAG, "不存在该组局groupKey , addJoinerToGroup失败");
        }

    }


    //    将参与者移出组局
    public static void removeJoinerFromGroup(String joinerName, int groupKey) {
        User joiner = findUserByName(joinerName);
        List<Joiner_Group> joiner_groups = LitePal.where("user_id=? and _group_id=?", String.valueOf(joiner.getId()), String.valueOf(groupKey)).find(Joiner_Group.class);
        if (!joiner_groups.isEmpty()) {     //存在groupKey--joinerName记录
            Joiner_Group joiner_group = joiner_groups.get(0);
            boolean isJoinerOrganizer = joiner_group.getIsOrganizer() == 1;
            //  执行remove
            LitePal.deleteAll(Joiner_Group.class, "user_id=? and _group_id=?", String.valueOf(joiner.getId()), String.valueOf(groupKey));
            //  如果joiner是group的Organizer
            if (isJoinerOrganizer) {
                List<Joiner_Group> joiner_groups1 = LitePal.where("_group_id=?", String.valueOf(groupKey)).find(Joiner_Group.class);
                if (!joiner_groups1.isEmpty()) {  // if(还有人)
                    joiner_groups1.get(0).setIsOrganizer(1);
                }
            }
        } else {
            Log.w(DBFunction.TAG, "不存在groupKey--joinerName , removeJoinerFromGroup失败");
        }
    }

    //    将对应组局移除
    public static void removeGroupFromDB(int groupKey) {
        LitePal.delete(_Group.class, groupKey);
    }





    /*
    运动记录
     */

    //将运动记录添加到数据库中
    public static void addRecordToDB(String username, SportRecord sportRecord) {
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        exerciseRecord.setUserByName(username);
        exerciseRecord.setSportName(sportRecord.getSportName());
        exerciseRecord.setSumTime(sportRecord.getSumTime());
        exerciseRecord.setEndTime(sportRecord.getEndTime());
        exerciseRecord.setScore(sportRecord.getScore());
        exerciseRecord.setPartner(sportRecord.getPartner());
        exerciseRecord.setNote(sportRecord.getNote());
        exerciseRecord.setRate(sportRecord.getRate());
        exerciseRecord.save();
    }

    //  获得某一天总的运动时间（如果这一天没有记录，返回0）
    public static int getSportTimeInDate(String username, int year, int month, int day) {
        User user = findUserByName(username);
        if (user != null) {
            return LitePal.where("user_id=? and year=? and month=? and day=?", String.valueOf(user.getId()), String.valueOf(year), String.valueOf(month), String.valueOf(day)).sum(ExerciseRecord.class, "sumTime", int.class);
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，getSportTimeInDate失败");
            return 0;
        }
    }

    //  获得某一天总的运动次数（如果这一天没有记录，返回0）
    public static int getFrequencyInDate(String username, int year, int month, int day) {
        User user = findUserByName(username);
        if (user != null) {
            return LitePal.where("user_id=? and year=? and month=? and day=?", String.valueOf(user.getId()), String.valueOf(year), String.valueOf(month), String.valueOf(day)).count(ExerciseRecord.class);
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，getFrequencyInDate失败");
            return 0;
        }
    }

    public static HashMap<String, Double> sportCalories = new HashMap<>();

    static {
        sportCalories.put("run", 10.5);
        sportCalories.put("walk", 4.8);
        sportCalories.put("fit", 7.0);
        sportCalories.put("td", 7.0); //td 暂设为7.0
        sportCalories.put("swim", 7.2);
        sportCalories.put("basketball", 6.9);
        sportCalories.put("tennis", 7.3);
        sportCalories.put("tabletennis", 5.5);
        sportCalories.put("billiard", 3.8);
        sportCalories.put("badminton", 5.5);
        sportCalories.put("volleyball", 6.9);
        sportCalories.put("soccer", 7.2);
        sportCalories.put("frisbee", 7.0);
    }

    //  获得某一天总的运动次数（如果这一天没有记录，返回0）
    public static double getTotalCalorieInDate(String username, int year, int month, int day) {
        User user = findUserByName(username);
        if (user != null) {
            double totalCalorie = 0.0;
            for (String sportName : sportCalories.keySet()) {
                totalCalorie += sportCalories.get(sportName) * LitePal.where("user_id=? and year=? and month=? and day=? and sportname=?", String.valueOf(user.getId()), String.valueOf(year), String.valueOf(month), String.valueOf(day), sportName).sum(ExerciseRecord.class, "sumTime", int.class) / 60;
            }
            return totalCalorie;
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，getTotalCalorieInDate失败");
            return 0;
        }
    }

    //    【运动的logo的id号，后端传为-1】
//        获取用户在日期date的历史记录。HistoryShowItem的数据结构在MyFragment.java文件中有定义，返回的每一个HistoryShowItem应如此形式：
//        new HistoryShowItem("跑步", xx, "12:30", "189min", "练习考试")。除了第二项xx是对应运动的logo的id号，其中时间要精确到分钟，
//        不要年月日，只要“时：分”，然后运动总时长要带单位min, 以min单位。如果不存在历史记录要返回一个空列表而不是null空指针
    public static ArrayList<HistoryShowItem> getUserHistory(String username, String dateString) {
        User user = findUserByName(username);
        if (user != null) {
            ArrayList<HistoryShowItem> historyShowItems = new ArrayList<>();
            LocalDate date = LocalDate.parse(dateString); // 解析日期字符串
            int year = date.getYear(); // 获取年份
            int month = date.getMonthValue(); // 获取月份
            int day = date.getDayOfMonth(); // 获取日期
            List<ExerciseRecord> exerciseRecordList = LitePal.where("user_id=? and year=? and month=? and day=?", String.valueOf(user.getId()), String.valueOf(year), String.valueOf(month), String.valueOf(day)).find(ExerciseRecord.class);
            for (ExerciseRecord exerciseRecord : exerciseRecordList) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); // 设置时间格式
                String timeString = dateFormat.format(exerciseRecord.getEndTime()); // 格式化为字符串
                String totalTimeString = String.valueOf(exerciseRecord.getSumTime());
                HistoryShowItem historyShowItem = new HistoryShowItem(exerciseRecord.getSportName(), -1, timeString, totalTimeString, exerciseRecord.getNote());
                historyShowItems.add(historyShowItem);
            }
            return historyShowItems;
        } else {
            Log.w(DBFunction.TAG, "不存在username用户，getTotalCalorieInDate失败");
            return new ArrayList<>();
        }
    }
}


//    private void setSports( {
//        sportName.add("run");//跑步
//        sportName.add("wak");//徒步
//        sportName.add("fit");//健身
//        sportName.add("td");//Td
//        sportName.add("swim");//游泳
//        sportName.add("basketbal"); //篮球
//        sportName.add("tennis");//网球
//        sportName.add("tabletennis"); //兵乓球
//        sportName.add("bitliard");//桌球
//        sportName.add("badminton"); //羽毛球
//        sportName.add("votleybal"); //排球
//        sportName.add("soccer");//足球
//
//
//    }


