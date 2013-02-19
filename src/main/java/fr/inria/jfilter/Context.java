package fr.inria.jfilter;

import java.util.HashMap;
import java.util.Map;

import fr.inria.jfilter.resolvers.BeanResolver;
import fr.inria.jfilter.resolvers.MapResolver;
import fr.inria.jfilter.resolvers.Resolver;
import fr.inria.jfilter.resolvers.TypeResolver;
import fr.inria.jfilter.resolvers.ValueResolver;

public class Context {
	public static final Resolver[] resolvers = new Resolver[] {
			BeanResolver.bean, MapResolver.map, TypeResolver.type };

	public static final Map<String, Object> newContext() {
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put(ValueResolver.VISIT_COLLECTION, true);
		ctx.put(ValueResolver.RESOLVER_PLUGINS, resolvers);
		return ctx;
	}

	public static final <T> T get(Map<String, Object> ctx, String key, T defaut) {
		@SuppressWarnings("unchecked")
		T res = (T) ctx.get(key);
		if (res == null)
			return defaut;
		return res;
	}
}
