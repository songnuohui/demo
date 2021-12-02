package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * 189 题
 *
 * @author SongNuoHui
 * @date 2021/12/2 11:15
 */
public class Q189 {
    /**
     * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 输入: nums = [1,2,3,4,5,6,7], k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右轮转 1 步: [7,1,2,3,4,5,6]
     * 向右轮转 2 步: [6,7,1,2,3,4,5]
     * 向右轮转 3 步: [5,6,7,1,2,3,4]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/rotate-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public void rotate(int[] nums, int k) {
        /*超出时间限制
        int length = nums.length;
        int mov = k % length;//移动的距离
        if (mov != 0) {
            while (k > 0) {
                int cur = nums[length - 1];
                //移动数组
                for (int i = length - 1; i > 0; i--) {
                    nums[i] = nums[i - 1];
                }
                nums[0] = cur;
                k--;
            }
        }*/
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = temp;
        }
    }

    public static void main(String[] args) {
        int nums[] = {1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        Q189 q189 = new Q189();
        q189.rotate(nums, k);
        System.out.println(Arrays.toString(nums));
    }
}
