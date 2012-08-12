package org.ldap.filter.lib;

public class EqualsFilter extends ComparableFilter {

	public EqualsFilter(String attribute, String value) {
		super(attribute, value, "=");
	}

	protected boolean convert(int result) {
		return result == 0;
	}
}
