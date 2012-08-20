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
package fr.inria.jfilter.parsers;

import fr.inria.jfilter.FilterException;
import fr.inria.jfilter.FilterParser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DefaultFilterTest extends TestCase {
	public DefaultFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(DefaultFilterTest.class);
	}

	public void testJSONFilterParser() throws FilterException {
		assertNotNull(FilterParser.instance.parse("{name:Doe}"));
	}

	public void testLDAPFilterParser() throws FilterException {
		assertNotNull(FilterParser.instance.parse("(name=Doe)"));
	}
}
