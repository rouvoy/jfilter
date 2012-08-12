package org.ldap.filter.lib;


public class LessThanFilter extends ComparableFilter {

	public LessThanFilter(String attribute, String value) {
		super(attribute, value, "<");
	}

	protected boolean convert(int result) {
		return result < 0;
	}
}
