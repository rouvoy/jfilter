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
package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.lib.EqualsToFilter;
import org.ldap.filter.lib.LessThanFilter;
import org.ldap.filter.lib.MoreThanFilter;

public class ComparableFilterTest extends FilterTestCase {
	public ComparableFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ComparableFilterTest.class);
	}

	public void testEqualsToFilterMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("firstname", bean.firstname);
		assertEquals(bean.firstname, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("firstname", "Bob");
		assertEquals(bean.firstname, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchInt() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("age", "20");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testMoreThanFilterMatchInt() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter("age", "19");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testMoreThanFilterDoNotMatchInt() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter("age", "21");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testLessThanFilterMatchInt() throws FilterException {
		LessThanFilter filter = new LessThanFilter("age", "21");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testLessThanFilterDoNotMatchInt() throws FilterException {
		LessThanFilter filter = new LessThanFilter("age", "19");
		assertEquals(bean.age, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("male", "true");
		assertEquals(bean.male, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("male", "false");
		assertEquals(bean.male, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("height", "1.8");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("height", "1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}
	
	public void testMoreThanFilterMatchDouble() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter("height", "1.7");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}
	
	public void testMoreThanFilterDoNotMatchDouble() throws FilterException {
		MoreThanFilter filter = new MoreThanFilter("height", "1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testLessThanFilterMatchDouble() throws FilterException {
		LessThanFilter filter = new LessThanFilter("height", "1.9");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertTrue(filter.match(bean));
	}
	
	public void testLessThanFilterDoNotMatchDouble() throws FilterException {
		LessThanFilter filter = new LessThanFilter("height", "1.7");
		assertEquals(bean.height, filter.getLeftValue(bean));
		assertFalse(filter.match(bean));
	}
}
