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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class MapResolver extends ValueResolver {
	public static final ValueResolver map = new MapResolver();

	@SuppressWarnings("unchecked")
	public Collection<Object> getValue(Object bean, String key) {
		if (bean instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) bean;
			if (map.containsKey(key)) {
				Object value = map.get(key);
				if (value instanceof Collection)
					return (Collection<Object>) value;
				return Collections.singleton(value);
			}
		}
		return Collections.emptySet();
	}
}
