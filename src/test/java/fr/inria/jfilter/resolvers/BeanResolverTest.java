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
import fr.inria.jfilter.ParsingException;
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

	public void testResolveWithUnknownKey() throws ParsingException {
		assertEmpty(BeanResolver.bean.getValues(doe.dad, "abc"));
	}

	public void testResolveFieldAsBean() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe, "dad");
		assertContains(doe.dad, val);
	}

	public void testResolveFieldAsString() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe.dad,
				"firstname");
		assertContains(doe.dad.firstname, val);
	}

	public void testResolveFieldAsInt() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe.dad, "age");
		assertContains(doe.dad.age, val);
	}

	public void testResolveFieldAsDouble() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe.dad, "height");
		assertContains(doe.dad.height, val);
	}

	public void testResolveFieldAsBoolean() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe.dad, "male");
		assertContains(doe.dad.male, val);
	}

	public void testResolveFieldAsCollection() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe, "childs");
		assertSize(1,val);
		assertContains(doe.childs, val);
	}

	public void testResolveGetterAsString() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe, "name");
		assertContains(doe.getName(), val);
	}

	public void testResolveMethodAsCollection() throws ParsingException {
		Collection<Object> val = BeanResolver.bean.getValues(doe, "members");
		assertSize(1,val);
		assertContains(doe.members(), val);
	}

	public void testResolvePathAsString() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"dad", "address", "city" });
		assertSize(1,val);
		assertContains(doe.dad.address.city, val);
	}

	public void testResolvePathAsEmptyCollection() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"dad", "hobbies", "size" });
		assertSize(1,val);
		assertContains(doe.dad.hobbies.size(), val);
	}

	public void testResolvePathAsInt() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"dad", "childs", "size" });
		assertSize(1,val);
		assertContains(doe.dad.childs.size(), val);
	}
	
	public void testResolvePathAsBeans() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"dad", "address", "country" });
		assertSize(1,val);
		assertContains(doe.dad.address.country(), val);
	}

	public void testResolveMultiPathAsBean() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"childs", "address" });
		assertSize(1,val);
		assertContains(doe.dad.address, val);
	}

	public void testResolveMultiPathAsString() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"childs", "address", "country" });
		assertSize(1,val);
		assertContains(doe.dad.address.country(), val);
	}

	public void testResolveLongPathAsBeans() {
		Collection<Object> val = BeanResolver.bean.getValues(doe, new String[] {
				"dad", "childs", "family", "mom", "childs", "family", "childs",
				"address", "postcode" });
		assertSize(1,val);
		assertContains(doe.dad.address.postcode, val);
	}
}
