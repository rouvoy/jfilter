package org.ldap.filter.lib.operators;

import static org.ldap.filter.lib.resolvers.ValueResolver.instance;

import org.ldap.filter.lib.utils.Option;

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
