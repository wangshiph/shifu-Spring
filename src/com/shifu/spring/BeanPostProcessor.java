package com.shifu.spring;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(String beanName, Object bean);

    Object postProcessAfterInitialization(String beanName, Object bean);

}
