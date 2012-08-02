package org.ldap.filter;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.lib.EqualsFilter;
import org.ldap.filter.lib.LessThanFilter;
import org.ldap.filter.lib.MoreThanFilter;
import org.ldap.filter.lib.NotFilter;

public class FilterParser {
	// filter = "(" filtercomp ")"
	private final Pattern filterRule = Pattern.compile("^\\x28(.+)\\x29$");

	// filtercomp = and / or / not / item
	// and = "&" filterlist
	// or = "|" filterlist
	// not = "!" filter
	// filterlist = 1*filter
	// item = simple / present / substring / extensible
	// simple = attr filtertype value
	// filtertype = equal / approx / greater / less
	// equal = "="
	// approx = "~="
	// greater = ">="
	// less = "<="
	private final Pattern simpleRule = Pattern.compile("(\\w*)([=|~|>|<])(.+)");

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

	public static final FilterParser instance = new FilterParser();
	private final Logger log = Logger.getLogger(FilterParser.class.getName());

	private FilterParser() {
	}

	public Filter parse(String filter) throws FilterException {
		if (log.isLoggable(Level.INFO))
			log.info("Parsing filter \"" + filter + "\"");
		return filter(filter);
	}


	private final Filter filter(String filter) throws FilterException {
		final Matcher m = filterRule.matcher(filter);
		if (log.isLoggable(Level.FINE))
			log.fine("Matching \"" + filter + "\" against "
					+ filterRule.pattern() + " => " + m.find() + " ("
					+ m.groupCount() + ")");
		if (!m.find())
			throw new FilterException("Sub-filter " + filter + " is incorrect");
		return simple(m.group(1));
	}


	private final Filter simple(String filter) throws FilterException {
		Matcher m = simpleRule.matcher(filter);
		if (log.isLoggable(Level.FINE))
			log.fine("Matching \"" + filter + "\" against "
					+ simpleRule.pattern() + " => " + m.find() + " ("
					+ m.groupCount() + ")");
		if (!m.find())
			throw new FilterException("Sub-filter " + filter + " is incorrect");
		if (m.group(2).equals("="))
			return new EqualsFilter(m.group(1), m.group(3));
		if (m.group(2).equals("~"))
			return new NotFilter(new EqualsFilter(m.group(1), m.group(3)));
		if (m.group(2).equals(">"))
			return new MoreThanFilter(m.group(1), m.group(3));
		if (m.group(2).equals("<"))
			return new LessThanFilter(m.group(1), m.group(3));
		throw new FilterException("Operator " + m.group(2)
				+ " is not supported");
	}
}
