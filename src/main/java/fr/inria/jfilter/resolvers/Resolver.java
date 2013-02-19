package fr.inria.jfilter.resolvers;

import java.util.Collection;
import java.util.Map;

public interface Resolver {
	Collection<Object> resolve(Object pojo, String axe);

	/**
	 * @param pojo Input object
	 * @param axe Resolution axe
	 * @param context Resolution context (optional parameters)
	 * @return Some object if found, None else
	 */
	Collection<Object> resolve(Object pojo, String axe, Map<String, Object> context);
}
