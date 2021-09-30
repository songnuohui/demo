package com.example.demo.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SongNuoHui
 * @date 2021/9/1 9:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private String gender;
    private Integer age;
    private String phone;
}
