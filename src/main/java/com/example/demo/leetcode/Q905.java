package com.example.demo.leetcode;

/**
 * @author SongNuoHui
 * @date 2022/4/28 15:48
 */
public class Q905 {
    public int[] sortArrayByParity(int[] nums) {
        int cnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]%2==0){
                int m = nums[cnt];
                nums[cnt]=nums[i];
                nums[i]=m;
                cnt++;
            }
        }
        return nums;
    }
}
