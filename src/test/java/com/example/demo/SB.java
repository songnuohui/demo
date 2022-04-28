package com.example.demo;

import org.apache.commons.lang3.StringUtils;

/**
 * @author SongNuoHui
 * @date 2021/12/22 17:10
 */
public class SB {
    public static void main(String[] args) {
        String secondCode = "018113";
        if (StringUtils.isNotEmpty(secondCode)) {
            if (secondCode.charAt(3) == '1' || secondCode.charAt(3) == '3') {
                StringBuffer sb = new StringBuffer(secondCode);
                sb.replace(3, 4, "2");
                secondCode = sb.toString();
            }
        }
        System.out.println(secondCode);
    }
}
