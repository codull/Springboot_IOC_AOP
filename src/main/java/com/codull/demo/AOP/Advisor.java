package com.codull.demo.AOP;

import org.aopalliance.aop.Advice;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 21:20
 **/
public interface Advisor {
    Advice getAdvice();
}
