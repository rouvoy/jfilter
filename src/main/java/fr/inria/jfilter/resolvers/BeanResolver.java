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
package fr.inria.jfilter.resolvers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.utils.Views;

public class BeanResolver extends AbstractResolver {
	public static final Resolver bean = new BeanResolver();

	public Collection<Object> resolve(Object bean, String key,
			Map<String, Object> ctx) {
		final Class<?> clazz = bean.getClass();
		Collection<Object> res = checkCache(clazz, bean, key, ctx);
		if (res != null)
			return res;
		res = resolveGetterValue(clazz, bean, key, ctx);
		if (res != null)
			return res;
		res = resolveMethodValue(clazz, bean, key, ctx);
		if (res != null)
			return res;
		return resolveFieldValue(clazz, bean, key, ctx);
	}

	private Collection<Object> checkCache(Class<?> clazz, Object bean,
			String key, Map<String, Object> ctx) {
		Object cache = ctx.get(clazz + "#" + key);
		if (cache == null)
			return null;
		try {
			if (cache instanceof Field)
				return Views.asView(((Field) cache).get(bean));
			else if (cache instanceof Method)
				return Views.asView(((Method) cache).invoke(bean));
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	private Collection<Object> resolveGetterValue(Class<?> clazz, Object bean,
			String key, Map<String, Object> ctx) {
		String getter = "get" + key;
		for (Method m : clazz.getMethods())
			if (getter.equalsIgnoreCase(m.getName())) {
				m.setAccessible(true);
				ctx.put(clazz + "#" + key, m);
				try {
					return Views.asView(m.invoke(bean));
				} catch (Exception e) {
				}
			}
		return null;
	}

	private Collection<Object> resolveMethodValue(Class<?> clazz, Object bean,
			String key, Map<String, Object> ctx) {
		for (Method m : clazz.getMethods())
			if (key.equalsIgnoreCase(m.getName())) {
				m.setAccessible(true);
				ctx.put(clazz + "#" + key, m);
				try {
					return Views.asView(m.invoke(bean));
				} catch (Exception e) {
				}
			}
		return null;
	}

	private Collection<Object> resolveFieldValue(Class<?> clazz, Object bean,
			String key, Map<String, Object> ctx) {
		for (Field f : clazz.getFields())
			if (key.equalsIgnoreCase(f.getName())) {
				f.setAccessible(true);
				ctx.put(clazz + "#" + key, f);
				try {
					return Views.asView(f.get(bean));
				} catch (Exception e) {
				}
			}
		return null;
	}
}
