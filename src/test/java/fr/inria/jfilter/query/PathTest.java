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
package fr.inria.jfilter.query;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.inria.jfilter.FilterTestCase;
import fr.inria.jfilter.ParsingException;
import fr.inria.jfilter.operators.EqualsToFilter;

public class PathTest extends FilterTestCase {
	public PathTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(PathTest.class);
	}

	public void testSimplePath() throws ParsingException {
		Path path = new Path();
		path.expand(new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "firstname" }, doe.dad.firstname)));
		assertFalse(path.select(doe).isEmpty());
		assertSize(1, path.select(doe));
		assertContains(doe.dad, path.select(doe));
	}

	public void testComplexPath() throws ParsingException {
		Path path = new Path();
		path.expand(new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "firstname" }, doe.dad.firstname)));
		path.expand(new Step(new String[] {}, new EqualsToFilter(
				new String[] { "age" }, "" + doe.dad.age)));
		path.expand(new Step(new String[] {}, new EqualsToFilter(new String[] {
				"address", "postcode" }, "10014")));
		assertFalse(path.select(doe).isEmpty());
		assertSize(1, path.select(doe));
		assertContains(doe.dad, path.select(doe));
	}
}
