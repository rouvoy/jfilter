package org.ldap.filter;

import java.util.HashMap;
import java.util.Properties;

import junit.framework.TestCase;

public abstract class FilterTestCase extends TestCase {

	public static class Person {
		String firstname = "John", name = "Doe";
		int age = 20;
		boolean male = true;
		double height = 1.8;
		String filter = "*:W";

		String getLastname() {
			return this.name;
		}
	}

	protected final Person bean = new Person();

	protected final HashMap<String, Object> map = new HashMap<String, Object>();

	protected final Properties property = new Properties();

	
	public FilterTestCase(String name) {
		super(name);
		map.put("firstname", bean.firstname);
		map.put("name", bean.name);
		map.put("age", bean.age);
		map.put("male", bean.male);
		map.put("height", bean.height);

		property.putAll(map);
	}
}