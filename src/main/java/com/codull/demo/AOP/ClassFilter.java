package com.codull.demo.AOP;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 21:21
 **/
public interface ClassFilter {
    Boolean matchers(Class beanClass) throws Exception;
}
