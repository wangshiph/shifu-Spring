package com.shifu.service;

import com.shifu.spring.BeanPostProcessor;
import com.shifu.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: shifu-Spring
 * @description:
 * @author: wangshifu6
 * @create: 2025-04-07
 */
@Component
public class ShifuBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("studentService")) {
            System.out.println("111" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if (beanName.equals("studentService")) {
            Object proxyInstance = Proxy.newProxyInstance(ShifuBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                //proxy：代理对象，bean：本来对象
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
