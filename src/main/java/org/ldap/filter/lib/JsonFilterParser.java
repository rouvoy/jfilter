package org.ldap.filter.lib;

import static java.util.regex.Pattern.compile;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterParser;

public class JsonFilterParser extends FilterParser {
	// filter = "{" filtercomp "}"
	private final Pattern filterRule = compile("^\\x7B(.+)\\x7D$");
	private final Pattern simpleRule = compile("^([^:]*)\\s*:\\s*(.+)$");

	private final Logger log = Logger.getLogger(JsonFilterParser.class
			.getName());

	protected Option<Filter> tryToParse(String filter) {
		if (log.isLoggable(Level.FINE))
			log.fine("Trying to parse as a JSON filter \"" + filter + "\"");
		return filter(filter.trim());
	}

	private final Option<Filter> filter(String filter) {
		final Matcher m = filterRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ filterRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		return m.matches() ? simple(m.group(1).trim()) : none;
	}

	private final Option<Filter> simple(String filter) {
		Matcher m = simpleRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ simpleRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		return m.matches() ? some(equalsTo(word(m.group(1)), word(m.group(2))))
				: none;
	}
}
