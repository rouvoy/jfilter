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
package org.ldap.filter.lib.parsers;

import static java.util.regex.Pattern.compile;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterParser;
import org.ldap.filter.lib.utils.Option;

public class JsonFilterParser extends FilterParser {
	// filter = "{" filtercomp "}"
	private final Pattern filterRule = compile("^\\x7B(.+)\\x7D$");
	// filtercomp = item , filtercomp
	// item = key ":" value
	private final Pattern itemRule = compile("^([^:]*):(.+)$");

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
		return some(and(list.toArray(new Filter[list.size()])));
	}

	private final Option<Filter> simple(String filter) {
		final Matcher m = matches(filter, itemRule);
		if (m == null)
			return none;
		return some(equalsTo(identifier(word(m.group(1).trim())),
				word(m.group(2).trim())));
	}

	private String[] identifier(String filter) {
		String[] res = filter.split("\\.");
		return res.length > 0 ? res : new String[] { filter };
	}
}
