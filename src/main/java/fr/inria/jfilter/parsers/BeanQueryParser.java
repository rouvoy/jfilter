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
package fr.inria.jfilter.parsers;

import static fr.inria.jfilter.FilterParser.filter;

import java.util.logging.Level;
import java.util.logging.Logger;

import fr.inria.jfilter.Parser;
import fr.inria.jfilter.ParsingException;
import fr.inria.jfilter.Query;
import fr.inria.jfilter.QueryParser;
import fr.inria.jfilter.query.Path;
import fr.inria.jfilter.query.Step;
import fr.inria.jfilter.utils.Option;
import fr.inria.jfilter.utils.Some;

public class BeanQueryParser extends QueryParser {

	private final Logger log = Logger
			.getLogger(BeanQueryParser.class.getName());

	public static final Parser<Query> bean = new BeanQueryParser();

	protected Option<Query> tryToParse(String query) throws ParsingException {
		if (log.isLoggable(Level.FINE))
			log.fine("Trying to parse \"" + query + "\" as a Bean query");
		Path path = new Path();
		int start = 0;
		while (true) {
			start = process(query, path, start);
			if (start >= query.length())
				break;
			if (start == -1)
				return none;
			if (query.charAt(start) == '.')
				start++;
		}
		return Some.some((Query) path);
	}

	private int process(String query, Path path, int start)
			throws ParsingException {
		int from = query.indexOf("(", start) + 1, to = query.length();
		Step s;
		if (from == 0)
			s = new Step(identifier(query.substring(start)), null);
		else {
			to = split(query, from, new char[] { '(', ')' });
			if (to == -1)
				return -1;
			s = new Step(identifier(query.substring(start, from - 1)),
					filter.parse(query.substring(from, to)));
		}
		path.expand(s);
		return to + 1;
	}

	private int split(String query, int start, char[] tokens) {
		int current = start;
		int depth = 0;
		while (current < query.length()) {
			char car = query.charAt(current);
			if (car == tokens[0])
				depth++;
			if (car == tokens[1])
				if (depth == 0)
					return current;
				else
					depth--;
			current++;
		}
		return -1;
	}

	private String[] identifier(String filter) {
		String[] res = filter.split("\\.");
		return res.length > 0 ? res : new String[] { filter };
	}
}
