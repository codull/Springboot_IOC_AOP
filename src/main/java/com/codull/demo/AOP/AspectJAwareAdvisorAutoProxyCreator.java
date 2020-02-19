package com.codull.demo.AOP;

import com.codull.demo.IOC.BeanPostProcessor;
import com.codull.demo.IOC.Factory.BeanFactory;
import com.codull.demo.IOC.Factory.BeanFactoryAware;
import com.codull.demo.IOC.XML.XmlBeanFactory;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.List;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 04:26
 **/

public class AspectJAwareAdvisorAutoProxyCreator  implements BeanPostProcessor, BeanFactoryAware {

    private XmlBeanFactory xmlBeanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        if (bean instanceof AspectJExpressionPointcutAdvisor) {
            return bean;
        }
        if (bean instanceof MethodInterceptor) {
            return bean;
        }

        // 1.  从 BeanFactory 查找 AspectJExpressionPointcutAdvisor 类型的对象
        List<AspectJExpressionPointcutAdvisor> advisors =
                xmlBeanFactory.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {

            // 2. 使用 Pointcut 对象匹配当前 bean 对象
            if (advisor.getPointcut().getClassFilter().matchers(bean.getClass())) {
                ProxyFactory advisedSupport = new ProxyFactory();
                advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
                advisedSupport.setTargetSource(targetSource);

                // 3. 生成代理对象，并返回
                return advisedSupport.getProxy();
            }
        }

        // 2. 匹配失败，返回 bean
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        xmlBeanFactory = (XmlBeanFactory) beanFactory;
    }
}
