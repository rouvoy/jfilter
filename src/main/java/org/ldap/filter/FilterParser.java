/**
 * Copyright (C) 2012 University Lille 1, Inria
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 * Contact: romain.rouvoy@univ-lille1.fr
 */
package org.ldap.filter;

import static java.util.regex.Pattern.compile;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.lib.AndFilter;
import org.ldap.filter.lib.EqualsToFilter;
import org.ldap.filter.lib.JsonFilterParser;
import org.ldap.filter.lib.LdapFilterParser;
import org.ldap.filter.lib.LessThanFilter;
import org.ldap.filter.lib.MoreThanFilter;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.NotFilter;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.OrFilter;
import org.ldap.filter.lib.Some;

public class FilterParser {
	private final Logger log = Logger.getLogger(FilterParser.class.getName());

	public static final FilterParser instance = new FilterParser();
	public static final FilterParser ldap = new LdapFilterParser();
	public static final FilterParser json = new JsonFilterParser();

	protected FilterParser() {
	}

	@SuppressWarnings("unchecked")
	protected static Option<Filter> none = (Option<Filter>) None.none;

	protected static Option<Filter> some(Filter f) {
		return Some.some(f);
	}

	protected static Filter equalsTo(String key, String value) {
		return new EqualsToFilter(key, value);
	}

	protected static Filter moreThan(String key, String value) {
		return new MoreThanFilter(key, value);
	}

	protected static Filter lessThan(String key, String value) {
		return new LessThanFilter(key, value);
	}

	protected static Filter not(Filter f) {
		return new NotFilter(f);
	}

	protected static Filter and(List<Filter> list) {
		return new AndFilter(list);
	}

	protected static Filter or(List<Filter> list) {
		return new OrFilter(list);
	}

	
	
	protected final Matcher matches(String filter, Pattern pattern) {
		final Matcher m = pattern.matcher(filter);
		if (log.isLoggable(Level.FINER))
			log.finer("Matching \"" + filter + "\" against "
					+ pattern.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		return m.matches() ? m : null;
	}

	protected final String word(String input) {
		Matcher m = wordPattern.matcher(input.trim());
		return m.matches() ? m.group(1) : input;
	}

	private final Pattern wordPattern = compile("\"?([^\"]+)\"?");

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
		return ldap.tryToParse(filter).or(json.tryToParse(filter));
	}


	protected final LinkedList<String> split(String input, char start, char end) {
		LinkedList<String> res = new LinkedList<String>();
		int count = 0;
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			if (current == start) {
				count++;
			}
			if (count > 0)
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
}
