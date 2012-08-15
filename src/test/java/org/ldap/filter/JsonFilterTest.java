package org.ldap.filter;

import static org.ldap.filter.FilterParser.json;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit tests for JSON-like filters.
 */
public class JsonFilterTest extends FilterTestCase {
	public JsonFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(JsonFilterTest.class);
	}

	public void testFilterParseSimple() throws FilterException {
		assertNotNull(json.parse("{name:Doe}"));
	}

	public void testFilterEqualsString() throws FilterException {
		Filter filter = json.parse("{name:Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithSpace() throws FilterException {
		Filter filter = json.parse("{ name : Doe }");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithValueQuotes() throws FilterException {
		Filter filter = json.parse("{name:\"Doe\"}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsStringWithKeyQuotes() throws FilterException {
		Filter filter = json.parse("{\"name\":Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsBolean() throws FilterException {
		Filter filter = json.parse("{male:true}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsInt() throws FilterException {
		Filter filter = json.parse("{age:20}");
		assertTrue(filter.match(bean));
	}

	public void testFilterEqualsDouble() throws FilterException {
		Filter filter = json.parse("{height:1.8}");
		assertTrue(filter.match(bean));
	}

	public void testFilterSequence() throws FilterException {
		Filter filter = json.parse("{firstname:John,name:Doe}");
		assertTrue(filter.match(bean));
	}

	public void testFilterStarFilter() throws FilterException {
		Filter filter = json.parse("{\"filter\":\"*:W\"}");
		assertTrue(filter.match(bean));
	}
}
