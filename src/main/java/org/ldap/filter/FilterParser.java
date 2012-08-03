package org.ldap.filter;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.lib.EqualsFilter;
import org.ldap.filter.lib.JsonFilterParser;
import org.ldap.filter.lib.LdapFilterParser;
import org.ldap.filter.lib.LessThanFilter;
import org.ldap.filter.lib.MoreThanFilter;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.NotFilter;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.Some;

public class FilterParser {
	private final Logger log = Logger.getLogger(FilterParser.class.getName());

	public static final FilterParser instance = new FilterParser();
	public static final FilterParser ldap = new LdapFilterParser();
	public static final FilterParser json = new JsonFilterParser();

	@SuppressWarnings("unchecked")
	protected Option<Filter> none = (Option<Filter>) None.none;

	protected Option<Filter> some(Filter f) {
		return Some.some(f);
	}

	protected Filter equalsTo(String key, String value) {
		return new EqualsFilter(key, value);
	}

	protected Filter moreThan(String key, String value) {
		return new MoreThanFilter(key, value);
	}

	protected Filter lessThan(String key, String value) {
		return new LessThanFilter(key, value);
	}

	protected Filter not(Filter f) {
		return new NotFilter(f);
	}

	private final Pattern wordPattern = Pattern.compile("\"?([^\"]+)\"?");

	protected final String word(String input) {
		Matcher m = wordPattern.matcher(input.trim());
		return m.matches() ? m.group(1) : input;
	}

	protected FilterParser() {
	}

	public Filter parse(String filter) throws FilterException {
		if (log.isLoggable(Level.FINE))
			log.fine("Parsing filter \"" + filter + "\"");
		Option<Filter> res = tryToParse(filter.trim());
		if (res.isEmpty())
			throw new FilterException("Failed to build a filter for " + filter);
		if (log.isLoggable(Level.FINE))
			log.fine("Parsed filter is " + res.get());
		return res.get();
	}

	protected Option<Filter> tryToParse(String filter) {
		return ldap.tryToParse(filter).orElse(json.tryToParse(filter));
	}
}
