package eu.wuttke.tinyscrum.ui.misc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtil {

	public static Object getPropertyValue(Object bean, String propertyId) {
		try {
			Method getter = bean.getClass().getMethod("get" + propertyId.substring(0, 1).toUpperCase() + propertyId.substring(1), new Class<?>[0]);
			if (getter != null)
				return getter.invoke(bean, (Object[])null);
			else
				return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setPropertyValue(Object bean, String propertyId, Object value) {
		try {
			Method setter = bean.getClass().getMethod("set" + propertyId.substring(0, 1).toUpperCase() + propertyId.substring(1), new Class<?>[]{value.getClass()});
			if (setter != null)
				setter.invoke(bean, new Object[]{value});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<String> getBeanPropertiesByType(Class<?> beanClass, Class<?> propertyType) {
		try {
			BeanInfo info = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			List<String> properties = new ArrayList<String>(pds.length);
			for (PropertyDescriptor pd : pds) {
				if (pd.getPropertyType() == propertyType)
					properties.add(pd.getName());
			}
			return properties;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	
}
