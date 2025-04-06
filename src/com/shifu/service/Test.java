package com.shifu.service;

import com.shifu.spring.ShifuApplicationContext;

/**
 * @program: shifu-Spring
 * @description:
 * @author: wangshifu6
 * @create: 2025-04-06
 */
public class Test {
    public static void main(String[] args) {
        ShifuApplicationContext shifuApplicationContext = new ShifuApplicationContext(AppConfig.class);

        //UserService userService = (UserService) shifuApplicationContext.getBean("userService");
        System.out.println(shifuApplicationContext.getBean("userService"));
        System.out.println(shifuApplicationContext.getBean("userService"));
    }
}
