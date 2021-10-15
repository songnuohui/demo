package com.example.demo.service.impl;

import com.example.demo.common.abs.Test;
import com.example.demo.common.request.IpResult;
import com.example.demo.common.request.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SongNuoHui
 * @date 2021/9/14 10:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SonTest extends Test {

    private String name;
    private String gender;
    private Integer age;

    @Override
    public String testInterface() {
        String name=this.name;
        String gender=this.gender;
        Integer age = this.age;
        StringBuffer sb = new StringBuffer();
        sb.append(name).append(gender).append(age);
        return sb.toString();
    }

    @Override
    public String testMethod() {
        return super.testMethod();
    }

    @Override
    public User setCookie() {
        return null;
    }

    @Override
    public IpResult getIpDetail(String ip) {
        return null;
    }
}
