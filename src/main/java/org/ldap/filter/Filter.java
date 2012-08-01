package org.ldap.filter;

public interface Filter {
	boolean match(Object bean);
}
