package org.ldap.filter.lib;

import static java.util.regex.Pattern.compile;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterParser;

public class LdapFilterParser extends FilterParser {
	// filter = "(" filtercomp ")"
	private final Pattern filterRule = compile("^\\x28(.+)\\x29$");

	// filtercomp = and / or / not / item
	// and = "&" filterlist
	// or = "|" filterlist
	// not = "!" filter
	private final Pattern notRule = compile("^!(.+)$");
	// filterlist = 1*filter
	// item = simple / present / substring / extensible
	// simple = attr filtertype value
	// filtertype = equal / approx / greater / less
	// equal = "="
	// approx = "~="
	// greater = ">="
	// less = "<="

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
			log.fine("Trying to parse as an LDAP filter \"" + filter + "\"");
		return filter(filter.trim());
	}

	private final Option<Filter> filter(String filter) {
		final Matcher m = filterRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ filterRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		if (!m.matches())
			return none;
		String val = m.group(1).trim();
		return not(val).orElse(simple(val));
	}

	private final Option<Filter> not(String filter) {
		final Matcher m = notRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ notRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		if (!m.matches())
			return none;
		Option<Filter> res = filter(m.group(1).trim());
		return res.isEmpty() ? none : some(not(res.get()));
	}

	private final Option<Filter> simple(String filter) {
		Matcher m = simpleRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ simpleRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		if (!m.matches())
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
