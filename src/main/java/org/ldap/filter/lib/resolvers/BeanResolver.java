/**
 * Copyright (C) 2012 University Lille 1, Inria
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 * Contact: romain.rouvoy@univ-lille1.fr
 */
package org.ldap.filter.lib.resolvers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.ldap.filter.lib.utils.None;
import org.ldap.filter.lib.utils.Option;
import org.ldap.filter.lib.utils.Some;

public class BeanResolver extends ValueResolver {

	@SuppressWarnings("unchecked")
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
