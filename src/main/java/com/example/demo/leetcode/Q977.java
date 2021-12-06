package com.example.demo.leetcode;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * 977 题
 *
 * @author SongNuoHui
 * @date 2021/12/2 10:57
 */
public class Q977 {
    /**
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     * 输入：nums = [-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     * 解释：平方后，数组变为 [16,1,0,9,100]
     * 排序后，数组变为 [0,1,9,16,100]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/squares-of-a-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public int[] sortedSquares(int[] nums) {
        int left=0,right=nums.length-1;
        int[] arr=new int [nums.length];
        int n=nums.length-1;
        while(left<=right){
            if(nums[left]*nums[left]>=nums[right]*nums[right]){
                arr[n]=nums[left]*nums[left];
                left++;
                n--;
            }else{
                arr[n]=nums[right]*nums[right];
                n--;
                right--;
            }
        }
        return arr;
        /*int[] arr=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            arr[i]=nums[i]*nums[i];
        }
        Arrays.sort(arr);
        return arr;*/
    }

    public static void main(String[] args) {
        int[] nums ={-7,-3,2,3,11};
        Q977 q977 = new Q977();
        System.out.println(Arrays.toString(q977.sortedSquares(nums)));
    }
}
