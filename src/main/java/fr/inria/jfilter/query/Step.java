package fr.inria.jfilter.query;

import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.Filter;
import fr.inria.jfilter.Query;
import fr.inria.jfilter.resolvers.ValueResolver;

public class Step implements Query {
	private final String[] elements;
	private final Filter predicate;

	public Step(String[] elt, Filter pred) {
		this.elements = elt;
		this.predicate = pred;
	}

	public Step(String elt, Filter pred) {
		this(new String[] { elt }, pred);
	}

	public Object apply(Object object, Map<String, Object> context) {
		Collection<?> val = ValueResolver.instance.getValues(object, elements);
		if (this.predicate != null)
			return this.predicate.filter(val, context);
		return val;
	}
}
