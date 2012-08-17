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

import org.ldap.filter.lib.None;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.ValueResolver;

/**
 * Unit test for Bean Resolver class.
 */
public class MapResolverTest extends FilterTestCase {
	public MapResolverTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MapResolverTest.class);
	}

	public void testResolveUnknownKey() {
		Option<Object> val = ValueResolver.map.getValue(map, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveMapWithString() {
		Object val = ValueResolver.map.getValue(map, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveMapWithInt() {
		Object val = ValueResolver.map.getValue(map, "age").get();
		assertEquals(bean.age, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveMapWithDouble() {
		Object val = ValueResolver.map.getValue(map, "height").get();
		assertEquals(bean.height, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveMapWithBoolean() {
		Object val = ValueResolver.map.getValue(map, "male").get();
		assertEquals(bean.male, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolveMapWithObject() {
		Object val = ValueResolver.map.getValue(map, "home").get();
		assertEquals(bean.home, val);
		assertEquals(Person.Address.class, val.getClass());
	}

	public void testResolvePropertiesWithString() {
		Object val = ValueResolver.map.getValue(property, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}
}
