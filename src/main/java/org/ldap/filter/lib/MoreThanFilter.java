package org.ldap.filter.lib;


public class MoreThanFilter extends ComparableFilter {

	public MoreThanFilter(String attribute, String value) {
		super(attribute, value, ">");
	}

	protected boolean convert(int result) {
		return result > 0;
	}

}
