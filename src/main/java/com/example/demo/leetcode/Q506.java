package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 506 题
 * @author SongNuoHui
 * @date 2021/12/2 9:31
 */
public class Q506 {
    /**
     * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。所有得分都 互不相同 。
     * <p>
     * 运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。运动员的名次决定了他们的获奖情况：
     * <p>
     * 名次第 1 的运动员获金牌 "Gold Medal" 。
     * 名次第 2 的运动员获银牌 "Silver Medal" 。
     * 名次第 3 的运动员获铜牌 "Bronze Medal" 。
     * 从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
     * 使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/relative-ranks
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public String[] findRelativeRanks(int[] score) {
        String[] strings = new String[score.length];

        for (int i = 0; i < score.length; i++) {
            int p = 1;
            for (int j = 0; j < score.length; j++) {
                if (score[j] > score[i] && score[j] != score[i]) {
                    p++;
                }
            }
            if (p == 1) {
                strings[i] = "Gold Medal";
            } else if (p == 2) {
                strings[i] = "Silver Medal";
            } else if (p == 3) {
                strings[i] = "Bronze Medal";
            } else {
                strings[i] = Integer.toString(p);
            }

        }
        return strings;
    }

    public static void main(String[] args) {
        Q506 q506 = new Q506();
        int[] score = {5,4,3,2,1};
        System.out.println(Arrays.toString(q506.findRelativeRanks(score)));
    }
}
