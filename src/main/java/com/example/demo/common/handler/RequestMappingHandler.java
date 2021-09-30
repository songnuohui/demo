package com.example.demo.common.handler;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author SongNuoHui
 * @date 2021/8/20 19:05
 */
public class RequestMappingHandler extends RequestMappingHandlerMapping {
    @Override
    public void registerMapping(RequestMappingInfo mapping, Object handler, Method method) {
        super.registerMapping(mapping, handler, method);
    }
}
