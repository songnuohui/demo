package com.example.demo.leetcode;

/**
 * 509 题
 *
 * @author SongNuoHui
 * @date 2021/12/2 15:43
 */
public class Q509 {
    /**
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。
     * 该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给你 n ，请计算 F(n) 。
     */
    public int fib(int n) {
       /* 递归
       if (n==0) return 0;
        if (n==1) return 1;
        return fib(n-1)+fib(n-2);*/

        //动态规划
        if (n < 2) {
            return n;
        }
        int p, q = 0, r = 1;
        for (int i = 2; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    public static void main(String[] args) {
        Q509 q509 = new Q509();
        System.out.println(q509.fib(10));
    }
}
