package org.ldap.filter.lib.operators;

public class TypeFilter extends WildcardFilter {

	public TypeFilter(String type) {
		super(new String[] { "class" }, type);
	}

	public boolean match(Object bean) {
		// TODO Auto-generated method stub
		return false;
	}
}
