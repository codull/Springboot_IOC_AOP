package com.codull.demo.IOC;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 04:02
 **/
public interface BeanDefinitionReader {
    public void loadBeanDefinitions(String location) throws Exception; //加载文件位置
}
