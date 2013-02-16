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

import static fr.inria.jfilter.FilterParser.json;
import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.Filter;
import fr.inria.jfilter.FilterException;
import fr.inria.jfilter.FilterTestCase;

/**
 * Unit tests for JSON-like filters.
 */
public class JsonFilterTest extends FilterTestCase {
	public JsonFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(JsonFilterTest.class);
	}

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(json.parse("{lastname:Doe}"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = json.parse("{lastname:Doe}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = json.parse("{ lastname : Doe }");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDoNotEqualsString() throws FilterException {
		Filter filter = json.parse("{ !lastname : Smith }");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithValueQuotes() throws FilterException {
		Filter filter = json.parse("{lastname:\"Doe\"}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithWildcard() throws FilterException {
		Filter filter = json.parse("{lastname:D*}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterExist() throws FilterException {
		Filter filter = json.parse("{lastname:*}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithKeyQuotes() throws FilterException {
		Filter filter = json.parse("{\"lastname\":Doe}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = json.parse("{male:true}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = json.parse("{age:30}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = json.parse("{height:1.8}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsEmbeddedString() throws FilterException {
		Filter filter = json.parse("{address.city:New York}");
		assertTrue(filter.match(doe.dad));
	}
	
	public void testFilterEmptyCollection() throws FilterException {
		Filter filter = json.parse("{hobbies.size:0}");
		assertTrue(filter.match(doe.dad));
	}	

	public void testFilterByAnyLastname() throws FilterException {
		Filter filter = json.parse("{dad.lastname:*}");
		assertTrue(filter.match(doe));
	}

	public void testFilterCollectionByAnyLastname() throws FilterException {
		Filter filter = json.parse("{members.lastname:*}");
		assertTrue(filter.match(doe));
	}

	public void testFilterSequence() throws FilterException {
		Filter filter = json.parse("{firstname:John,lastname:Doe}");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterStarFilter() throws FilterException {
		Filter filter = json.parse("{\"filter\":\"*:W\"}");
		assertTrue(filter.match(doe.dad));
	}
}
