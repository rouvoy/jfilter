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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.inria.jfilter.utils.None;
import fr.inria.jfilter.utils.Option;
import fr.inria.jfilter.utils.Some;

public class QueryParser implements Parser<Query> {
	private final Logger log = Logger.getLogger(QueryParser.class.getName());

	public static final QueryParser query = new QueryParser();

	public static final QueryParser[] parsers = new QueryParser[] {};

	protected QueryParser() {
	}

	@SuppressWarnings("unchecked")
	protected static Option<Query> none = (Option<Query>) None.none;

	protected static Option<Query> some(Query query) {
		return Some.some(query);
	}

	private final Map<String, Query> queries = new HashMap<String, Query>();

	public Query parse(String query) throws ParsingException {
		if (log.isLoggable(Level.FINE))
			log.fine("Parsing query \"" + query + "\"");
		if (queries.containsKey(query))
			return queries.get(query);
		final Option<Query> res = tryToParse(query.trim());
		if (res.isDefined())
			queries.put(query, res.get());
		else
			throw new ParsingException("Failed to build a query for " + query);
		if (log.isLoggable(Level.FINE))
			log.fine("Parsed query is " + res.get());
		return res.get();
	}

	protected Option<Query> tryToParse(String filter) throws ParsingException {
		for (QueryParser p : parsers) {
			final Option<Query> f = p.tryToParse(filter);
			if (f.isDefined())
				return f;
		}
		return none;
	}
}
