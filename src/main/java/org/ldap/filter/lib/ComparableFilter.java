/**
 * Copyright (C) ${license.year} University Lille 1, Inria
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
package org.ldap.filter.lib;

import org.ldap.filter.Filter;

public abstract class ComparableFilter implements Filter {
	protected final String attribute, value, operator;
	private final ValueResolver[] resolvers = new ValueResolver[] {
			new MapResolver(), new BeanResolver() };

	public ComparableFilter(String attribute, String value, String operator) {
		this.attribute = attribute;
		this.value = value;
		this.operator = operator;
	}

	public Object getLeftValue(Object input) {
		for (int i = 0; i < resolvers.length; i++) {
			Option<Object> val = resolvers[i].getValue(input, this.attribute);
			if (val.isDefined())
				return val.get();
		}
		return null;
	}

	protected <T extends Comparable<T>> boolean compare(T left, T right) {
		return convert(left.compareTo(right));
	}

	protected abstract boolean convert(int result);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean match(Object bean) {
		try {
			Object res = getLeftValue(bean);
			Class<?> type = res.getClass();
			if (type.isAssignableFrom(Integer.class))
				return compare((Integer) res, new Integer(value));
			if (type.isAssignableFrom(Short.class))
				return compare((Short) res, new Short(value));
			if (type.isAssignableFrom(Long.class))
				return compare((Long) res, new Long(value));
			if (type.isAssignableFrom(Float.class))
				return compare((Float) res, new Float(value));
			if (type.isAssignableFrom(Double.class))
				return compare((Double) res, new Double(value));
			if (type.isAssignableFrom(Boolean.class))
				return compare((Boolean) res, new Boolean(value));
			if (type.isAssignableFrom(Comparable.class))
				return convert(((Comparable) res).compareTo(value));
			return res.equals(value);
		} catch (Exception e) {
			return false;
		}
	}

	public String toString() {
		return "[" + attribute + " " + operator + " " + value + "]";
	}
}
