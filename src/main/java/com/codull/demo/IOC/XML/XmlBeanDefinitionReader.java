package com.codull.demo.IOC.XML;

import com.codull.demo.IOC.BeanDefinition;
import com.codull.demo.IOC.BeanDefinitionReader;
import com.codull.demo.IOC.BeanReference;
import com.codull.demo.IOC.PropertyValue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Springboot_IOC_AOP
 * @description:
 * @author: anthony1314
 * @create: 2020-02-19 04:30
 **/

public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    private Map<String, BeanDefinition> registry;//维护一个map容器来作为IOC容器

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public XmlBeanDefinitionReader() {
        registry = new HashMap<>();
    }

    /**
     *
     *
     * @description: 加载某个位置的xml IOC配置文件
     * @param location
     * @return: void
     * @author: anthony1314
     * @time: 2020/4/17 16:01
     */
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = new FileInputStream(location);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        Element root = doc.getDocumentElement();
        parseBeanDefinitions(root);
    }

    /**
     *
     *
     * @description: 对文档的每个节点进行遍历
     * @param root
     * @return: void
     * @author: anthony1314
     * @time: 2020/4/17 16:02
     */
    private void parseBeanDefinitions(Element root) throws Exception {
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                parseBeanDefinition(ele);
            }
        }
    }
    /**
     *
     *
     * @description: 初始化每个类 根据文档子节点 初始化其属性
     * @param ele
     * @return: void
     * @author: anthony1314
     * @time: 2020/4/17 16:05
     */
    private void parseBeanDefinition(Element ele) throws Exception {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName(className);
        processProperty(ele, beanDefinition);
        registry.put(name, beanDefinition);
    }
    /**
     *
     *
     * @description: 将带有 property 标签的 节点的 作为bean实例的属性 放进beanDefinition 的属性列表
     * @param ele,beanDefinition
     * @return: void
     * @author: anthony1314
     * @time: 2020/4/17 16:06
     */
    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNodes = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNodes.getLength(); i++) {
            Node propertyNode = propertyNodes.item(i);
            if (propertyNode instanceof Element) {
                Element propertyElement = (Element) propertyNode;
                String name = propertyElement.getAttribute("name");
                String value = propertyElement.getAttribute("value");
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                } else {
                    String ref = propertyElement.getAttribute("ref");
                    if (null == ref || 0 == ref.length()) {
                        throw new IllegalArgumentException("ref config error!");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
