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
package fr.inria.jfilter.resolvers;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.FilterException;
import fr.inria.jfilter.FilterTestCase;

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
		assertEmpty(BeanResolver.bean.getValue(bean, "abc"));
	}

	public void testResolveFieldBeanWithString() throws FilterException {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "firstname");
		assertContains(bean.firstname, val);
	}

	public void testResolveFieldBeanWithInt() throws FilterException {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "age");
		assertContains(bean.age, val);
	}

	public void testResolveFieldBeanWithDouble() throws FilterException {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "height");
		assertContains(bean.height, val);
	}

	public void testResolveFieldBeanWithBoolean() throws FilterException {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "male");
		assertContains(bean.male, val);
	}

	public void testResolveMethodBeanWithString() throws FilterException {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "lastname");
		assertContains(bean.getLastname(), val);
	}

	public void testResolveBeanWithEmbeddedObject() {
		Collection<Object> val = BeanResolver.bean.getValue(bean, "home");
		assertContains(bean.home, val);
	}

	public void testResolveBeanWithEmbeddedString() {
		Collection<Object> val = BeanResolver.bean.getValue(bean, new String[] {
				"home", "city" });
		assertContains(bean.home.city, val);
	}

	public void testResolveBeanWithEmbeddedInteger() {
		Collection<Object> val = BeanResolver.bean.getValue(bean, new String[] {
				"home", "postcode" });
		assertContains(bean.home.postcode, val);
	}
}
