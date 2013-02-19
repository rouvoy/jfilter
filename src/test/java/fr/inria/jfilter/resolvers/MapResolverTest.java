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

import static fr.inria.jfilter.resolvers.MapResolver.map;
import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.FilterTestCase;
import fr.inria.jfilter.ParsingException;

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

	public void testResolveWithUnknownKey() throws ParsingException {
		assertNull(MapResolver.map.resolve(doe.map(), "abc"));
	}

	public void testResolveFieldAsBean() throws ParsingException {
		assertContains(doe.dad.map(), map.resolve(doe.map(), "dad"));
	}

	public void testResolveFieldAsString() throws ParsingException {
		assertContains(doe.dad.firstname,
				map.resolve(doe.dad.map(), "firstname"));
	}

	public void testResolveFieldAsInt() throws ParsingException {
		assertContains(doe.dad.age, map.resolve(doe.dad.map(), "age"));
	}

	public void testResolveFieldAsDouble() throws ParsingException {
		assertContains(doe.dad.height, map.resolve(doe.dad.map(), "height"));
	}

	public void testResolveFieldAsBoolean() throws ParsingException {
		assertContains(doe.dad.male, map.resolve(doe.dad.map(), "male"));
	}

	public void testResolveGetterAsString() throws ParsingException {
		assertContains(doe.getName(), map.resolve(doe.map(), "name"));
	}

	public void testResolveMethodAsCollection() throws ParsingException {
		assertIs(doe.members(), map.resolve(doe.map(), "members"));
	}
}
