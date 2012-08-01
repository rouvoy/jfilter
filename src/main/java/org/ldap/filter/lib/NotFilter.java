package org.ldap.filter.lib;

import org.ldap.filter.Filter;

public class NotFilter implements Filter {
	private final Filter delegate;

	public NotFilter(Filter delegate) {
		this.delegate = delegate;
	}

	public boolean match(Object bean) {
		return !this.delegate.match(bean);
	}

	public String toString() {
		return "!(" + this.delegate.toString() + ")";
	}
}
