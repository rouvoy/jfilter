package org.ldap.filter.lib;

import static java.util.regex.Pattern.compile;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterParser;

public class JsonFilterParser extends FilterParser {
	// filter = "{" filtercomp "}"
	private final Pattern filterRule = compile("^\\x7B(.+)\\x7D$");
	// filtercomp = key ":" value
	private final Pattern simpleRule = compile("^([^:]*)\\s*:\\s*(.+)$");

	private final Logger log = Logger.getLogger(JsonFilterParser.class
			.getName());

	protected Option<Filter> tryToParse(String filter) {
		if (log.isLoggable(Level.FINE))
			log.fine("Trying to parse \"" + filter + "\" as a JSON filter");
		return filter(filter.trim());
	}

	private final Option<Filter> filter(String filter) {
		final Matcher m = matches(filter, filterRule);
		if (m == null)
			return none;
		return sequence(m.group(1).trim());
	}

	private final Option<Filter> sequence(String filter) {
		String[] parts = filter.split(",");
		if (parts.length == 1)
			return simple(filter);
		LinkedList<Filter> list = new LinkedList<Filter>();
		for (String part : parts) {
			Option<Filter> res = simple(part);
			if (res.isEmpty())
				return none;
			list.add(res.get());
		}
		return some(and(list));
	}

	private final Option<Filter> simple(String filter) {
		final Matcher m = matches(filter, simpleRule);
		if (m == null)
			return none;
		return some(equalsTo(word(m.group(1)), word(m.group(2))));
	}
}
