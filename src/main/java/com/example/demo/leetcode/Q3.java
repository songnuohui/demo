package com.example.demo.leetcode;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SongNuoHui
 * @date 2021/12/6 17:48
 */
public class Q3 {
    /**
     * 3 题  滑动窗口
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度
     * <p>
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */

    public int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for(int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();

        int res = 0;
        int start = 0; // 窗口开始位置
        for(int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res   = Math.max(res, i - start + 1);
            last[index] = i;
        }

        return res;
    }

    public static void main(String[] args) {
        String s = "bbbbbb";
        Q3 q3 = new Q3();
        System.out.println(q3.lengthOfLongestSubstring(s));
    }
}
