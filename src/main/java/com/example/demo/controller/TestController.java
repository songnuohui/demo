package com.example.demo.controller;

import com.example.demo.common.entity.IpResult;
import com.example.demo.common.entity.User;
import com.example.demo.service.TestService;
import com.example.demo.utils.R;
import com.example.demo.utils.RichResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author SongNuoHui
 * @date 2021/8/20 15:09
 */
@Api("控制类")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation("test")
    @RequestMapping(method = RequestMethod.GET, value = "/test.json")
    public RichResult<?> test() {
        return R.ok("hello world");
    }

    @ApiOperation("set cookie")
    @GetMapping(value = "/set-cookie.json")
    public RichResult<?> setCookies() {
        User user = testService.setCookie();
        return R.ok(user);
    }

    @ApiOperation("查询ip")
    @PostMapping(value = "/ip-find")
    public RichResult<IpResult> getIpInfo(@RequestParam(value = "ip") String ip) {
        IpResult ipDetail = testService.getIpDetail(ip);
        return R.ok(ipDetail);
    }
}
