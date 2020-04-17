package com.codull.demo.IOC;

/**
 * @program: Springboot_IOC_AOP
 * @description: IOC容器里bean的信号
 * @author: anthony1314
 * @create: 2020-02-19 04:02
 **/

public class BeanReference {
    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
