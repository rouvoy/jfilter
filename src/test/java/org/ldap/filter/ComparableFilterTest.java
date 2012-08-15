package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.lib.EqualsToFilter;

public class ComparableFilterTest extends FilterTestCase {
	public ComparableFilterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ComparableFilterTest.class);
	}

	public void testEqualsToFilterMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("firstname", bean.firstname);
		assertEquals(bean.firstname, filter.getValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchString() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("firstname", "Bob");
		assertEquals(bean.firstname, filter.getValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchInt() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("age", "20");
		assertEquals(bean.age, filter.getValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchInt() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("age", "21");
		assertEquals(bean.age, filter.getValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("male", "true");
		assertEquals(bean.male, filter.getValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchBoolean() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("male", "false");
		assertEquals(bean.male, filter.getValue(bean));
		assertFalse(filter.match(bean));
	}

	public void testEqualsToFilterMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("height", "1.8");
		assertEquals(bean.height, filter.getValue(bean));
		assertTrue(filter.match(bean));
	}

	public void testEqualsToFilterDoNotMatchDouble() throws FilterException {
		EqualsToFilter filter = new EqualsToFilter("height", "1.9");
		assertEquals(bean.height, filter.getValue(bean));
		assertFalse(filter.match(bean));
	}
}
