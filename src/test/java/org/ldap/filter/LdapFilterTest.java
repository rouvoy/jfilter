package org.ldap.filter;

import static org.ldap.filter.FilterParser.ldap;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test for LDAP-like filters.
 */
public class LdapFilterTest extends FilterTestCase {
	public LdapFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(LdapFilterTest.class);
	}

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(ldap.parse("(name=Doe)"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = ldap.parse("(name=Doe)");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = ldap.parse("( name = Doe )");
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
