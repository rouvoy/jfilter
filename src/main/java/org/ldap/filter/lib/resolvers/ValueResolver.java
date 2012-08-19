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

import org.ldap.filter.lib.utils.None;
import org.ldap.filter.lib.utils.Option;
import org.ldap.filter.lib.utils.Some;

public class ValueResolver {
	public static final ValueResolver bean = new BeanResolver();
	public static final ValueResolver map = new MapResolver();
	public static final ValueResolver instance = new ValueResolver();

	protected ValueResolver() {
	}

	public Option<Object> getValue(Object pojo, String[] path) {
		Option<Object> res = Some.some(pojo);
		for (String key : path) {
			if (res.isEmpty())
				return None.none();
			res = getValue(res.get(), key);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public Option<Object> getValue(Object pojo, String key) {
		return bean.getValue(pojo, key).or(map.getValue(pojo, key));
	}
}
