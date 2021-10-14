package com.example.demo.service;

import com.example.demo.common.entity.IpResult;
import com.example.demo.common.entity.User;

/**
 * @author SongNuoHui
 * @date 2021/8/20 15:14
 */

public interface TestService {

    /**
     * 将信息写入cookie
     */
    User setCookie();

    /**
     * 阿里 ip 定位查询
     */
    IpResult getIpDetail(String ip);

    String newInterface();
}
