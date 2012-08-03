package org.ldap.filter.lib;

import java.util.LinkedList;

import org.ldap.filter.Filter;

public class OrFilter implements Filter {
	private final LinkedList<Filter> delegates;

	public OrFilter(LinkedList<Filter> delegates) {
		this.delegates = delegates;
	}

	public boolean match(Object bean) {
		for (Filter f : delegates)
			if (f.match(bean))
				return true;
		return false;
	}

	public String toString() {
		return "|(" + this.delegates.toString() + ")";
	}
}
