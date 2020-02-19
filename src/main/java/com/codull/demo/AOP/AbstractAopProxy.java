package com.codull.demo.AOP;



/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 21:43
 **/

public abstract class AbstractAopProxy implements AopProxy {

    protected AdvisedSupport advised;

    public AbstractAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

}