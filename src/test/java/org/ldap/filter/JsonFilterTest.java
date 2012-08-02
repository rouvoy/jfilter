package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class JsonFilterTest extends TestCase {
	public JsonFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JsonFilterTest.class);
	}

	public static class Person {
		String firstname = "John", name = "Doe";
		int age = 20;
		boolean male = true;
		double height = 1.8 ; 
	}

	private Person x = new Person();

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(FilterParser.json.parse("{name:Doe}"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = FilterParser.json.parse("{name:Doe}");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = FilterParser.json.parse("{male:true}");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = FilterParser.json.parse("{age:20}");
		assertTrue(filter.match(x));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = FilterParser.json.parse("{height:1.8}");
		assertTrue(filter.match(x));
	}
}
