package com.codull.demo.IOC.Factory;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 04:04
 **/
public interface BeanFactoryAware {

    public void setBeanFactory(BeanFactory beanFactory) throws Exception;

}
