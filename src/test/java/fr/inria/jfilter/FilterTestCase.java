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
package fr.inria.jfilter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import fr.inria.jfilter.utils.Option;

public abstract class FilterTestCase extends TestCase {
	public static class Family {
		public final Person dad, mom;
		public final Collection<Person> childs = new LinkedList<Person>();

		public Family(Person mr, Person ms) {
			this.dad = mr;
			this.mom = ms;
			mr.family = this;
			ms.family = this;
		}

		public Collection<Person> members() {
			Collection<Person> m = new LinkedList<FilterTestCase.Person>();
			Collections.addAll(m, dad, mom);
			m.addAll(childs);
			return m;
		}

		public String getName() {
			return dad.lastname;
		}

		public String toString() {
			return this.getName();
		}

		public boolean equals(Object o) {
			return this.toString().equals(o.toString());
		}

		public int hashCode() {
			return this.toString().hashCode();
		}

		public void addChild(Child c) {
			this.childs.add(c);
			this.dad.childs.add(c);
			this.mom.childs.add(c);
			c.dad = this.dad;
			c.mom = this.mom;
			c.family = this;
		}

		private final Map<String, Object> map = new HashMap<String, Object>();

		public Map<String, Object> map() {
			if (map.isEmpty()) {
				map.put("name", this.getName());
				map.put("dad", this.dad.map());
				map.put("mom", this.mom.map());
				map.put("childs", this.childs);
				map.put("members", this.members());
			}
			return map;
		}
	}

	public static class Person {
		public final String firstname, lastname;
		public final boolean male;
		public int age;
		public double height;
		public Family family;

		public Address address = new Address();

		public Collection<Person> childs = new HashSet<Person>();
		public final Collection<String> hobbies = new HashSet<String>();

		public String filter = "*:W";

		public Person(String first, String last, boolean m, int a, double h) {
			this.firstname = first;
			this.lastname = last;
			this.age = a;
			this.male = m;
			this.height = h;
		}

		public String[] getName() {
			return new String[] { this.firstname, this.lastname };
		}

		public String toString() {
			return this.firstname + " " + this.lastname;
		}

		public boolean equals(Object o) {
			return this.toString().equals(o.toString());
		}

		public int hashCode() {
			return this.toString().hashCode();
		}

		public static class Address {
			public String street = "Main street", city = "New York";
			public int postcode = 10014;

			public String country() {
				return "United States";
			}

			public String toString() {
				return street + ", " + postcode + " " + city + " (" + country()
						+ ")";
			}

			public boolean equals(Object o) {
				return this.toString().equals(o.toString());
			}

			public int hashCode() {
				return this.toString().hashCode();
			}

			private final Map<String, Object> map = new HashMap<String, Object>();

			public Map<String, Object> map() {
				if (map.isEmpty()) {
					map.put("street", this.street);
					map.put("city", this.city);
					map.put("postcode", this.postcode);
					map.put("country", this.country());
				}
				return map;
			}
		}

		private final Map<String, Object> map = new HashMap<String, Object>();

		public Map<String, Object> map() {
			if (map.isEmpty()) {
				map.put("firstname", this.firstname);
				map.put("lastname", this.lastname);
				map.put("age", this.age);
				map.put("male", this.male);
				map.put("height", this.height);
				map.put("address", this.address.map());
				map.put("family", this.family);
				map.put("childs", this.childs);
				map.put("hobbies", this.hobbies);
				map.put("name", this.getName());
				map.put("filter", this.filter);
			}
			return map;
		}

		private final Properties property = new Properties();

		public Properties properties() {
			property.putAll(map());
			return property;
		}
	}

	public static class Child extends Person {
		public Person dad, mom;

		public Child(String first, String last, boolean m, int a, double h) {
			super(first, last, m, a, h);
		}

		public Map<String, Object> map() {
			Map<String, Object> res = super.map();
			res.put("dad", this.dad);
			res.put("mom", this.dad);
			return res;
		}
	}

	protected final Family doe;

	public FilterTestCase(String name) {
		super(name);
		Person dad = new Person("John", "Doe", true, 30, 1.8);
		Person mom = new Person("Jane", "Doe", false, 30, 1.6);
		this.doe = new Family(dad, mom);
		this.doe.addChild(new Child("Junior", "Doe", true, 10, 1.2));
		this.doe.addChild(new Child("Julie", "Doe", false, 8, 1));
	}

	protected static final void assertEmpty(Collection<?> coll) {
		assertTrue("Collection should be empty", coll.isEmpty());
	}

	protected static final void assertEmpty(Option<?> opt) {
		assertTrue("Option should be empty", opt.isEmpty());
		if (opt.get() instanceof Collection<?>)
			assertEmpty((Collection<?>) opt.get());
	}

	protected static final void assertContains(Object expected,
			Collection<?> coll) {
		assertFalse("Collection should not be empty", coll.isEmpty());
		assertTrue("Collection " + coll + " should contain the value "
				+ expected, coll.contains(expected));
	}

	protected static final void assertContains(Object expected,
			Option<Object> opt) {
		assertFalse("Option should not be empty", opt.isEmpty());
		if (opt.get() instanceof Collection<?>)
			assertContains(expected, (Collection<?>) opt.get());
		else
			assertTrue("Option should contain the value " + expected, opt.get()
					.equals(expected));
	}

	protected void assertIs(Object expected, Collection<Object> val) {
		assertFalse("Collection should be defined", val.isEmpty());
		assertEquals(expected, val);
	}

	protected static final void assertSize(int size, Collection<?> coll) {
		assertEquals(size, coll.size());
	}
}