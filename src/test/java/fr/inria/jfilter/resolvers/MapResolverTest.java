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

	public void testResolveWithUnknownKey() throws FilterException {
		assertEmpty(MapResolver.map.getValues(doe.map(), "abc"));
	}

	public void testResolveFieldAsBean() throws FilterException {
		Collection<Object> val = MapResolver.map.getValues(doe.map(), "dad");
		assertContains(doe.dad.map(), val);
	}

	public void testResolveFieldAsString() throws FilterException {
		Collection<Object> val = MapResolver.map.getValues(doe.dad.map(),
				"firstname");
		assertContains(doe.dad.firstname, val);
	}

	public void testResolveFieldAsInt() throws FilterException {
		Collection<Object> val = MapResolver.map
				.getValues(doe.dad.map(), "age");
		assertContains(doe.dad.age, val);
	}

	public void testResolveFieldAsDouble() throws FilterException {
		Collection<Object> val = MapResolver.map.getValues(doe.dad.map(),
				"height");
		assertContains(doe.dad.height, val);
	}

	public void testResolveFieldAsBoolean() throws FilterException {
		Collection<Object> val = MapResolver.map.getValues(doe.dad.map(),
				"male");
		assertContains(doe.dad.male, val);
	}

	public void testResolveGetterAsString() throws FilterException {
		Collection<Object> val = MapResolver.map.getValues(doe.map(), "name");
		assertContains(doe.getName(), val);
	}

	public void testResolveMethodAsCollection() throws FilterException {
		Collection<Object> val = MapResolver.map
				.getValues(doe.map(), "members");
		assertContains(doe.members(), val);
	}

	public void testResolvePathAsString() {
		Collection<Object> val = MapResolver.map.getValues(doe.map(),
				new String[] { "dad", "address", "city" });
		assertContains(doe.dad.address.city, val);
	}

	public void testResolvePathAsBeans() {
		Collection<Object> val = MapResolver.map.getValues(doe.map(),
				new String[] { "dad", "address",  });
		assertContains(doe.dad.address.map(), val);
	}

	public void testResolvePathAsInt() {
		Collection<Object> val = MapResolver.map.getValues(doe.map(),
				new String[] { "dad", "address", "postcode" });
		assertContains(doe.dad.address.postcode, val);
	}
}
