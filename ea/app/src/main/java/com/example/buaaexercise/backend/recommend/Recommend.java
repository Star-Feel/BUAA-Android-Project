package com.example.buaaexercise.backend.recommend;

import android.util.Log;

import com.example.buaaexercise.backend.db.ExerciseRecord;
import com.example.buaaexercise.backend.db.Hobby;
import com.example.buaaexercise.backend.db.Joiner_Group;
import com.example.buaaexercise.backend.db.User;
import com.example.buaaexercise.backend.db._Group;
import com.example.buaaexercise.backend.dbFunction.DBFunction;
import com.example.buaaexercise.group.GroupItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Recommend {

    /*
    TODO:组局推荐请调用该函数
    weather 为当日天气，包括：”沙尘暴" "小雪" "多云" "阴天" "晴天"
     */
    public static List<GroupItem> recommendGroup(String userName, String weather) {
        List<_Group> entireGroupLst = DBFunction.getAllGroupTables();
        User user = DBFunction.findUserByName(userName);
        assert user != null;
        List<ExerciseRecord> usrERLst = user.getExerciseRecordList();
        List<Hobby> usrHobbyLst = user.getHobbyList();

        // 推荐算法基于以下几个因素进行匹配和评估

        HashMap<_Group, Double> group_score = new HashMap<>();

        // 遍历所有活动组局
        for (_Group group : entireGroupLst) {
            // 检查活动组局是否符合用户的喜好
            double tmp = groupMatchesUserPreferences(group, usrHobbyLst);
            // 计算匹配分数
            double matchScore = calculateMatchScore(group, usrERLst, weather) + tmp;
            group_score.put(group, matchScore);
        }

        int limit = 20;
        List<GroupItem> groupToRec = new ArrayList<>();

        while (limit > 0 && !group_score.isEmpty()) {
            double maxScore = 0.0;
            _Group groupToSel = null;

            for(_Group group : group_score.keySet()) {
                if(group_score.get(group) == null) {
                    continue;
                }
                if(group_score.get(group) > maxScore) {
                    maxScore = group_score.get(group);
                    groupToSel = group;
                }
            }

            if (groupToSel == null) {
                break;
            }

            group_score.remove(groupToSel);
            GroupItem groupItem = new GroupItem(groupToSel.getOrganizerName(), groupToSel.getJoinerLimit(),
                    groupToSel.getSport(), groupToSel.getDescription(), groupToSel.getPlanStartTime(),
                    groupToSel.getPlanEndTime(), groupToSel.getLocation(), groupToSel.getGroupKey());
            //todo
            List<User> joiners = groupToSel.getJoiners();
            List<String> joinerNames = joiners.stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());
            groupItem.setJoiners(joinerNames);
            //----------------------------------------------------//
            groupToRec.add(groupItem);
            limit--;
        }
        return groupToRec;
    }

    // 检查活动组局是否符合用户的喜好
    private static double groupMatchesUserPreferences(_Group group, List<Hobby> usrHobbyLst) {
        // 检查活动组局与用户的爱好列表是否匹配
        // 返回 true 或 false
        for (Hobby hobby : usrHobbyLst) {
            if (hobby.getSportName().equals(group.getSport())) {
                return 10;
            }
        }
        return 0;
    }

    // 计算活动组局的匹配分数
    private static double calculateMatchScore(_Group group, List<ExerciseRecord> usrERLst, String weather) {
        // 根据用户的历史活动记录、天气情况等计算活动组局的匹配分数
        // 返回一个0到1之间的分数，表示匹配程度

        // 计算历史活动记录的匹配分数
        double historyMatchScore = calculateHistoryMatchScore(group, usrERLst);

        // 计算天气匹配分数
        double weatherMatchScore = calculateWeatherMatchScore(group, weather);

        // 综合考虑历史活动记录和天气的匹配程度
        double overallMatchScore = (historyMatchScore + weatherMatchScore) / 2;

        return overallMatchScore;
    }

    // 计算历史活动记录的匹配分数
    private static double calculateHistoryMatchScore(_Group group, List<ExerciseRecord> usrERLst) {
        int totalMatchCount = 0;

        for (ExerciseRecord record : usrERLst) {
            if (groupMatchesActivity(record.getSportName(), group)) {
                totalMatchCount++;
            }
        }

        // 计算匹配度得分
        double matchScore = (double) totalMatchCount / usrERLst.size();

        return matchScore;
    }

    // 检查历史活动记录中的活动是否与活动组局匹配
    private static boolean groupMatchesActivity(String type, _Group group) {
        // 在这里实现您的逻辑，比如判断活动的类型、难度、时间等是否符合活动组局的要求
        // 返回 true 或 false
        if (type.equals(group.getSport())) {
            return true;
        }
        return false;
    }

    // 计算天气匹配分数
    private static double calculateWeatherMatchScore(_Group group, String weather) {
        // 根据天气情况判断活动组局是否适合当前天气
        // 返回一个0到1之间的分数，表示匹配程度
        int sandstorm = 1;
        int snow = 10;
        int cloudy = 100;
        int emo = 1000;
        int clear = 10000;
        int gpVal = 0;
        int run = 1;
        int walk = 1;
        int billiard = 10;
        int swim = 10;
        int fit = 10;
        int tennis = 100;
        int volleyball = 100;
        int badminton = 1000;
        int tabletennis = 1000;
        int soccer = 1000;
        int td = 10000;

        switch (group.getSport()) {
            case "run" :
                gpVal = run;
                break;
            case "walk":
                gpVal = walk;
                break;
            case "billiard" :
                gpVal = billiard;
                break;
            case "swim":
                gpVal = swim;
                break;

            case "fit":
                gpVal = fit;
                break;
            case "tennis":
                gpVal = tennis;
                break;
            case "volleyball":
                gpVal = volleyball;
                break;
            case "badminton":
                gpVal = badminton;
                break;
            case "tabletennis":
                gpVal = tabletennis;
                break;
            case "soccer":
                gpVal = soccer;
                break;
            case "td":
                gpVal = td;
                break;
        }
        int weVal = 0;
        switch (weather) {
            case "沙尘暴":
                weVal = sandstorm;
                break;
            case "小雪":
                weVal = snow;
                break;
            case "多云":
                weVal = cloudy;
                break;
            case "阴":
                weVal = emo;
                break;
            case "晴":
                weVal = clear;
                break;
            default:
                weVal = emo;
        }
        double val = (double) weVal / gpVal;
        return Math.min(val, 1.0);
    }

    public static int getMonthDay(int month) {
        int day = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            return 28;
        }
        return 30;
    }


    /*
    TODO:健身计划推荐请调用该函数
    year month day 为当日时间
     */
    public static ArrayList<String> recommendFitPlan(String userName, int year, int month, int day) {
        double totalCalorie = 0;
        double sportTime = 0;
        double frequency = 0;

        Log.d("Recommend", "-----------------------------------0-----------------------------");


        for (int idx = 1; idx <= 10; idx++) {
            totalCalorie += DBFunction.getTotalCalorieInDate(userName, year, month, day);
            sportTime += DBFunction.getSportTimeInDate(userName, year, month, day);
            frequency += DBFunction.getFrequencyInDate(userName, year, month, day);

            day--;
            if (day == 0) {
                month--;
                if (month == 0) {
                    year--;
                    month = 12;
                }
                day = getMonthDay(month);
            }
        }

        Log.d("Recommend", "-----------------------------------1-----------------------------");


        sportTime /= 3600;
        totalCalorie /= 10;
        User user = DBFunction.findUserByName(userName);
        assert user != null;
        String sex = user.getSex();
        int height = user.getHeight();
        int weight = user.getWeight();

        Log.d("Recommend", "-----------------------------------2-----------------------------");
        Log.d("Recommend", " height: " + height + " weight: " + weight + " Calorie: " + totalCalorie);
        Log.d("Recommend", " sportTime: " + sportTime + " frequency: " + frequency + " sex: " + sex);

        ArrayList<String> planRecommendations = new ArrayList<>();

        if (sex == null) sex = "男";
        // 根据用户信息和统计数据进行健身计划推荐
        if (sex.equals("男")) {
            // 男性推荐计划
            if (height >= 180 && weight >= 70 && totalCalorie > 2500 && sportTime > 60 && frequency > 4) {
                planRecommendations.add("高强度有氧运动方案\n");
                planRecommendations.add("增肌训练计划\n");
                planRecommendations.add(fitPlan(3, sex));
            } else if (height >= 170 && weight >= 60 && totalCalorie > 2000 && sportTime > 45 && frequency > 3) {
                planRecommendations.add("中强度有氧运动方案\n");
                planRecommendations.add("核心训练计划\n");
                planRecommendations.add(fitPlan(2, sex));
            } else {
                planRecommendations.add("一般强度有氧运动方案");
                planRecommendations.add("保持健康训练计划");
                planRecommendations.add(fitPlan(1, sex));
            }
        } else {
            // 女性推荐计划
            if (height >= 165 && weight >= 50 && totalCalorie > 2000 && sportTime > 45 && frequency > 3) {
                planRecommendations.add("高强度有氧运动方案");
                planRecommendations.add("塑形训练计划");
                planRecommendations.add(fitPlan(3, sex));
            } else if (height >= 155 && weight >= 45 && totalCalorie > 1500 && sportTime > 30 && frequency > 2) {
                planRecommendations.add("中强度有氧运动方案");
                planRecommendations.add("减脂训练计划");
                planRecommendations.add(fitPlan(2, sex));
            } else {
                planRecommendations.add("一般强度有氧运动方案");
                planRecommendations.add("保持健康训练计划");
                planRecommendations.add(fitPlan(1, sex));
            }
        }
        Log.d("Recommend", "-----------------------------------3-----------------------------");


        return planRecommendations;
    }

    public static String fitPlan(int seq, String sex) {
        if (sex.equals("男") && seq == 3) {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "周一和周四（胸部和三头肌）：\n" +
                    "- 卧推（杠铃或哑铃）：3组，每组8-12次\n" +
                    "- 上斜卧推（杠铃或哑铃）：3组，每组8-12次\n" +
                    "- 坐姿飞鸟：3组，每组10-15次\n" +
                    "- 三头肌下压（杠铃或哑铃）：3组，每组10-15次\n" +
                    "- 颈后臂屈伸（杠铃）：3组，每组10-15次\n" +
                    "\n" +
                    "周二和周五（背部和二头肌）：\n" +
                    "- 拉力器划船：3组，每组8-12次\n" +
                    "- 引体向上：3组，每组8-12次\n" +
                    "- 哑铃弯举：3组，每组10-15次\n" +
                    "- 杠铃弯举：3组，每组10-15次\n" +
                    "- 集中弯举：3组，每组10-15次\n" +
                    "\n" +
                    "周三和周六（腿部和肩部）：\n" +
                    "- 腿举（杠铃或器械）：3组，每组8-12次\n" +
                    "- 腿弯举：3组，每组8-12次\n" +
                    "- 腿屈伸（坐姿或站姿）：3组，每组10-15次\n" +
                    "- 坐姿推举（哑铃或杠铃）：3组，每组8-12次\n" +
                    "- 俯身划船（杠铃或哑铃）：3组，每组10-15次\n" +
                    "\n" +
                    "在进行增肌训练时，注意以下几个要点：\n" +
                    "- 每组的重量应该适中，能够完成8-12次或10-15次的动作，但又不至于太轻。\n" +
                    "- 每组之间休息1-2分钟，以便肌肉得到恢复。\n" +
                    "- 注意正确的姿势和动作执行技巧，避免受伤。\n" +
                    "- 配合饮食，确保蛋白质和营养的摄入，提供肌肉生长所需的营养物质。\n" +
                    "\n";
        } else if (sex.equals("男") && seq == 2) {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "1. 快速步行/慢跑组合训练：\n" +
                    "   - 热身：快速步行5分钟\n" +
                    "   - 慢跑：跑步2分钟，以中等强度进行\n" +
                    "   - 快速步行：步行2分钟，以较快的速度进行\n" +
                    "   - 重复慢跑和快速步行的组合，每次间隔2分钟，共计30分钟\n" +
                    "   - 放松：缓慢步行5分钟\n" +
                    "\n" +
                    "2. 跳绳训练：\n" +
                    "   - 热身：快速步行5分钟\n" +
                    "   - 跳绳：连续跳绳2分钟，以中等强度进行\n" +
                    "   - 休息：休息30秒\n" +
                    "   - 重复跳绳和休息的组合，每次间隔2分钟，共计30分钟\n" +
                    "   - 放松：缓慢步行5分钟\n" +
                    "\n" +
                    "3. 室内有氧运动机器训练：\n" +
                    "   - 热身：快速步行5分钟\n" +
                    "   - 踏步机/跑步机/划船机等：选择一种室内有氧运动机器，进行中等强度的运动，持续30分钟\n" +
                    "   - 放松：缓慢步行5分钟\n" +
                    "\n" +
                    "4. 游泳训练：\n" +
                    "   - 热身：快速步行5分钟\n" +
                    "   - 游泳：进行中等强度的游泳，如自由泳、蛙泳或仰泳，持续30分钟\n" +
                    "   - 放松：缓慢步行5分钟\n" +
                    "\n" +
                    "在进行中强度有氧运动时，注意以下几个要点：\n" +
                    "- 保持适度的运动强度，能够感到轻度出汗和心率加快，但不至于过度疲劳。\n" +
                    "- 选择喜欢的运动方式，增加运动的趣味性和坚持性。\n" +
                    "- 根据自身的体力和适应能力，逐渐增加运动的时间和强度。\n" +
                    "- 每次运动前进行热身活动，以减少受伤的风险。\n" +
                    "- 运动后进行适当的拉伸放松活动，促进肌肉恢复和灵活性。\n" +
                    "\n";
        } else if (sex.equals("男") && seq == 1) {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "周一（上半身力量训练和有氧运动）：\n" +
                    "1. 杠铃卧推：3组，每组8-12次\n" +
                    "2. 引体向上（或下拉）：3组，每组8-12次\n" +
                    "3. 哑铃肩推：3组，每组10-15次\n" +
                    "4. 俯身哑铃划船：3组，每组10-15次\n" +
                    "5. 有氧运动：选择喜欢的有氧运动进行，如快走、慢跑或骑自行车，持续30分钟\n" +
                    "\n" +
                    "周二（下半身力量训练和灵活性训练）：\n" +
                    "1. 深蹲：3组，每组8-12次\n" +
                    "2. 哑铃硬拉：3组，每组8-12次\n" +
                    "3. 腿举：3组，每组10-15次\n" +
                    "4. 俯身臂屈伸：3组，每组10-15次\n" +
                    "5. 瑜伽或普拉提课程：进行45分钟的身体拉伸和放松训练\n" +
                    "\n" +
                    "周三（休息或进行轻度有氧运动或伸展）\n" +
                    "\n" +
                    "周四（全身力量训练和有氧运动）：\n" +
                    "1. 杠铃深蹲：3组，每组8-12次\n" +
                    "2. 杠铃卧推：3组，每组8-12次\n" +
                    "3. 高位下拉：3组，每组10-15次\n" +
                    "4. 哑铃弯举：3组，每组10-15次\n" +
                    "5. 有氧运动：选择喜欢的有氧运动进行，持续30分钟\n" +
                    "\n" +
                    "周五（有氧运动和核心训练）：\n" +
                    "1. 快走或慢跑：持续30分钟\n" +
                    "2. 仰卧起坐：3组，每组15-20次\n" +
                    "3. 仰卧交替抬腿：3组，每组15-20次\n" +
                    "4. 侧平板支撑：每侧持续30秒，共3组\n" +
                    "\n" +
                    "周六（瑜伽或伸展课程）：\n" +
                    "参加瑜伽、普拉提或伸展课程，进行全身的拉伸和放松训练，持续45-60分钟\n" +
                    "\n" +
                    "周日（休息）\n" +
                    "\n";
        } else if (sex.equals("女") && seq == 3) {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "周一（下半身力量训练）：\n" +
                    "1. 杠铃深蹲：3组，每组8-12次\n" +
                    "2. 哑铃弯举踮脚尖：3组，每组10-15次\n" +
                    "3. 哑铃臀桥：3组，每组10-15次\n" +
                    "4. 哑铃站姿单腿臀部后抬：3组，每组10-15次\n" +
                    "5. 静态蹲姿：保持姿势30秒，共3组\n" +
                    "\n" +
                    "周二（有氧运动和核心训练）：\n" +
                    "1. 快走或慢跑：持续30分钟\n" +
                    "2. 仰卧起坐：3组，每组15-20次\n" +
                    "3. 侧平板支撑：每侧持续30秒，共3组\n" +
                    "4. 腹部旋转：3组，每组10-15次\n" +
                    "\n" +
                    "周三（休息或进行轻度有氧运动或伸展）\n" +
                    "\n" +
                    "周四（上半身力量训练）：\n" +
                    "1. 俯卧撑（可选择膝盖着地）：3组，每组8-12次\n" +
                    "2. 哑铃肩推：3组，每组10-15次\n" +
                    "3. 哑铃划船：3组，每组10-15次\n" +
                    "4. 哑铃平板卧推：3组，每组8-12次\n" +
                    "5. 俯身哑铃飞鸟：3组，每组10-15次\n" +
                    "\n" +
                    "周五（有氧运动和全身塑形训练）：\n" +
                    "1. 快走或慢跑：持续30分钟\n" +
                    "2. 跳绳：连续跳绳2分钟，休息30秒，重复5次\n" +
                    "3. 哑铃弯举：3组，每组10-15次\n" +
                    "4. 哑铃深蹲并推举：3组，每组10-15次\n" +
                    "5. 哑铃俯身后抬腿：3组，每组10-15次\n" +
                    "\n" +
                    "周六（瑜伽或伸展课程）：\n" +
                    "参加瑜伽、普拉提或伸展课程，进行全身的拉伸和放松训练，持续45-60分钟\n" +
                    "\n" +
                    "周日（休息）\n" +
                    "\n";
        } else if (sex.equals("女") && seq == 2) {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "周一（有氧运动）：\n" +
                    "1. 快走或慢跑：持续30-45分钟，保持适中的心率区间\n" +
                    "2. 踏步机或椭圆机：进行20-30分钟的有氧运动，保持适度的强度\n" +
                    "\n" +
                    "周二（全身力量训练）：\n" +
                    "1. 杠铃深蹲：3组，每组8-12次\n" +
                    "2. 哑铃卧推：3组，每组8-12次\n" +
                    "3. 哑铃划船：3组，每组8-12次\n" +
                    "4. 哑铃站姿单腿臀部后抬：3组，每组10-15次\n" +
                    "5. 俯身哑铃飞鸟：3组，每组10-15次\n" +
                    "\n" +
                    "周三（高强度间歇训练）：\n" +
                    "1. 快速跳绳：连续跳绳30秒，休息15秒，重复10-15次\n" +
                    "2. 高抬膝：连续高抬膝30秒，休息15秒，重复10-15次\n" +
                    "3. 平板支撑：进行3组，每组持续30秒\n" +
                    "\n" +
                    "周四（休息或进行轻度有氧运动或伸展）\n" +
                    "\n" +
                    "周五（有氧运动）：\n" +
                    "1. 游泳：进行30-45分钟的游泳，保持适度的强度\n" +
                    "2. 自行车骑行：进行30-45分钟的骑行，保持适度的强度\n" +
                    "\n" +
                    "周六（全身力量训练）：\n" +
                    "1. 哑铃硬拉：3组，每组8-12次\n" +
                    "2. 杠铃卧推：3组，每组8-12次\n" +
                    "3. 哑铃肩推：3组，每组10-15次\n" +
                    "4. 哑铃弯举：3组，每组10-15次\n" +
                    "5. 俯身哑铃划船：3组，每组10-15次\n" +
                    "\n" +
                    "周日（休息）\n" +
                    "\n";
        } else {
            return "请根据个人情况和需求，进行适当的调整：\n" +
                    "\n" +
                    "周一（有氧运动和全身力量训练）：\n" +
                    "1. 慢跑或快走：持续30分钟\n" +
                    "2. 哑铃深蹲：3组，每组12-15次\n" +
                    "3. 俯卧撑（可选择膝盖着地）：3组，每组8-12次\n" +
                    "4. 哑铃划船：3组，每组12-15次\n" +
                    "\n" +
                    "周二（瑜伽或伸展课程）：\n" +
                    "参加瑜伽、普拉提或伸展课程，进行全身的拉伸和放松训练，持续45-60分钟\n" +
                    "\n" +
                    "周三（有氧运动和核心训练）：\n" +
                    "1. 游泳或自行车骑行：持续30分钟\n" +
                    "2. 仰卧起坐：3组，每组15-20次\n" +
                    "3. 侧平板支撑：每侧持续30秒，共3组\n" +
                    "\n" +
                    "周四（休息或进行轻度有氧运动或伸展）\n" +
                    "\n" +
                    "周五（有氧运动和下半身力量训练）：\n" +
                    "1. 踏步机或椭圆机：进行20-30分钟的有氧运动，保持适度的强度\n" +
                    "2. 哑铃弯举踮脚尖：3组，每组12-15次\n" +
                    "3. 哑铃臀桥：3组，每组12-15次\n" +
                    "\n" +
                    "周六（休息或进行户外活动，如散步、骑行等）\n" +
                    "\n" +
                    "周日（休息或进行伸展和放松训练）\n" +
                    "\n";
        }
    }

}
