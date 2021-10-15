package com.example.demo.controller.test;

import com.example.demo.common.request.IpResult;
import com.example.demo.common.request.User;
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
@Api(tags = "测试的接口")
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation("做一个测试")
    @RequestMapping(method = RequestMethod.GET, value = "/test.json")
    public RichResult<?> test() {
        return R.ok("hello world");
    }

    @ApiOperation("设置cookie")
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
