package com.example.demo.controller.other;

import com.example.demo.utils.R;
import com.example.demo.utils.RichResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 获取该项目所有接口的详情
 * @author SongNuoHui
 * @date 2021/9/14 16:14
 */
@Api(tags = "自定义接口")
@RestController
public class TotalInterfaceController {
    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation("获取项目的所有接口")
    @GetMapping("/getParam.json")
    public RichResult<?> getParam() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 拿到Handler适配器中的全部方法
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo info : methodMap.keySet()) {
            assert info.getPatternsCondition() != null;
            Set<String> urlSet = info.getPatternsCondition().getPatterns();
            // 获取全部请求方式
            Set<RequestMethod> Methods = info.getMethodsCondition().getMethods();
            //System.out.println(Methods.toString());
            for (String url : urlSet) {
                // 加上自己的域名和端口号，就可以直接调用
                urlList.add("http://localhost:8888" + url);
            }
        }
        TreeSet<String> tree = new TreeSet(urlList);
        Map<Integer,TreeSet<String>> map = new HashMap<>();
        int size = tree.size();
        map.put(size,tree);
        return R.ok(map);
    }

}
