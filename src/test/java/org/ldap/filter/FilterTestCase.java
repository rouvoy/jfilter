/**
 * Copyright (C) 2012 University Lille 1, Inria
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 * Contact: romain.rouvoy@univ-lille1.fr
 */
package org.ldap.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

public abstract class FilterTestCase extends TestCase {

	public static class Person {
		public String firstname = "John", name = "Doe";
		public int age = 20;
		public boolean male = true;
		public double height = 1.8;
		public String filter = "*:W";
		public Address home = new Address();

		public String getLastname() {
			return this.name;
		}

		public static class Address {
			public String street = "Main street", city = "New York";
			public int postcode = 10014;
		}
	}

	protected final Person bean = new Person();
	protected final HashMap<String, Object> map = new HashMap<String, Object>();
	protected final Properties property = new Properties();
	protected final List<Person> list = new LinkedList<Person>();
	protected final Set<Person> set = new HashSet<Person>();

	public FilterTestCase(String name) {
		super(name);
		map.put("firstname", bean.firstname);
		map.put("name", bean.name);
		map.put("age", bean.age);
		map.put("male", bean.male);
		map.put("height", bean.height);
		map.put("home", bean.home);

		property.putAll(map);
		
		list.add(bean);
		
		set.add(bean);
	}
}