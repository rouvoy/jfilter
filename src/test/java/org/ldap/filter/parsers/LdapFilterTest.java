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
package org.ldap.filter.parsers;

import static org.ldap.filter.FilterParser.ldap;

import org.ldap.filter.Filter;
import org.ldap.filter.FilterException;
import org.ldap.filter.FilterTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for LDAP-like filters.
 */
public class LdapFilterTest extends FilterTestCase {
	public LdapFilterTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(LdapFilterTest.class);
	}

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(ldap.parse("(name=Doe)"));
	}

	public void testFilterParseSimplest() throws FilterException {
		assertNotNull(ldap.parse("name=Doe"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = ldap.parse("(name=Doe)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDoNotEqualsString() throws FilterException {
		Filter filter = ldap.parse("!name=Smith");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = ldap.parse("( name = Doe )");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithWildcard() throws FilterException {
		Filter filter = ldap.parse("(name=D*)");
		assertTrue(filter.match(bean));
	}
	
	public void testFilterExist() throws FilterException {
		Filter filter = ldap.parse("(name=*)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDoNotExist() throws FilterException {
		Filter filter = ldap.parse("!(title=*)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = ldap.parse("(male=true)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = ldap.parse("(age=20)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = ldap.parse("(height=1.8)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDiffersString() throws FilterException {
		Filter filter = ldap.parse("(firstname~Bob)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDiffersBolean() throws FilterException {
		Filter filter = ldap.parse("(male~false)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDiffersInt() throws FilterException {
		Filter filter = ldap.parse("(age~19)");
		assertTrue(filter.match(bean));
	}

	public void testFilterDiffersDouble() throws FilterException {
		Filter filter = ldap.parse("(height~1.9)");
		assertTrue(filter.match(bean));
	}

	public void testFilterMoreThanInt() throws FilterException {
		Filter filter = ldap.parse("(age>18)");
		assertTrue(filter.match(bean));
	}

	public void testFilterMoreOrEqualsInt() throws FilterException {
		Filter filter = ldap.parse("(age>=20)");
		assertTrue(filter.match(bean));
	}

	public void testFilterLessThanInt() throws FilterException {
		Filter filter = ldap.parse("(age<30)");
		assertTrue(filter.match(bean));
	}

	public void testFilterMoreThanDouble() throws FilterException {
		Filter filter = ldap.parse("(height>1.2)");
		assertTrue(filter.match(bean));
	}

	public void testFilterLessThanDouble() throws FilterException {
		Filter filter = ldap.parse("(height<1.9)");
		assertTrue(filter.match(bean));
	}

	public void testFilterLessOrEqualsDouble() throws FilterException {
		Filter filter = ldap.parse("(height<=1.9)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsEmbeddedString() throws FilterException {
		Filter filter = ldap.parse("(home.city=New York)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsEmbeddedInt() throws FilterException {
		Filter filter = ldap.parse("(home.postcode=10014)");
		assertTrue(filter.match(bean));
	}

	public void testFilterNotEqualsString() throws FilterException {
		Filter filter = ldap.parse("(!(name=Don))");
		assertTrue(filter.match(bean));
	}

	public void testFilterAndString() throws FilterException {
		Filter filter = ldap.parse("(&(name=Doe)(firstname=John))");
		assertTrue(filter.match(bean));
	}

	public void testFilterOrString() throws FilterException {
		Filter filter = ldap.parse("(|(name=Doe)(age>10))");
		assertTrue(filter.match(bean));
	}
}
