package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.request.IpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        System.out.println("hello");
    }

    @org.junit.jupiter.api.Test
    void each() {
        List<Map<Integer, Integer>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            map.put(i, 10 + i);
            list.add(map);
        }
        list.forEach(
                p -> {
                    Map<Integer, Integer> map = new HashMap<>();
                    p.forEach(
                            (k, v) -> {
                                if (!k.equals(5)) {
                                    map.put(k, v);
                                }
                            }
                    );
                    List<Integer> li = new ArrayList<>(map.keySet());
                    li.forEach(l -> {
                        if (l != null) {
                            System.out.println(l);
                        }
                    });
                }
        );
    }

    @Value("${ip.address}")
    String ip;

    @org.junit.jupiter.api.Test
    void testBindValue(){
        System.out.println(ip);
    }

    @org.junit.jupiter.api.Test
    void jsonTest() throws Exception{
        String str="{\"code\":100,\"message\":\"success\",\"ip\":\"36.28.53.213\"," +
                "\"result\":{\"en_short\":\"CN\",\"en_name\":\"China\",\"nation\":" +
                "\"中国\",\"province\":\"浙江省\",\"city\":\"\",\"district\":\"\"," +
                "\"adcode\":\"330000\",\"lat\":\"30.26555\",\"lng\":\"120.1536\"}}\n";
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(jsonObject);
        IpResult ipResult = JSONObject.parseObject(str,IpResult.class);
        System.out.println(ipResult);
    }
}

