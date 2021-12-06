package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * @author SongNuoHui
 * @date 2021/12/6 17:37
 */
public class Q1816 {
    /**
     * 句子 是一个单词列表，列表中的单词之间用单个空格隔开，且不存在前导或尾随空格。每个单词仅由大小写英文字母组成（不含标点符号）。
     *
     * 例如，"Hello World"、"HELLO" 和 "hello world hello world" 都是句子。
     * 给你一个句子 s和一个整数 k ，请你将 s截断 ，使截断后的句子仅含 前 k 个单词。返回 截断 s后得到的句子
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/truncate-sentence
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * 输入：s = "Hello how are you Contestant", k = 4
     * 输出："Hello how are you"
     * 解释：
     * s 中的单词为 ["Hello", "how" "are", "you", "Contestant"]
     * 前 4 个单词为 ["Hello", "how", "are", "you"]
     * 因此，应当返回 "Hello how are you"
     */

    public String truncateSentence(String s, int k) {
        String[] s1 = s.split(" ");
        System.out.println(Arrays.toString(s1));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < k; i++) {
            sb.append(s1[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "Hello how are you Contestant";
        Q1816 q1816 = new Q1816();
        System.out.println(q1816.truncateSentence(s, 4));
    }
}
