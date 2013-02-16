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
package fr.inria.jfilter.operators;

import junit.framework.Test;
import junit.framework.TestSuite;


import fr.inria.jfilter.ParsingException;
import fr.inria.jfilter.FilterTestCase;
import fr.inria.jfilter.operators.WildcardFilter;

public class WildcardFilterTest extends FilterTestCase {
	public WildcardFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(WildcardFilterTest.class);
	}

	public void testExistFilter() throws ParsingException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "*");
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.childs, null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.childs, null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testExistEmbeddedFilter() throws ParsingException {
		WildcardFilter filter = new WildcardFilter(new String[] { "address", "country" }, "*");
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.childs, null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.childs, null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testFilterStartsWith() throws ParsingException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "J*");
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.childs, null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.childs, null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testFilterEndsWith() throws ParsingException {
		WildcardFilter filter = new WildcardFilter(new String[] { "lastname" }, "*e");
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.childs, null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.childs, null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testFilterContains() throws ParsingException {
		WildcardFilter filter = new WildcardFilter(new String[] { "lastname" }, "*o*");
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.childs, null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.childs, null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}
}
