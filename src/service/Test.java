package service;

import spring.MasterWangApplicationContext;

/**
 * @author wangshiph
 * @create 2025-02-13 23:26
 */
public class Test {
    public static void main(String[] args) {
        MasterWangApplicationContext applicationContext = new MasterWangApplicationContext(AppConfig.class);
        UserService userSerivce = (UserService) applicationContext.getBean("userSerivce");
    }
}
