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
package org.ldap.filter.operators;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.FilterException;
import org.ldap.filter.FilterTestCase;
import org.ldap.filter.lib.operators.WildcardFilter;

public class WildcardFilterTest extends FilterTestCase {
	public WildcardFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(WildcardFilterTest.class);
	}

	public void testExistFilter() throws FilterException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "*");
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testFilterStartsWith() throws FilterException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "J*");
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testFilterEndsWith() throws FilterException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "*n");
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}

	public void testFilterContains() throws FilterException {
		WildcardFilter filter = new WildcardFilter(new String[] { "firstname" }, "*oh*");
		assertTrue(filter.match(bean));
		assertFalse(filter.filter(list).isEmpty());
		assertFalse(filter.filter(set).isEmpty());
	}
}
