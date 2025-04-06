package com.shifu.spring;

/**
 * @program: shifu-Spring
 * @description:
 * @author: wangshifu6
 * @create: 2025-04-06
 */
public class BeanDefinition {
    private Class type;

    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
