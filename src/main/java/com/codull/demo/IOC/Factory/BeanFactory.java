package com.codull.demo.IOC.Factory;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * Bean工厂，是一个工厂(Factory)， 是Spring IOC容器的最顶层接口，
 * 它的作用是 管理Bean，即实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。
 * @author: anthony1314
 * @create: 2020-02-19 04:04
 **/
public interface BeanFactory {

    Object getBean(String beanId) throws Exception;//获取bean实例

}
