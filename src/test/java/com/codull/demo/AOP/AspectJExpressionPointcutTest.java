package com.codull.demo.AOP;

import com.codull.demo.model.HelloService;
import com.codull.demo.model.HelloServiceImpl;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-20 01:00
 **/
public class AspectJExpressionPointcutTest {

    @Test
    public void testClassFilter() throws Exception {
        String expression = "execution(* com.codull.demo.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.matchers(HelloService.class);
        assertTrue(matches);
    }

    @Test
    public void testMethodMatcher() throws Exception {
        String expression = "execution(* com.codull.demo.model.*.sayHelloWorld(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        boolean matches = aspectJExpressionPointcut.matchers(
                HelloServiceImpl.class.getDeclaredMethod("sayHelloWorld"), HelloServiceImpl.class);
        assertTrue(matches);
    }

}