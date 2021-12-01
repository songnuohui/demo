package com.example.demo.leetcode;

/**
 * @author SongNuoHui
 * @date 2021/12/1 15:07
 */
public class Q2 {

    public int maxPower(String s) {
        int temp = 1;
        int max=1;
        for(int i=0 ;i<s.length()-1;i++){
            if(s.charAt(i)==s.charAt(i+1)){
                temp++;
                if (temp>max){
                    max = temp;
                }
            }else{
                temp=1;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        String s = "\"tourist\"";
        Q2 q2 = new Q2();
        System.out.println(q2.maxPower(s));
    }
}
