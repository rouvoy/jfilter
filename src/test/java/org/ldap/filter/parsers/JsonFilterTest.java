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
package org.ldap.filter.parsers;

import static org.ldap.filter.FilterParser.json;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterException;
import org.ldap.filter.FilterTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

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
		assertNotNull(json.parse("{name:Doe}"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = json.parse("{name:Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = json.parse("{ name : Doe }");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithValueQuotes() throws FilterException {
		Filter filter = json.parse("{name:\"Doe\"}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithWildcard() throws FilterException {
		Filter filter = json.parse("{name:D*}");
		assertTrue(filter.match(bean));
	}

	public void testFilterExist() throws FilterException {
		Filter filter = json.parse("{name:*}");
		assertTrue(filter.match(bean));
	}


	public void testFilterEqualsStringWithKeyQuotes() throws FilterException {
		Filter filter = json.parse("{\"name\":Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = json.parse("{male:true}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = json.parse("{age:20}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = json.parse("{height:1.8}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsEmbeddedString() throws FilterException {
		Filter filter = json.parse("{home.city:New York}");
		assertTrue(filter.match(bean));
	}

	public void testFilterSequence() throws FilterException {
		Filter filter = json.parse("{firstname:John,name:Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterStarFilter() throws FilterException {
		Filter filter = json.parse("{\"filter\":\"*:W\"}");
		assertTrue(filter.match(bean));
	}
}
