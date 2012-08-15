package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.lib.BeanResolver;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.ValueResolver;

/**
 * Unit test for Bean Resolver class.
 */
public class BeanResolverTest extends FilterTestCase {
	public BeanResolverTest(String testName) {
		super(testName);
	}

	private final ValueResolver beanResolver = new BeanResolver();

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(BeanResolverTest.class);
	}

	public void testResolveUnknownKey() throws FilterException {
		Option<Object> val = beanResolver.getValue(bean, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveFieldBeanWithString() throws FilterException {
		Object val = beanResolver.getValue(bean, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveFieldBeanWithInt() throws FilterException {
		Object val = beanResolver.getValue(bean, "age").get();
		assertEquals(bean.age, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveFieldBeanWithDouble() throws FilterException {
		Object val = beanResolver.getValue(bean, "height").get();
		assertEquals(bean.height, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveFieldBeanWithBoolean() throws FilterException {
		Object val = beanResolver.getValue(bean, "male").get();
		assertEquals(bean.male, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolveMethodBeanWithString() throws FilterException {
		Object val = beanResolver.getValue(bean, "lastname").get();
		assertEquals(bean.getLastname(), val);
		assertEquals(String.class, val.getClass());
	}
}
