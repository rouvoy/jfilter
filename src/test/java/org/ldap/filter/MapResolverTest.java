package org.ldap.filter;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.ldap.filter.lib.MapResolver;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.ValueResolver;

/**
 * Unit test for Bean Resolver class.
 */
public class MapResolverTest extends FilterTestCase {
	public MapResolverTest(String testName) {
		super(testName);
	}

	private final ValueResolver mapResolver = new MapResolver();

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MapResolverTest.class);
	}

	public void testResolveUnknownKey() {
		Option<Object> val = mapResolver.getValue(map, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveMapWithString() {
		Object val = mapResolver.getValue(map, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveMapWithInt() {
		Object val = mapResolver.getValue(map, "age").get();
		assertEquals(bean.age, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveMapWithDouble() {
		Object val = mapResolver.getValue(map, "height").get();
		assertEquals(bean.height, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveMapWithBoolean() {
		Object val = mapResolver.getValue(map, "male").get();
		assertEquals(bean.male, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolvePropertiesWithString() {
		Object val = mapResolver.getValue(property, "firstname").get();
		assertEquals(bean.firstname, val);
		assertEquals(String.class, val.getClass());
	}
}
