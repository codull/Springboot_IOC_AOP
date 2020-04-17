package com.codull.demo.IOC.XML;

import com.codull.demo.IOC.BeanDefinition;
import com.codull.demo.IOC.BeanPostProcessor;
import com.codull.demo.IOC.BeanReference;
import com.codull.demo.IOC.Factory.BeanFactory;
import com.codull.demo.IOC.Factory.BeanFactoryAware;
import com.codull.demo.IOC.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 04:28
 **/

public class XmlBeanFactory  implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>(); //IOC容器里面所有bean实例的名字和属性

    private List<String> beanDefinitionNames = new ArrayList<>(); //IOC容器里面所有bean实例的名字

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private XmlBeanDefinitionReader beanDefinitionReader;

    public XmlBeanFactory(String location) throws Exception {
        beanDefinitionReader = new XmlBeanDefinitionReader();
        loadBeanDefinitions(location);
//        Iterator iter = beanDefinitionMap.keySet().iterator();
//        while (iter.hasNext()){
//            Object key = iter.next();
//            Object val = beanDefinitionMap.get(key);
//            System.out.println(key);
//        }

    }

    public void loadBeanDefinitions(String location) throws Exception {
        beanDefinitionReader.loadBeanDefinitions(location); // xml 配置文件中的属性已经读取完毕
        registerBeanDefinition(); // bean 的注册
        registerBeanPostProcessor(); // 找到所有实现了 BeanPostProcessor 的类
    }

    private void registerBeanDefinition() {
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionReader.getRegistry().entrySet()) {//获得注册器里的所有键值对
            String name = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            beanDefinitionMap.put(name, beanDefinition);
            beanDefinitionNames.add(name);
            //放进两个容器里面
        }
    }

    private void registerBeanPostProcessor() throws Exception {
        List beans = getBeansForType(BeanPostProcessor.class);
        for (Object bean : beans) {
            addBeanPostProcessor((BeanPostProcessor) bean);
        }
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (null == beanDefinition) {
            throw new IllegalArgumentException("no this bean with name " + name);
        }
        Object bean = beanDefinition.getBean();
        if (null == bean) { // 如果还没有实例化，就执行下列操作
            bean = createBean(beanDefinition);
            bean = initializeBean(bean, name);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    private Object createBean(BeanDefinition bd) throws Exception {
        Object bean = bd.getBeanClass().newInstance();
        applyPropertyValues(bean, bd); // 通过反射机制实例化填充对象属性
        return bean;
    }

    private void applyPropertyValues(Object bean, BeanDefinition bd) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        for (PropertyValue propertyValue : bd.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            try { // 调用 set 方法
                Method declaredMethod = bean.getClass().getDeclaredMethod("set" +
                        propertyValue.getName().substring(0, 1).toUpperCase()
                        + propertyValue.getName().substring(1), value.getClass());

                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);

            } catch (NoSuchMethodException e) { // 操作属性
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                try {
                    int val = Integer.valueOf((String) value); // 看 value 是否能转换成 int 类型
                    declaredField.set(bean, val);
                } catch (Exception error) {
                    declaredField.set(bean, value);
                }

            }
        }
    }

    private Object initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }
}
