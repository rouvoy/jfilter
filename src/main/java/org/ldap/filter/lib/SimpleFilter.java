package org.ldap.filter.lib;

import org.ldap.filter.Filter;

public abstract class SimpleFilter implements Filter {
	protected final String attribute, value, operator;

	public SimpleFilter(String attribute, String value, String operator) {
		this.attribute = attribute;
		this.value = value;
		this.operator = operator;
	}

	public String toString() {
		return "(" + attribute + " " + operator + " " + value + ")";
	}
}
