package com.example.buaaexercise.blog.sensitive_detect;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public final class SensitiveWordFilter {
    public static List wordList;
    private final static char replace = '*'; // 替代字符
    private final static char[] skip = new char[]{ // 遇到这些字符就会跳过，例如,如果"AB"是敏感词，那么"A B","A=B"也会被屏蔽
            '!', '*', '-', '+', '_', '=', ',', '.', '@'
    };

    /**
     * 敏感词替换
     *
     * @param text 待替换文本
     * @return 替换后的文本
     */
    public static String Filter(String text) {
        if (wordList == null || wordList.size() == 0) return text;
        char[] __char__ = text.toCharArray(); // 把String转化成char数组，便于遍历
        int i, j;
        Word word;
        boolean flag; // 是否需要替换
        for (i = 0; i < __char__.length; i++) { // 遍历所有字符
            char c = __char__[i];
            word = wordList.binaryGet(c); // 使用二分查找来寻找字符，提高效率
            if (word != null) { // word != null说明找到了
                flag = false;
                j = i + 1;
                while (j < __char__.length) { // 开始逐个比较后面的字符
                    if (skip(__char__[j])) { // 跳过空格之类的无关字符
                        j++;
                        continue;
                    }
                    if (word.next != null) { // 字符串尚未结束，不确定是否存在敏感词
                        /*
                        以下代码并没有使用二分查找，因为以同一个字符开头的敏感词较少
                        例如，wordList中记录了所有敏感词的开头第一个字，它的数量通常会有上千个
                        假如现在锁定了字符“T”开头的敏感词，而“T”开头的敏感词只有10个，这时使用二分查找的效率反而低于顺序查找
                         */
                        word = word.next.get(__char__[j]);
                        if (word == null) {
                            break;
                        }
                        j++;
                    } else { // 字符串已结束，存在敏感词汇
                        flag = true;
                        break;
                    }
                }
                if (word != null && word.next == null) {
                    flag = true;
                }
                if (flag) { // 如果flag==true，说明检测出敏感粗，需要替换
                    while (i < j) {
                        if (skip(__char__[i])) { // 跳过空格之类的无关字符，如果要把空格也替换成'*'，则删除这个if语句
                            i++;
                            continue;
                        }
                        __char__[i] = replace;
                        i++;
                    }
                    i--;
                }
            }
        }
        return new String(__char__);
    }

    /**
     * 加载敏感词列表
     *
     * @param words 敏感词数组
     */
    public static void loadWord(ArrayList<String> words) {
        if (words == null) return;
        char[] chars;
        List now;
        Word word;
        wordList = new List();
        for (String __word__ : words) {
            if (__word__ == null) continue;
            chars = __word__.toCharArray();
            now = wordList;
            word = null;
            for (char c : chars) {
                if (word != null) {
                    if (word.next == null) word.next = new List();
                    now = word.next;
                }
                word = now.get(c);
                if (word == null) word = now.add(c);
            }
        }
        sort(wordList);
    }

    /**
     * 加载敏感词txt文件，每个敏感词独占一行，不可出现空格，空行，逗号等非文字内容,必须使用UTF-8编码
     *
     * @param path txt文件的绝对地址
     */
    public static void loadWordFromFile(Context context, String path) {
        String encoding = "UTF-8";
        try {
            AssetManager assetManager = context.getAssets();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    assetManager.open(path), encoding
            );
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            ArrayList<String> list = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            loadWord(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对敏感词多叉树递增排序
     *
     * @param list 待排序List
     */
    private static void sort(List list) {
        if (list == null) return;
        Collections.sort(list); // 递增排序
        for (Word word : list) {
            sort(word.next);
        }
    }

    /**
     * 判断是否跳过当前字符
     *
     * @param c 待检测字符
     * @return true:需要跳过   false:不需要跳过
     */
    private static boolean skip(char c) {
        for (char c1 : skip) {
            if (c1 == c) return true;
        }
        return false;
    }
}
