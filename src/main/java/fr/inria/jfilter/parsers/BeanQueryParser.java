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

	public static final QueryParser bean = new BeanQueryParser("Bean", '.',
			'(', ')'), xpath = new BeanQueryParser("XPath", '/', '[', ']');

	private final char open, close, split;
	public final String type;

	public BeanQueryParser(String type, char split, char open, char close) {
		this.type = type;
		this.split = split;
		this.open = open;
		this.close = close;
	}

	protected Option<Query> tryToParse(String query) throws ParsingException {
		if (log.isLoggable(Level.FINE))
			log.fine("Trying to parse \"" + query + "\" as a " + type
					+ " query");
		Path path = new Path();
		int start = 0;
		while (true) {
			start = process(query, path, start);
			if (start >= query.length())
				break;
			if (start == -1)
				return none;
			if (query.charAt(start) == split)
				start++;
		}
		return Some.some((Query) path);
	}

	private int process(String query, Path path, int start)
			throws ParsingException {
		int from = query.indexOf(open, start) + 1, to = query.length();
		Step s;
		if (from == 0)
			s = new Step(identifier(query.substring(start)), null);
		else {
			to = split(query, from);
			if (to == -1)
				return -1;
			s = new Step(identifier(query.substring(start, from - 1)),
					filter.parse(query.substring(from, to)));
		}
		path.expand(s);
		return to + 1;
	}

	private int split(String query, int start) {
		int current = start;
		int depth = 0;
		while (current < query.length()) {
			char car = query.charAt(current);
			if (car == open)
				depth++;
			if (car == close)
				if (depth == 0)
					return current;
				else
					depth--;
			current++;
		}
		return -1;
	}

	private String[] identifier(String filter) {
		String[] res = filter.split("\\" + split);
		return res.length > 0 ? res : new String[] { filter };
	}
}
