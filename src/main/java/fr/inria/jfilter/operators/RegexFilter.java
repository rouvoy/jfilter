package fr.inria.jfilter.operators;

import static fr.inria.jfilter.resolvers.ValueResolver.instance;

public class RegexFilter extends FilterImpl {
	protected final String[] attribute;
	protected String regex;

	public RegexFilter(String[] identifier, String expression) {
		this.attribute = identifier;
		this.regex = expression;
	}

	public boolean match(Object bean) {
		for (Object value : instance.getValue(bean, attribute))
			if (value.toString().matches(regex))
				return true;
		return false;
	}
}
