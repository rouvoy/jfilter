package org.ldap.filter.lib;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanResolver implements ValueResolver {

	public Option<Object> getValue(Object bean, String key) {
		return getFieldValue(bean, key).or(getMethodValue(bean, key));
	}

	private Option<Object> getFieldValue(Object bean, String key) {
		try {
			Field field = bean.getClass().getDeclaredField(key);
			field.setAccessible(true);
			return Some.some(field.get(bean));
		} catch (Exception e) {
			return None.none();
		}
	}

	private Option<Object> getMethodValue(Object bean, String key) {
		String getter = "get" + key.substring(0, 1).toUpperCase()
				+ key.substring(1).toLowerCase();
		try {
			Method mtd = bean.getClass().getDeclaredMethod(getter);
			mtd.setAccessible(true);
			return Some.some(mtd.invoke(bean));
		} catch (Exception e) {
			return None.none();
		}
	}
}
