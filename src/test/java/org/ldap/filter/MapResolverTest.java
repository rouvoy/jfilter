package org.ldap.filter;

import java.util.HashMap;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.ldap.filter.lib.MapResolver;
import org.ldap.filter.lib.None;
import org.ldap.filter.lib.Option;
import org.ldap.filter.lib.ValueResolver;

/**
 * Unit test for Bean Resolver class.
 */
public class MapResolverTest extends TestCase {
	public MapResolverTest(String testName) {
		super(testName);
		x.put("firstname", "John");
		x.put("name", "Doe");
		x.put("age", 20);
		x.put("male", true);
		x.put("height", 1.8);

		y.put("firstname", "Nikita");
	}

	private final ValueResolver mapResolver = new MapResolver();
	private final HashMap<String, Object> x = new HashMap<String, Object>();

	private final Properties y = new Properties();

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MapResolverTest.class);
	}

	public void testResolveUnknownKey() {
		Option<Object> val = mapResolver.getValue(x, "abc");
		assertEquals(None.none, val);
	}

	public void testResolveMapWithString() {
		Object val = mapResolver.getValue(x, "firstname").get();
		assertEquals("John", val);
		assertEquals(String.class, val.getClass());
	}

	public void testResolveMapWithInt() {
		Object val = mapResolver.getValue(x, "age").get();
		assertEquals(20, val);
		assertEquals(Integer.class, val.getClass());
	}

	public void testResolveMapWithDouble() {
		Object val = mapResolver.getValue(x, "height").get();
		assertEquals(1.8, val);
		assertEquals(Double.class, val.getClass());
	}

	public void testResolveMapWithBoolean() {
		Object val = mapResolver.getValue(x, "male").get();
		assertEquals(true, val);
		assertEquals(Boolean.class, val.getClass());
	}

	public void testResolvePropertiesWithString() {
		Object val = mapResolver.getValue(y, "firstname").get();
		assertEquals("Nikita", val);
		assertEquals(String.class, val.getClass());
	}
}
