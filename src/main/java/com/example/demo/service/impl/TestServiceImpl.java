package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.common.request.IpResult;
import com.example.demo.common.request.User;
import com.example.demo.common.exception.SuperCodeException;
import com.example.demo.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author SongNuoHui
 * @date 2021/9/1 9:21
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    protected HttpServletResponse response;

    private static final String appCode ="buy from the alicloud market ";//code

    private static final String host = "https://ips.market.alicloudapi.com";//地址

    private static  final String path = "/iplocaltion";//路径

    @Override
    public User setCookie() {
        User user = new User("song", "男", 22, "1234567890");
        int timeOut = 7200;
        addCookie("name", user.getUserName(), "/", timeOut);
        addCookie("gender", user.getGender(), "/", timeOut);
        return user;
    }

    @Override
    public IpResult getIpDetail(String ip) {
        String urlSend = host + path + "?ip=" + ip;   //拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appCode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                //将string转为对象
                return JSONObject.parseObject(json, IpResult.class);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    throw new SuperCodeException("AppCode错误");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    throw new SuperCodeException("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    throw new SuperCodeException("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    throw new SuperCodeException("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    throw new SuperCodeException("套餐包次数用完");
                } else {
                    log.error(error);
                    throw new SuperCodeException("参数名错误 或 其他错误");
                }
            }
        } catch (MalformedURLException e) {
            log.error("URL格式错误");
        } catch (UnknownHostException e) {
            log.error("URL地址错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new IpResult();
    }

    @Override
    public String newInterface() {
        return null;
    }

    /*
     * 读取返回结果
     */
    private String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), StandardCharsets.UTF_8);
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private void addCookie(String name, String value, String path, Integer maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setVersion(1);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        String domain = "localhost";
        cookie.setDomain(domain);
        try {
            //设置编码
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }
}
