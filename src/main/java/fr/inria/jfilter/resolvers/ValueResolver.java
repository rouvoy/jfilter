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

import static fr.inria.jfilter.Context.get;
import static fr.inria.jfilter.Context.resolvers;
import static java.lang.Boolean.TRUE;

import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.Context;
import fr.inria.jfilter.utils.Views;

public class ValueResolver extends AbstractResolver {
	public static final ValueResolver instance = new ValueResolver();

	public static final String VISIT_COLLECTION = "visit_collection";
	public static final String RESOLVER_PLUGINS = "resolver_plugins";

	protected ValueResolver() {
	}

	public Collection<Object> resolve(Object pojo, String[] path) {
		return resolve(pojo, path, Context.newContext());
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> resolve(Object pojo, String[] path,
			Map<String, Object> context) {
		Object output = pojo;
		for (String key : path) {
			output = resolve(output, key, context);
			if (output == null)
				return null;
		}
		return (Collection<Object>) output;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> resolve(Object pojo, String axe,
			Map<String, Object> context) {
		Collection<Object> res = resolveSingleValue(pojo, axe, context);
		if (res != null)
			return res;
		Boolean visit = get(context, VISIT_COLLECTION, TRUE);
		if (visit.booleanValue() && pojo instanceof Collection<?>)
			return resolveCollection((Collection<Object>) pojo, axe, context);
		return null;
	}

	protected Collection<Object> resolveCollection(Collection<Object> col,
			String axe, Map<String, Object> context) {
		Collection<Object> res = Views.newView();
		for (Object pojo : col)
			Views.mergeViews(res, resolveSingleValue(pojo, axe, context));
		return res;
	}

	protected Collection<Object> resolveSingleValue(Object pojo, String axe,
			Map<String, Object> context) {
		for (Resolver resolver : get(context, RESOLVER_PLUGINS, resolvers)) {
			Collection<Object> res = resolver.resolve(pojo, axe, context);
			if (res != null)
				return res;
		}
		return null;
	}
}
