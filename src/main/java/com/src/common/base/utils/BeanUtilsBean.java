package com.src.common.base.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtilsBean {
	public static Map<String, Object> describe(Object bean)
			throws IntrospectionException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (bean == null) {
			return (new HashMap<String, Object>());
		}
		BeanInfo info = Introspector.getBeanInfo(bean.getClass());
		Map<String, Object> description = new HashMap<String, Object>();
		PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			Method reader = descriptors[i].getReadMethod();
			if (reader != null) {
				Object[] os = new Object[0];
				Object value = reader.invoke(bean, os);
				description.put(name, value);
			}
		}
		description.remove("class");
		return (description);
	}
}