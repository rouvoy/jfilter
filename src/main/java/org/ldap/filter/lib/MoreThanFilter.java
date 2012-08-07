package org.ldap.filter.lib;

import java.lang.reflect.Field;

public class MoreThanFilter extends SimpleFilter {

	public MoreThanFilter(String attribute, String value) {
		super(attribute, value, ">");
	}

	public boolean match(Object bean) {
		try {
			Field field = bean.getClass().getDeclaredField(attribute);
			field.setAccessible(true);
			Class<?> type = field.getType();
			if (type.isAssignableFrom(int.class))
				return field.getInt(bean) > Integer.parseInt(value);
			if (type.isAssignableFrom(short.class))
				return field.getShort(bean) > Short.parseShort(value);
			if (type.isAssignableFrom(long.class))
				return field.getLong(bean) > Long.parseLong(value);
			if (type.isAssignableFrom(float.class))
				return field.getFloat(bean) > Float.parseFloat(value);
			if (type.isAssignableFrom(double.class))
				return field.getDouble(bean) > Double.parseDouble(value);
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
