package spring;

import service.AppConfig;

import java.util.Objects;

/**
 * @author wangshiph
 * @create 2025-02-13 23:27
 */
public class MasterWangApplicationContext {
    private Class configClass;

    public MasterWangApplicationContext(Class configClass){
        this.configClass = configClass;
    }

    public Object getBean(String userSerivce) {
        return null;
    }
}
