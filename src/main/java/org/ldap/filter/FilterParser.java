package org.ldap.filter;

import org.ldap.filter.lib.JsonFilterParser;
import org.ldap.filter.lib.LdapFilterParser;

public class FilterParser {
	public static final FilterParser instance = new FilterParser();
	public static final FilterParser ldap = new LdapFilterParser();
	public static final FilterParser json = new JsonFilterParser();
	
	protected FilterParser() {
	}
	
	public Filter parse(String filter) throws FilterException {
		try {
			return ldap.parse(filter);
		} catch (Exception e) {
			return json.parse(filter);
		}
	} 
}
