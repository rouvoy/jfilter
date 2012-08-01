package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LdapFilterTest extends TestCase {
	public LdapFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(LdapFilterTest.class);
	}

	public static class Person {
		String firstname = "John", name = "Doe";
		int age = 20;
		boolean male = true;
		double height = 1.8 ; 
	}

	private Person x = new Person();

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(FilterParser.instance.parse("(name=Doe)"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = FilterParser.instance.parse("(name=Doe)");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = FilterParser.instance.parse("(male=true)");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = FilterParser.instance.parse("(age=20)");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = FilterParser.instance.parse("(height=1.8)");
		assertTrue(filter.match(x));
	}

	public void testFilterNotEqualsString() throws FilterException {
		Filter filter = FilterParser.instance.parse("(firstname~Bob)");
		assertTrue(filter.match(x));
	}

	public void testFilterNotEqualsBolean() throws FilterException {
		Filter filter = FilterParser.instance.parse("(male~false)");
		assertTrue(filter.match(x));
	}

	public void testFilterNotEqualsInt() throws FilterException {
		Filter filter = FilterParser.instance.parse("(age~19)");
		assertTrue(filter.match(x));
	}

	public void testFilterNotEqualsDouble() throws FilterException {
		Filter filter = FilterParser.instance.parse("(height~1.9)");
		assertTrue(filter.match(x));
	}

	public void testFilterMoreThanInt() throws FilterException {
		Filter filter = FilterParser.instance.parse("(age>18)");
		assertTrue(filter.match(x));
	}

	public void testFilterLessThanInt() throws FilterException {
		Filter filter = FilterParser.instance.parse("(age<30)");
		assertTrue(filter.match(x));
	}

	public void testFilterMoreThanDouble() throws FilterException {
		Filter filter = FilterParser.instance.parse("(height>1.2)");
		assertTrue(filter.match(x));
	}

	public void testFilterLessThanDouble() throws FilterException {
		Filter filter = FilterParser.instance.parse("(height<1.9)");
		assertTrue(filter.match(x));
	}
}
