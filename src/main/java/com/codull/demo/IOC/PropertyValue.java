package com.codull.demo.IOC;

/**
 * @program: Springboot_IOC_AOP
 * @description: 属性值 对象
 @author: anthony1314
 * @create: 2020-02-19 03:25
 **/

public class PropertyValue {

    private final String name; // 属性的名字

    private final Object value;// 属性的对象

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
