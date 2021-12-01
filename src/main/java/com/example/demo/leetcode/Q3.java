package com.example.demo.leetcode;

/**
 * @author SongNuoHui
 * @date 2021/12/1 15:18
 */
public class Q3 {
    /**
     * 二分查找
     * 给定一个n个元素有序的（升序）整型数组nums 和一个目标值target ，写一个函数搜索nums中的 target，如果目标值存在返回下标，否则返回 -1。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/binary-search
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;


        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid-1;
            } else if (nums[mid] < target) {
                left = mid+1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int nums[] = {5};
        Q3 q3 = new Q3();
        System.out.println(q3.search(nums, 5));
    }
}
