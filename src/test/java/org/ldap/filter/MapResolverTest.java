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
		Option<Object> val = mapResolver.getValue(x, "firstname");
		assertEquals("John", val.get());
	}

	public void testResolveMapWithInt() {
		Option<Object> val = mapResolver.getValue(x, "age");
		assertEquals(20, val.get());
	}

	public void testResolveMapWithDouble() {
		Option<Object> val = mapResolver.getValue(x, "height");
		assertEquals(1.8, val.get());
	}

	public void testResolveMapWithBoolean() {
		Option<Object> val = mapResolver.getValue(x, "male");
		assertEquals(true, val.get());
	}

	public void testResolvePropertiesWithString() {
		Option<Object> val = mapResolver.getValue(y, "firstname");
		assertEquals("Nikita", val.get());
	}
}
