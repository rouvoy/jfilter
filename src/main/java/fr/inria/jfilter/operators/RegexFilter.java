package fr.inria.jfilter.operators;

import static fr.inria.jfilter.resolvers.ValueResolver.instance;

import fr.inria.jfilter.utils.Option;

public class RegexFilter extends FilterImpl {
	protected final String[] attribute;
	protected String regex;

	public RegexFilter(String[] identifier, String expression) {
		this.attribute = identifier;
		this.regex = expression;
	}

	public boolean match(Object bean) {
		Option<Object> res = instance.getValue(bean, attribute);
		return res.isEmpty() ? false : res.get().toString().matches(regex);
	}
}
