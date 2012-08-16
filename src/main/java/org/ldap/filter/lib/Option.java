package org.ldap.filter.lib;

public interface Option<T> {
	boolean isEmpty();

	boolean isDefined();

	T get();

	T getOr(Option<T>... opt);

	Option<T> or(Option<T>... opt);
}
