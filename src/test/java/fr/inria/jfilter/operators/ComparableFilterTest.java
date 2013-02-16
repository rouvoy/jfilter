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
import fr.inria.jfilter.operators.EqualsToFilter;
import fr.inria.jfilter.operators.LessThanFilter;
import fr.inria.jfilter.operators.MoreThanFilter;

public class ComparableFilterTest extends FilterTestCase {
	public ComparableFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ComparableFilterTest.class);
	}

	public void testEqualsToFilterMatchString() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(
				new String[] { "firstname" }, doe.dad.firstname);
		assertContains(doe.dad.firstname, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchString() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(
				new String[] { "firstname" }, "Bob");
		assertContains(doe.dad.firstname, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertTrue(filter.filter(doe.members(), null).isEmpty());
		assertTrue(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterMatchInt() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "age" }, "30");
		assertContains(doe.dad.age, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testMoreThanFilterMatchInt() throws ParsingException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "age" }, "19");
		assertContains(doe.dad.age, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testMoreThanFilterDoNotMatchInt() throws ParsingException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "age" }, "31");
		assertContains(doe.dad.age, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertTrue(filter.filter(doe.members(), null).isEmpty());
		assertTrue(filter.filter(doe.members(), null).isEmpty());
	}

	public void testLessThanFilterMatchInt() throws ParsingException {
		LessThanFilter filter = new LessThanFilter(new String[] { "age" }, "31");
		assertContains(doe.dad.age, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testLessThanFilterDoNotMatchInt() throws ParsingException {
		LessThanFilter filter = new LessThanFilter(new String[] { "age" }, "5");
		assertContains(doe.dad.age, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertTrue(filter.filter(doe.members(), null).isEmpty());
		assertTrue(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterMatchBoolean() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "male" },
				"true");
		assertContains(doe.dad.male, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchBoolean() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "male" },
				"false");
		assertContains(doe.dad.male, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertSize(2,filter.filter(doe.members(), null));
	}

	public void testEqualsToFilterMatchDouble() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "height" },
				"1.8");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchDouble() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "height" },
				"1.9");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertTrue(filter.filter(doe.members(), null).isEmpty());
		assertTrue(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterMatchEmbeddedLong() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "address",
				"postcode" }, "10014");
		assertContains(doe.dad.address.postcode, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testEqualsToFilterMatchEmbeddedCollection() throws ParsingException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "childs",
				"size" }, "2");
		assertContains(doe.dad.childs.size(), filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testMoreThanFilterMatchDouble() throws ParsingException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "height" },
				"1.7");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testMoreThanFilterDoNotMatchDouble() throws ParsingException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "height" },
				"1.9");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertTrue(filter.filter(doe.members(), null).isEmpty());
		assertTrue(filter.filter(doe.members(), null).isEmpty());
	}

	public void testLessThanFilterMatchDouble() throws ParsingException {
		LessThanFilter filter = new LessThanFilter(new String[] { "height" },
				"1.9");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertTrue(filter.match(doe.dad, null));
		assertTrue(filter.match(doe.dad.map(), null));
		assertTrue(filter.match(doe.dad.properties(), null));
		assertFalse(filter.filter(doe.members(), null).isEmpty());
		assertFalse(filter.filter(doe.members(), null).isEmpty());
	}

	public void testLessThanFilterDoNotMatchDouble() throws ParsingException {
		LessThanFilter filter = new LessThanFilter(new String[] { "height" },
				"1.7");
		assertContains(doe.dad.height, filter.getLeftValue(doe.dad));
		assertFalse(filter.match(doe.dad, null));
		assertFalse(filter.match(doe.dad.map(), null));
		assertFalse(filter.match(doe.dad.properties(), null));
		assertSize(3,filter.filter(doe.members(), null));
		assertSize(3,filter.filter(doe.members(), null));
	}
}
