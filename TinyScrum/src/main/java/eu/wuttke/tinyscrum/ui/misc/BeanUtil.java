package eu.wuttke.tinyscrum.ui.misc;

import java.lang.reflect.Method;

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
}
