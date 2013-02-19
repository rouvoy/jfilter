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
import java.util.HashSet;
import java.util.Map;

import fr.inria.jfilter.utils.Views;

public class TypeResolver extends ValueResolver {
	public static final ValueResolver type = new TypeResolver();

	public static final String TYPE = "objectClass";

	private final Collection<String> keySet = new HashSet<String>();

	protected TypeResolver(String identifier) {
		this.keySet.add(identifier);
	}

	protected TypeResolver() {
		this(TYPE);
	}

	public Collection<Object> resolve(Object pojo, String axe,
			Map<String, Object> context) {
		if (!this.keySet.contains(axe))
			return null;
		Collection<Object> res = Views.newView();
		if (pojo instanceof Class)
			update((Class<?>) pojo, res);
		else
			update(pojo.getClass(), res);
		return res;
	}

	private void update(Class<?> clazz, Collection<Object> set) {
		if (clazz == null || set.contains(clazz))
			return;
		set.add(clazz);
		set.add(clazz.getName());
		set.add(clazz.getSimpleName());
		update(clazz.getSuperclass(), set);
		for (Class<?> itf : clazz.getInterfaces())
			update(itf, set);
	}
}
