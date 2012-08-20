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


import fr.inria.jfilter.FilterException;
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

	public void testEqualsToFilterMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(
				new String[] { "firstname" }, bean.firstname);
		assertEquals(bean.firstname, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(
				new String[] { "firstname" }, "Bob");
		assertEquals(bean.firstname, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterMatchInt() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "age" }, "20");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testMoreThanFilterMatchInt() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "age" }, "19");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testMoreThanFilterDoNotMatchInt() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "age" }, "21");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testLessThanFilterMatchInt() throws FilterException {
		LessThanFilter filter = new LessThanFilter(new String[] { "age" }, "21");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testLessThanFilterDoNotMatchInt() throws FilterException {
		LessThanFilter filter = new LessThanFilter(new String[] { "age" }, "19");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "male" },
				"true");
		assertEquals(bean.male, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "male" },
				"false");
		assertEquals(bean.male, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "height" },
				"1.8");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterDoNotMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "height" },
				"1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testEqualsToFilterMatchEmbeddedLong() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter(new String[] { "home",
				"postcode" }, "10014");
		assertEquals(bean.home.postcode, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testMoreThanFilterMatchDouble() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "height" },
				"1.7");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testMoreThanFilterDoNotMatchDouble() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter(new String[] { "height" },
				"1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}

	public void testLessThanFilterMatchDouble() throws FilterException {
		LessThanFilter filter = new LessThanFilter(new String[] { "height" },
				"1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testLessThanFilterDoNotMatchDouble() throws FilterException {
		LessThanFilter filter = new LessThanFilter(new String[] { "height" },
				"1.7");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
		assertTrue(filter.filter(list).isEmpty());
		assertTrue(filter.filter(set).isEmpty());
	}
}
