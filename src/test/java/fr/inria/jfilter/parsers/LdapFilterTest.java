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

import static fr.inria.jfilter.FilterParser.ldap;


import fr.inria.jfilter.Filter;
import fr.inria.jfilter.FilterException;
import fr.inria.jfilter.FilterTestCase;

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
		assertNotNull(ldap.parse("(lastname=Doe)"));
	}

	public void testFilterParseSimplest() throws FilterException {
		assertNotNull(ldap.parse("lastname=Doe"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = ldap.parse("(lastname=Doe)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDoNotEqualsString() throws FilterException {
		Filter filter = ldap.parse("!lastname=Smith");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = ldap.parse("( lastname = Doe )");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithWildcard() throws FilterException {
		Filter filter = ldap.parse("(lastname=D*)");
		assertTrue(filter.match(doe.dad));
	}
	
	public void testFilterExist() throws FilterException {
		Filter filter = ldap.parse("(lastname=*)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDoNotExist() throws FilterException {
		Filter filter = ldap.parse("!(title=*)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = ldap.parse("(male=true)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = ldap.parse("(age=30)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = ldap.parse("(height=1.8)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersString() throws FilterException {
		Filter filter = ldap.parse("(firstname~Bob)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersBolean() throws FilterException {
		Filter filter = ldap.parse("(male~false)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersInt() throws FilterException {
		Filter filter = ldap.parse("(age~19)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersDouble() throws FilterException {
		Filter filter = ldap.parse("(height~1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreThanInt() throws FilterException {
		Filter filter = ldap.parse("(age>18)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreOrEqualsInt() throws FilterException {
		Filter filter = ldap.parse("(age>=20)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessThanInt() throws FilterException {
		Filter filter = ldap.parse("(age<31)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreThanDouble() throws FilterException {
		Filter filter = ldap.parse("(height>1.2)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessThanDouble() throws FilterException {
		Filter filter = ldap.parse("(height<1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessOrEqualsDouble() throws FilterException {
		Filter filter = ldap.parse("(height<=1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsEmbeddedString() throws FilterException {
		Filter filter = ldap.parse("(address.city=New York)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsEmbeddedInt() throws FilterException {
		Filter filter = ldap.parse("(address.postcode=10014)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterNotEqualsString() throws FilterException {
		Filter filter = ldap.parse("(!(lastname=Don))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterAndString() throws FilterException {
		Filter filter = ldap.parse("(&(lastname=Doe)(firstname=John))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterOrString() throws FilterException {
		Filter filter = ldap.parse("(|(lastname=Doe)(age>10))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterBySimplePerson() throws FilterException {
		Filter filter = ldap.parse("(objectClass=Person)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterByPerson() throws FilterException {
		Filter filter = ldap.parse("(objectClass=fr.inria.jfilter.FilterTestCase.Person)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterByPartialPerson() throws FilterException {
		Filter filter = ldap.parse("(objectClass=*.Person)");
		assertTrue(filter.match(doe.dad));
	}
}
