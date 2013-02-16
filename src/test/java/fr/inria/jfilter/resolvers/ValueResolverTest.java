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

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.ParsingException;
import fr.inria.jfilter.FilterTestCase;

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
		assertEmpty(ValueResolver.instance.getValues(doe.dad, "abc"));
	}

	public void testResolveFieldAsInt() throws ParsingException {
		assertContains(doe.dad.age,
				ValueResolver.instance.getValues(doe.dad, "age"));
	}

	public void testResolveMethodAsString() throws ParsingException {
		assertContains(doe.getName(),
				ValueResolver.instance.getValues(doe, "name"));
	}

	public void testResolveMapWithEmbeddedBean() {
		assertContains(
				doe.dad.address.postcode,
				ValueResolver.instance.getValues(doe.dad.map(), new String[] {
						"address", "postcode" }));
	}

	public void testResolveAddressClassType() {
		assertContains(
				Person.Address.class,
				ValueResolver.instance.getValues(doe, new String[] { "dad",
						"address", "objectClass" }));
	}

	public void testResolvePostcodeClassType() {
		assertContains(
				Integer.class,
				ValueResolver.instance.getValues(doe, new String[] { "childs",
						"address", "postcode", "objectClass" }));
	}
}
