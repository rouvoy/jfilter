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
package fr.inria.jfilter;

import static java.util.regex.Pattern.compile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.inria.jfilter.operators.AndFilter;
import fr.inria.jfilter.operators.EqualsToFilter;
import fr.inria.jfilter.operators.LessThanFilter;
import fr.inria.jfilter.operators.MoreThanFilter;
import fr.inria.jfilter.operators.NotFilter;
import fr.inria.jfilter.operators.OrFilter;
import fr.inria.jfilter.operators.WildcardFilter;
import fr.inria.jfilter.parsers.JsonFilterParser;
import fr.inria.jfilter.parsers.LdapFilterParser;
import fr.inria.jfilter.utils.None;
import fr.inria.jfilter.utils.Option;
import fr.inria.jfilter.utils.Some;

public class FilterParser {
	private final Logger log = Logger.getLogger(FilterParser.class.getName());

	public static final FilterParser instance = new FilterParser(),
			ldap = new LdapFilterParser(), json = new JsonFilterParser();

	public static final FilterParser[] parsers = new FilterParser[] { ldap,
			json };

	protected FilterParser() {
	}

	@SuppressWarnings("unchecked")
	protected static Option<Filter> none = (Option<Filter>) None.none;

	protected static Option<Filter> some(Filter f) {
		return Some.some(f);
	}

	protected static Filter equalsTo(String[] key, String value) {
		return new EqualsToFilter(key, value);
	}

	protected static Filter moreThan(String[] key, String value) {
		return new MoreThanFilter(key, value);
	}

	protected static Filter lessThan(String[] key, String value) {
		return new LessThanFilter(key, value);
	}

	protected static Filter not(Filter f) {
		return new NotFilter(f);
	}

	protected static Filter and(Filter... list) {
		return new AndFilter(list);
	}

	protected static Filter or(Filter... list) {
		return new OrFilter(list);
	}

	protected static Filter wildcard(String[] attr, String value) {
		return new WildcardFilter(attr, value);
	}

	protected final Matcher matches(String filter, Pattern pattern) {
		final Matcher m = pattern.matcher(filter);
		if (log.isLoggable(Level.FINEST))
			log.finest("Matching \"" + filter + "\" against "
					+ pattern.pattern() + " => " + m.matches() + " ("
					+ m.groupCount() + ")");
		return m.matches() ? m : null;
	}

	protected final String word(String input) {
		Matcher m = wordPattern.matcher(input.trim());
		return m.matches() ? m.group(1) : input;
	}

	private final Pattern wordPattern = compile("\"?([^\"]+)\"?");

	private final Map<String, Filter> filters = new HashMap<String, Filter>();

	public Filter parse(String filter) throws FilterException {
		if (log.isLoggable(Level.FINE))
			log.fine("Parsing filter \"" + filter + "\"");
		if (filters.containsKey(filter))
			return filters.get(filter);
		final Option<Filter> res = tryToParse(filter.trim());
		if (res.isDefined())
			filters.put(filter, res.get());
		else
			throw new FilterException("Failed to build a filter for " + filter);
		if (log.isLoggable(Level.FINE))
			log.fine("Parsed filter is " + res.get());
		return res.get();
	}

	protected Option<Filter> tryToParse(String filter) {
		for (FilterParser p : parsers) {
			final Option<Filter> f = p.tryToParse(filter);
			if (f.isDefined())
				return f;
		}
		return none;
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
