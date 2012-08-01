package org.ldap.filter.lib;

import org.ldap.filter.Filter;

public class FalseFilter implements Filter {

	public boolean match(Object bean) {
		return false;
	}

	public String toString() {
		return "(false)";
	}
}
