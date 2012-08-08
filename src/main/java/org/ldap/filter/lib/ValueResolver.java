package org.ldap.filter.lib;

public interface ValueResolver {
	Option<Object> getValue(Object bean, String key);
}
