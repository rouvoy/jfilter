package org.ldap.filter.lib;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterException;
import org.ldap.filter.FilterParser;

public class JsonFilterParser extends FilterParser {
	// filter = "{" filtercomp "}"
	private final Pattern filterRule = Pattern.compile("^\\x7B(.+)\\x7D$");
	private final Pattern simpleRule = Pattern.compile("(\\w*):(.+)");

	private final Logger log = Logger.getLogger(JsonFilterParser.class.getName());

	public Filter parse(String filter) throws FilterException {
		if (log.isLoggable(Level.FINE))
			log.fine("Parsing filter \"" + filter + "\"");
		return filter(filter);
	}


	private final Filter filter(String filter) throws FilterException {
		final Matcher m = filterRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ filterRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		if (!m.matches())
			throw new FilterException("Sub-filter " + filter + " is incorrect");
		return simple(m.group(1));
	}


	private final Filter simple(String filter) throws FilterException {
		Matcher m = simpleRule.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ simpleRule.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		if (!m.matches())
			throw new FilterException("Sub-filter " + filter + " is incorrect");
		return new EqualsFilter(m.group(1), m.group(2));
	}
}
