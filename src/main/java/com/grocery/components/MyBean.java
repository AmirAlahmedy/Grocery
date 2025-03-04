package com.grocery.components;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MyBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Bean is initialized - afterPropertiesSet()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Bean is being destroyed - destroy()");
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct - init()");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy - preDestroy()");
    }
}
