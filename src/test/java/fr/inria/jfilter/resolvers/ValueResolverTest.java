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

import static fr.inria.jfilter.resolvers.ValueResolver.instance;
import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.FilterTestCase;
import fr.inria.jfilter.ParsingException;

/**
 * Unit test for Type Resolver class.
 */
public class ValueResolverTest extends FilterTestCase {
	public ValueResolverTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(ValueResolverTest.class);
	}

	public void testResolveWithUnknownKey() throws ParsingException {
		assertNull(instance.resolve(doe.dad, "abc"));
	}

	public void testResolveFieldAsInt() throws ParsingException {
		assertContains(doe.dad.age, instance.resolve(doe.dad, "age"));
	}

	public void testResolveMethodAsString() throws ParsingException {
		assertContains(doe.getName(), instance.resolve(doe, "name"));
	}

	public void testResolveMapWithEmbeddedBean() {
		assertContains(
				doe.dad.address.postcode,
				instance.resolve(doe.dad.map(), new String[] { "address",
						"postcode" }));
	}

	public void testResolvePathAsString() {
		assertContains(doe.dad.address.city, instance.resolve(doe,
				new String[] { "dad", "address", "city" }));
	}

	public void testResolvePathAsEmptyCollection() {
		assertContains(doe.dad.hobbies.size(), instance.resolve(doe,
				new String[] { "dad", "hobbies", "size" }));
	}

	public void testResolvePathAsInt() {
		assertContains(doe.dad.childs.size(),
				instance.resolve(doe, new String[] { "dad", "childs", "size" }));
	}

	public void testResolvePathAsBeans() {
		assertContains(
				doe.dad.address.country(),
				instance.resolve(doe, new String[] { "dad", "address",
						"country" }));
	}

	public void testResolveMultiPathAsBean() {
		assertContains(doe.dad.address,
				instance.resolve(doe, new String[] { "childs", "address" }));
	}

	public void testResolveMultiPathAsString() {
		assertContains(
				doe.dad.address.country(),
				instance.resolve(doe, new String[] { "childs", "address",
						"country" }));
	}

	public void testResolveLongPathAsBeans() {
		assertContains(
				doe.dad.address.postcode,
				instance.resolve(doe, new String[] { "dad", "childs", "family",
						"mom", "childs", "family", "childs", "address",
						"postcode" }));
	}

	public void ResolveAddressClassType() {
		assertContains(
				Person.Address.class,
				instance.resolve(doe, new String[] { "dad", "address",
						"objectClass" }));
	}

	public void ResolvePostcodeClassType() {
		assertContains(
				Integer.class,
				instance.resolve(doe, new String[] { "childs", "address",
						"postcode", "objectClass" }));
	}
}
