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
import fr.inria.jfilter.ParsingException;
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

	public void testFilterParseSimple() throws ParsingException {
		assertNotNull(ldap.parse("(lastname=Doe)"));
	}

	public void testFilterParseSimplest() throws ParsingException {
		assertNotNull(ldap.parse("lastname=Doe"));
	}

	public void testFilterEqualsString() throws ParsingException {
		Filter filter = ldap.parse("(lastname=Doe)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDoNotEqualsString() throws ParsingException {
		Filter filter = ldap.parse("!lastname=Smith");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithSpace() throws ParsingException {
		Filter filter = ldap.parse("( lastname = Doe )");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsStringWithWildcard() throws ParsingException {
		Filter filter = ldap.parse("(lastname=D*)");
		assertTrue(filter.match(doe.dad));
	}
	
	public void testFilterExist() throws ParsingException {
		Filter filter = ldap.parse("(lastname=*)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDoNotExist() throws ParsingException {
		Filter filter = ldap.parse("!(title=*)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsBolean() throws ParsingException {
		Filter filter = ldap.parse("(male=true)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsInt() throws ParsingException {
		Filter filter = ldap.parse("(age=30)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsDouble() throws ParsingException {
		Filter filter = ldap.parse("(height=1.8)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersString() throws ParsingException {
		Filter filter = ldap.parse("(firstname~Bob)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersBolean() throws ParsingException {
		Filter filter = ldap.parse("(male~false)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersInt() throws ParsingException {
		Filter filter = ldap.parse("(age~19)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterDiffersDouble() throws ParsingException {
		Filter filter = ldap.parse("(height~1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreThanInt() throws ParsingException {
		Filter filter = ldap.parse("(age>18)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreOrEqualsInt() throws ParsingException {
		Filter filter = ldap.parse("(age>=20)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessThanInt() throws ParsingException {
		Filter filter = ldap.parse("(age<31)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterMoreThanDouble() throws ParsingException {
		Filter filter = ldap.parse("(height>1.2)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessThanDouble() throws ParsingException {
		Filter filter = ldap.parse("(height<1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterLessOrEqualsDouble() throws ParsingException {
		Filter filter = ldap.parse("(height<=1.9)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsEmbeddedString() throws ParsingException {
		Filter filter = ldap.parse("(address.city=New York)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEqualsEmbeddedInt() throws ParsingException {
		Filter filter = ldap.parse("(address.postcode=10014)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterNotEqualsString() throws ParsingException {
		Filter filter = ldap.parse("(!(lastname=Don))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterEmptyCollection() throws ParsingException {
		Filter filter = ldap.parse("(hobbies.size=0)");
		assertTrue(filter.match(doe.dad));
	}
	
	public void testFilterByAnyLastname() throws ParsingException {
		Filter filter = ldap.parse("(dad.lastname=*)");
		assertTrue(filter.match(doe));
	}

	public void testFilterCollectionByAnyLastname() throws ParsingException {
		Filter filter = ldap.parse("(members.lastname=*)");
		assertTrue(filter.match(doe));
	}

	public void testFilterAndString() throws ParsingException {
		Filter filter = ldap.parse("(&(lastname=Doe)(firstname=John))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterOrString() throws ParsingException {
		Filter filter = ldap.parse("(|(lastname=Doe)(age>10))");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterBySimplePerson() throws ParsingException {
		Filter filter = ldap.parse("(objectClass=Person)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterByPerson() throws ParsingException {
		Filter filter = ldap.parse("(objectClass=fr.inria.jfilter.FilterTestCase.Person)");
		assertTrue(filter.match(doe.dad));
	}

	public void testFilterByPartialPerson() throws ParsingException {
		Filter filter = ldap.parse("(objectClass=*.Person)");
		assertTrue(filter.match(doe.dad));
	}
}
