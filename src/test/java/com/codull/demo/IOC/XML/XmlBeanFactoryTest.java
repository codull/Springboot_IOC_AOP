package com.codull.demo.IOC.XML;

import com.codull.demo.model.HelloService;
import org.junit.jupiter.api.Test;


/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-20 00:20
 **/
public class XmlBeanFactoryTest {

    @Test
    public void getBean() throws Exception {
        String location = getClass().getClassLoader().getResource("spring-ioc.xml").getFile();
        XmlBeanFactory bf = new XmlBeanFactory(location);
//        Wheel wheel = (Wheel) bf.getBean("wheel");
//        System.out.println(wheel);
//        Car car = (Car) bf.getBean("car");
//        System.out.println(car.getWidth() + "m宽， " + car.getMoney());
        HelloService helloService = (HelloService) bf.getBean("helloService");
        helloService.sayHelloWorld();
    }

}