package org.ldap.filter.lib;

import java.lang.reflect.Field;

public class EqualsFilter extends SimpleFilter {

	public EqualsFilter(String attribute, String value) {
		super(attribute, value, "=");
	}

	public boolean match(Object bean) {
		try {
			Field field = bean.getClass().getDeclaredField(attribute);
			field.setAccessible(true);
			Class<?> type = field.getType();
			if (type.isAssignableFrom(int.class))
				return field.getInt(bean) == Integer.parseInt(value);
			if (type.isAssignableFrom(float.class))
				return field.getFloat(bean) == Float.parseFloat(value);
			if (type.isAssignableFrom(double.class))
				return field.getDouble(bean) == Double.parseDouble(value);
			if (type.isAssignableFrom(boolean.class))
				return field.getBoolean(bean) == Boolean.parseBoolean(value);
			return field.get(bean).equals(value);
		} catch (Exception e) {
			return false;
		}
	}
}
