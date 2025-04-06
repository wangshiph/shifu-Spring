package com.shifu.service;

import com.shifu.spring.Autowired;
import com.shifu.spring.BeanNameAware;
import com.shifu.spring.Component;
import com.shifu.spring.InitializingBean;
import com.shifu.spring.Scope;

/**
 * @program: shifu-Spring
 * @description:
 * @author: wangshifu6
 * @create: 2025-04-06
 */
@Component("userService")
@Scope("singleton")
public class UserService implements BeanNameAware, InitializingBean {
    @Autowired
    private OrderService orderService;
    private String beanName;
    public void test() {
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }
}
