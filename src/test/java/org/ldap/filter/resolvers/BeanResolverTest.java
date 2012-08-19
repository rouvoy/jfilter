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
package org.ldap.filter.resolvers;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.FilterException;
import org.ldap.filter.FilterTestCase;
import org.ldap.filter.lib.resolvers.ValueResolver;
import org.ldap.filter.lib.utils.None;
import org.ldap.filter.lib.utils.Option;

/**
 * Unit test for Bean Resolver class.
 */
public class BeanResolverTest extends FilterTestCase {
	public BeanResolverTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(BeanResolverTest.class);
	}

	public void testResolveUnknownKey() throws FilterException {
		Option<Object> val = ValueResolver.bean.getValue(bean, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveFieldBeanWithString() throws FilterException {
		Object val = ValueResolver.bean.getValue(bean, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveFieldBeanWithInt() throws FilterException {
		Object val = ValueResolver.bean.getValue(bean, "age").get();
		assertEquals(bean.age, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveFieldBeanWithDouble() throws FilterException {
		Object val = ValueResolver.bean.getValue(bean, "height").get();
		assertEquals(bean.height, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveFieldBeanWithBoolean() throws FilterException {
		Object val = ValueResolver.bean.getValue(bean, "male").get();
		assertEquals(bean.male, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolveMethodBeanWithString() throws FilterException {
		Object val = ValueResolver.bean.getValue(bean, "lastname").get();
		assertEquals(bean.getLastname(), val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveBeanWithEmbeddedObject() {
		Object val = ValueResolver.bean.getValue(bean, "home").get();
		assertEquals(bean.home, val);
		assertEquals(Person.Address.class, val.getClass());
	}

	public void testResolveBeanWithEmbeddedString() {
		Object val = ValueResolver.bean.getValue(bean,
				new String[] { "home", "city" }).get();
		assertEquals(bean.home.city, val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveBeanWithEmbeddedInteger() {
		Object val = ValueResolver.bean.getValue(bean,
				new String[] { "home", "postcode" }).get();
		assertEquals(bean.home.postcode, val);
		assertEquals(Integer.class, val.getClass());
	}
}
