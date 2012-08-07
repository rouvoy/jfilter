package org.ldap.filter.lib;

import static java.util.regex.Pattern.compile;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterParser;

public class LdapFilterParser extends FilterParser {
	// filter = "(" filtercomp ")"
	private final Pattern filterRule = compile("^\\x28(.+)\\x29$");

	// not = "!" filter
	private final Pattern notRule = compile("^!(.+)$");

	// filtercomp = and / or / not / item
	// and = "&" filterlist
	// or = "|" filterlist
	private final Pattern filtercompRule = compile("^([&|\\x7C])(.+)$");

	// filterlist = 1*filter
	// item = simple / present / substring / extensible

	// simple = attr filtertype value
	// filtertype = equal / approx / greater / less
	// equal = "=", approx = "~=", greater = ">=", less = "<="
	private final Pattern simpleRule = compile("^(\\S*)\\s*([=|~|>|<])\\s*(.+)$");

	// extensible = attr [":dn"] [":" matchingrule] ":=" value
	// / [":dn"] ":" matchingrule ":=" value
	// present = attr "=*"
	// substring = attr "=" [initial] any [final]
	// initial = value
	// any = "*" *(value "*")
	// final = value
	// attr = AttributeDescription from Section 4.1.5 of [1]
	// matchingrule = MatchingRuleId from Section 4.1.9 of [1]
	// value = AttributeValue from Section 4.1.6 of [1]

	private final Logger log = Logger.getLogger(LdapFilterParser.class
			.getName());

	protected Option<Filter> tryToParse(String filter) {
		if (log.isLoggable(Level.FINE))
			log.fine("Trying to parse \"" + filter + "\" as an LDAP filter");
		return filter(filter.trim());
	}

	private final Option<Filter> filter(String filter) {
		final Matcher m = matches(filter, filterRule);
		if (m == null)
			return none;
		String val = m.group(1).trim();
		return filtercomp(val).orElse(not(val).orElse(simple(val)));
	}

	private final LinkedList<String> split(String input, char start, char end) {
		LinkedList<String> res = new LinkedList<String>();
		int count = 0;
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			if (current == start) {
				count++;
			}
			if (count>0)
				buf.append(current);
			if (current == end) {
				if (count == 1) {
					res.add(buf.toString());
					buf = new StringBuffer();
				}
				count--;
			}
		}
		return res;
	}

	private final Option<Filter> filtercomp(String filter) {
		final Matcher m = matches(filter, filtercompRule);
		if (m == null)
			return none;
		LinkedList<Filter> list = new LinkedList<Filter>();
		for (String f : split(m.group(2).trim(),'(',')')) {
			Option<Filter> res = filter(f);
			if (res.isEmpty())
				return none;
			list.add(res.get());
		}
		String operator = m.group(1).trim();
		if (operator.equals("&"))
			return some(and(list));
		if (operator.equals("|"))
			return some(or(list));
		return none;
	}

	private final Option<Filter> not(String filter) {
		final Matcher m = matches(filter, notRule);
		if (m == null)
			return none;
		Option<Filter> res = filter(m.group(1).trim());
		return res.isEmpty() ? none : some(not(res.get()));
	}

	private final Option<Filter> simple(String filter) {
		final Matcher m = matches(filter, simpleRule);
		if (m == null)
			return none;
		String operator = m.group(2).trim();
		if (operator.equals("="))
			return some(equalsTo(m.group(1), m.group(3)));
		if (operator.equals("~"))
			return some(not(equalsTo(m.group(1), m.group(3))));
		if (operator.equals(">"))
			return some(moreThan(m.group(1), m.group(3)));
		if (operator.equals("<"))
			return some(lessThan(m.group(1), m.group(3)));
		return none;
	}
}
