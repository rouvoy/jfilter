package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ldap.filter.lib.BeanResolver;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.ValueResolver;

/**
 * Unit test for Bean Resolver class.
 */
public class BeanResolverTest extends TestCase {
	public BeanResolverTest(String testName) {
		super(testName);
	}

	private final ValueResolver beanResolver = new BeanResolver();

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
		return new TestSuite(BeanResolverTest.class);
	}

	public void testResolveUnknownKey() throws FilterException {
		Option<Object> val = beanResolver.getValue(x, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveFieldBeanWithString() throws FilterException {
		Object val = beanResolver.getValue(x, "firstname").get();
		assertEquals("John", val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveFieldBeanWithInt() throws FilterException {
		Object val = beanResolver.getValue(x, "age").get();
		assertEquals(20, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveFieldBeanWithDouble() throws FilterException {
		Object val = beanResolver.getValue(x, "height").get();
		assertEquals(1.8, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveFieldBeanWithBoolean() throws FilterException {
		Object val = beanResolver.getValue(x, "male").get();
		assertEquals(true, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolveMethodBeanWithString() throws FilterException {
		Object val = beanResolver.getValue(x, "lastname").get();
		assertEquals("Doe", val);
		assertEquals(String.class, val.getClass());
	}
}
