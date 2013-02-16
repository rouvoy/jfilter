package fr.inria.jfilter.operators;

import static fr.inria.jfilter.resolvers.ValueResolver.instance;

import java.util.Collection;
import java.util.Map;

public class RegexFilter extends FilterImpl {
	protected final String[] attribute;
	protected String regex;

	public RegexFilter(String[] identifier, String expression) {
		this.attribute = identifier;
		this.regex = expression;
	}

	public boolean match(Object bean, Map<String, Object> context) {
		if (bean instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) bean;
			for (Object elt : col)
				if (check(elt))
					return true;
		}
		return check(bean);
	}

	protected boolean check(Object bean) {
		for (Object value : instance.getValues(bean, attribute))
			if (value.toString().matches(regex))
				return true;
		return false;
	}
}
