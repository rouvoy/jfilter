package org.ldap.filter;

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

	/**
	 * @return the suite of tests being tested
	 */
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
