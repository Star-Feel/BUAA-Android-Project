package com.example.buaaexercise.backend.db;

import androidx.annotation.NonNull;

import com.example.buaaexercise.R;

import org.litepal.LitePal;
import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class User extends LitePalSupport {
    //表中自动生成列名为id的自增key,此处拿出来是为了方便子表查询
    private long id;
    @Column(nullable = false, unique = true)
    private String username;        //primary
    private String password;
    private String registerTime;
    private int headImage = R.drawable.user; // 头像
    private String personalitySign; // 个性签名, 不超过20个字
    private int height = -1; //身高, 精确到cm, 所以是int
    private int weight = -1; //体重, 精确到kg, 所以是int
    private String birthday; //生日, yyyymmdd 的格式, 防止前导零的情况, 以字符串存
    private String sex; //性别,直接用“男”,"女"就行。 ~~男用"m"表示, 女用"f"表示~~

    //
    private List<Blog> blogList = new ArrayList<Blog>();
    private List<BlogCommentTable> blogCommentTableList = new ArrayList<BlogCommentTable>();
    private List<ExerciseRecord> exerciseRecordList = new ArrayList<ExerciseRecord>();
    private List<Joiner_Group> joinerGroupList = new ArrayList<>();
    public List<Hobby> getHobbyList = new ArrayList<>();
    public List<GroupCommentTable> groupCommentTables = new ArrayList<>();


    /*
        函数部分
     */
    public User() {
    }

    public User(String username) {
        User user = new User();
        user.setUsername(username); //        this.username = username;
        user.save();
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", headImage='" + headImage + '\'' +
                ", personalitySign='" + personalitySign + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }


    public void setDeletedFlag() {
        setUsername("用户已注销_" + String.valueOf(id));
    }


    //  外键约束
    public List<Blog> getBlogList() {
        return LitePal.where("user_id = ?", String.valueOf(id)).find(Blog.class);
    }

    public List<BlogCommentTable> getBlogCommentTableList() {
        return LitePal.where("user_id = ?", String.valueOf(id)).find(BlogCommentTable.class);
    }

    public List<ExerciseRecord> getExerciseRecordList() {
        return LitePal.where("user_id = ?", String.valueOf(id)).find(ExerciseRecord.class);
    }

    public List<_Group> getGroups() {
        List<Joiner_Group> joinerGroupList = LitePal.where("user_id = ?", String.valueOf(id)).find(Joiner_Group.class);
        List<_Group> groups = new ArrayList<>();
        for (Joiner_Group joinerGroup : joinerGroupList) {
            groups.add(joinerGroup.getGroup());
        }
        return groups;
    }

    public List<Hobby> getHobbyList() {
        return LitePal.where("user_id = ?", String.valueOf(id)).find(Hobby.class);
    }


    // 普通 get & set 方法

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
//        if (DBFunction.isUsernameExist(username)) {
//            Log.w(DBFunction.TAG, "用户名重复");
//            return false;
//        } else if (username == null) {
//            Log.w(DBFunction.TAG, "用户名为空");
//            return false;
//        } else {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }

    public String getPersonalitySign() {
        return personalitySign;
    }

    public void setPersonalitySign(String personalitySign) {
        this.personalitySign = personalitySign;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//    package com.android.model;
//
//import com.android.util.CollectionUtil;
//
//import org.litepal.crud.DataSupport;
//
//import java.util.List;
//
//    /**
//     * Title:
//     * Description:
//     * <p>
//     * Created by pei
//     * Date: 2017/11/23
//     */
//    public class Person extends DataBaseModel{
//
//        //person表中自动生成列名为id的自增key,此处拿出来是为了方便子表查询
//        private long id;
//
//        private String name;
//        private String sex;
//        private User user;
//
//        public long getId() {
//            return id;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getSex() {
//            return sex;
//        }
//
//        public void setSex(String sex) {
//            this.sex = sex;
//        }
//
//
//        public User getUser() {
//            //子表中会生成一个关联父表的id供父表查询，且字表中id生成符合规则："父表类名小写_id"
//            //若父表为Person类(父表中会自动生成一个id自增列)，子表为User类,则字表中会自动生成字段person_id对应父表中id，以供查询
//            String linkId=this.getClass().getSimpleName().toLowerCase();
//            List<User>list= DataSupport.where(linkId+"_id=?",String.valueOf(id)).find(User.class);
//            if(CollectionUtil.isEmpty(list)){
//                user= null;
//            }else{
//                user=list.get(0);
//            }
//            return user;
//        }
//
//        public void setUser(User user) {
//            //set的时候存储子表数据
//            user.save();
//            this.user = user;
//        }
//    }

}