package fr.inria.jfilter.query;

import static fr.inria.jfilter.resolvers.ValueResolver.instance;

import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.Filter;

public class Step extends AbstractQuery {
	private final String[] elements;
	private final Filter predicate;

	public Step(String[] elt, Filter pred) {
		this.elements = elt;
		this.predicate = pred;
	}

	public Step(String elt, Filter pred) {
		this(new String[] { elt }, pred);
	}

	public Collection<Object> apply(Object pojo, Map<String, Object> context) {
		Collection<Object> val = instance.resolve(pojo, elements, context);
		if (val.isEmpty() || this.predicate == null)
			return val;
		return this.predicate.filter(val, context);
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (String s : this.elements) {
			buf.append(s + ".");
		}
		buf.append("(" + this.predicate + ")");
		return buf.toString();
	}
}
