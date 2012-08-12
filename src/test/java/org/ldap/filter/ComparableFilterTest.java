package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ldap.filter.lib.EqualsFilter;

public class ComparableFilterTest extends TestCase {
		public ComparableFilterTest(String testName) {
			super(testName);
		}

		public static class Person {
			String firstname = "John", name = "Doe";
			int age = 20;
			boolean male = true;
			double height = 1.8;

			String getLastname() {
				return this.name;
			}
		}

		private final Person x = new Person();

		/**
		 * @return the suite of tests being tested
		 */
		public static Test suite() {
			return new TestSuite(ComparableFilterTest.class);
		}

		public void testEqualsFilterMatchString() throws FilterException {
			EqualsFilter filter = new EqualsFilter("firstname","John");
			assertEquals("John", filter.getValue(x));
			assertTrue(filter.match(x));
		}

		public void testEqualsFilterDoNotMatchString() throws FilterException {
			EqualsFilter filter = new EqualsFilter("firstname","Bob");
			assertEquals("John", filter.getValue(x));
			assertFalse(filter.match(x));
		}

		public void testEqualsFilterMatchInt() throws FilterException {
			EqualsFilter filter = new EqualsFilter("age","20");
			assertEquals(20, filter.getValue(x));
			assertTrue(filter.match(x));
		}

		public void testEqualsFilterDoNotMatchInt() throws FilterException {
			EqualsFilter filter = new EqualsFilter("age","21");
			assertEquals(20, filter.getValue(x));
			assertFalse(filter.match(x));
		}

		public void testEqualsFilterMatchBoolean() throws FilterException {
			EqualsFilter filter = new EqualsFilter("male","true");
			assertEquals(true, filter.getValue(x));
			assertTrue(filter.match(x));
		}

		public void testEqualsFilterDoNotMatchBoolean() throws FilterException {
			EqualsFilter filter = new EqualsFilter("male","false");
			assertEquals(true, filter.getValue(x));
			assertFalse(filter.match(x));
		}

		public void testEqualsFilterMatchDouble() throws FilterException {
			EqualsFilter filter = new EqualsFilter("height","1.8");
			assertEquals(1.8, filter.getValue(x));
			assertTrue(filter.match(x));
		}

		public void testEqualsFilterDoNotMatchDouble() throws FilterException {
			EqualsFilter filter = new EqualsFilter("height","1.9");
			assertEquals(1.8, filter.getValue(x));
			assertFalse(filter.match(x));
		}
}
