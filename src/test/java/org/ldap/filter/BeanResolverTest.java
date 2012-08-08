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
		Option<Object> val = beanResolver.getValue(x, "firstname");
		assertEquals("John", val.get());
	}

	public void testResolveFieldBeanWithInt() throws FilterException {
		Option<Object> val = beanResolver.getValue(x, "age");
		assertEquals(20, val.get());
	}

	public void testResolveFieldBeanWithDouble() throws FilterException {
		Option<Object> val = beanResolver.getValue(x, "height");
		assertEquals(1.8, val.get());
	}

	public void testResolveFieldBeanWithBoolean() throws FilterException {
		Option<Object> val = beanResolver.getValue(x, "male");
		assertEquals(true, val.get());
	}

	public void testResolveMethodBeanWithString() throws FilterException {
		Option<Object> val = beanResolver.getValue(x, "lastname");
		assertEquals("Doe", val.get());
	}
}
