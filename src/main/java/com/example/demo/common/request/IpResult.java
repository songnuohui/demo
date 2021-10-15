package com.example.demo.common.request;

import lombok.Data;

/**
 * @author SongNuoHui
 * @date 2021/9/1 17:30
 */
@Data
public class IpResult {
    private String code;//状态码
    private String message;//message
    private String ip;
    private Result result;//查询结果
}

@Data
class Result {
    private String enShort;
    private String enName;
    private String nation;
    private String province;
    private String city;
    private String district;//归属区县
    private String adcode;//归属地编码
    private String lat;//纬度
    private String lng;//经度
}

