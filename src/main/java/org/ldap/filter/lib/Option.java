package org.ldap.filter.lib;

public interface Option<T> {
	boolean isEmpty();

	boolean isDefined();

	T get();

	T getOrElse(Option<T> opt);

	Option<T> orElse(Option<T> opt);
}
