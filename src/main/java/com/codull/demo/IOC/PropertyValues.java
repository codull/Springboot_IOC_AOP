package com.codull.demo.IOC;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Springboot_IOC_AOP
 * @description: 属性们
 * @author: anthony1314
 * @create: 2020-02-19 03:26
 **/

public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {

        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }


}
