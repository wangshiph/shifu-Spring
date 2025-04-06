package com.shifu.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: shifu-Spring
 * @description:
 * @author: wangshifu6
 * @create: 2025-04-06
 */
public class ShifuApplicationContext {
    private Class configClass;
    
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();

    public ShifuApplicationContext(Class appconfigClass) {
        this.configClass = appconfigClass;
        //扫描-》beanDefinition->beanDefinitionMap
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value();//扫描路径，扫描的不是java文件，而是扫描编译好的class文件
            path = path.replace(".","/");
            ClassLoader classLoader = ShifuApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getAbsolutePath();
                    System.out.println(fileName);
                    if(fileName.endsWith(".class")){
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        className = className.replace("/", ".");

                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            if(clazz.isAnnotationPresent(Component.class)){
                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                String beanName = componentAnnotation.value();
                                if(beanName.equals("")){
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }
                                BeanDefinition beanDefinition = getBeanDefinition(clazz);
                                beanDefinitionMap.put(beanName,beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        //实例化单例bean
        for(String beanName: beanDefinitionMap.keySet()){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if("singleton".equals(beanDefinition.getScope())){
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName,bean);
            }
        }
    }

    private static BeanDefinition getBeanDefinition(Class<?> clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setType(clazz);
        if (clazz.isAnnotationPresent(Scope.class)) {
            Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
            beanDefinition.setScope(scopeAnnotation.value());
        }else{
            beanDefinition.setScope("singleton");
        }
        return beanDefinition;
    }

    public Object getBean(String beanName){
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(null == beanDefinition){
            throw new RuntimeException("没有这个bean");
        }else {
            String scope = beanDefinition.getScope();
            if("singleton".equals(scope)){
                Object bean = singletonObjects.get(beanName);
                if(null == bean){
                    Object o = createBean(beanName,beanDefinition);
                    singletonObjects.put(beanName,o);
                }
                return bean;
            }else {
                //多例
                return createBean(beanName,beanDefinition);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
