package org.ldap.filter.lib;

public class EqualsToFilter extends ComparableFilter {

	public EqualsToFilter(String attribute, String value) {
		super(attribute, value, "=");
	}

	protected boolean convert(int result) {
		return result == 0;
	}
}
