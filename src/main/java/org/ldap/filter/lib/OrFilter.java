package org.ldap.filter.lib;

import java.util.List;

import org.ldap.filter.Filter;

public class OrFilter implements Filter {
	private final List<Filter> delegates;

	public OrFilter(List<Filter> delegates) {
		this.delegates = delegates;
	}

	public boolean match(Object bean) {
		for (Filter f : delegates)
			if (f.match(bean))
				return true;
		return false;
	}

	public String toString() {
		return "||" + this.delegates.toString();
	}
}
