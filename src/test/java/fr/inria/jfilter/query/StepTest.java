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
package fr.inria.jfilter.query;

import junit.framework.Test;
import junit.framework.TestSuite;

import fr.inria.jfilter.ParsingException;
import fr.inria.jfilter.FilterTestCase;
import fr.inria.jfilter.Query;
import fr.inria.jfilter.operators.EqualsToFilter;
import fr.inria.jfilter.operators.LessThanFilter;
import fr.inria.jfilter.operators.MoreThanFilter;

public class StepTest extends FilterTestCase {
	public StepTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(StepTest.class);
	}

	public void testEqualsToFilterMatchString() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "firstname" }, doe.dad.firstname));
		assertFalse(step.select(doe).isEmpty());
		assertSize(1, step.select(doe));
		assertContains(doe.dad, step.select(doe));
	}

	public void testEqualsToFilterDoNotMatchString() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "firstname" }, "Bob"));
		assertTrue(step.select(doe).isEmpty());
	}

	public void testEqualsToFilterMatchInt() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "age" }, "" + doe.dad.age));
		assertFalse(step.select(doe).isEmpty());
		assertSize(2, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testMoreThanFilterMatchInt() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new MoreThanFilter(
				new String[] { "age" }, "19"));
		assertFalse(step.select(doe).isEmpty());
		assertSize(2, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testMoreThanFilterDoNotMatchInt() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new MoreThanFilter(
				new String[] { "age" }, "31"));
		assertTrue(step.select(doe).isEmpty());
	}

	public void testLessThanFilterMatchInt() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new LessThanFilter(
				new String[] { "age" }, "31"));
		assertFalse(step.select(doe).isEmpty());
		assertSize(4, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testLessThanFilterDoNotMatchInt() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new LessThanFilter(
				new String[] { "age" }, "5"));
		assertTrue(step.select(doe).isEmpty());
	}

	public void testEqualsToFilterMatchBoolean() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "male" }, "true"));
		assertFalse(step.select(doe).isEmpty());
		assertSize(2, step.select(doe));
		assertContains(doe.dad, step.select(doe));
	}

	public void testEqualsToFilterMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "height" }, "1.8"));
		assertFalse(step.select(doe).isEmpty());
		assertSize(1, step.select(doe));
		assertContains(doe.dad, step.select(doe));
	}

	public void testEqualsToFilterDoNotMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "height" }, "1.9"));
		assertTrue(step.select(doe).isEmpty());
	}

	public void testEqualsToFilterMatchEmbeddedLong() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "address", "postcode" }, "10014"));
		assertSize(4, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testEqualsToFilterMatchEmbeddedCollection()
			throws ParsingException {
		Query step = new Step(new String[] { "members" }, new EqualsToFilter(
				new String[] { "childs", "size" }, "2"));
		assertSize(2, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testMoreThanFilterMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new MoreThanFilter(
				new String[] { "height" }, "1.7"));
		assertFalse(step.select(doe).isEmpty());
		assertSize(1, step.select(doe));
		assertContains(doe.dad, step.select(doe));
	}

	public void testMoreThanFilterDoNotMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new MoreThanFilter(
				new String[] { "height" }, "1.9"));
		assertTrue(step.select(doe).isEmpty());
	}

	public void testLessThanFilterMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new LessThanFilter(
				new String[] { "height" }, "1.9"));
		assertSize(4, step.select(doe));
		assertContains(doe.dad, step.select(doe));
		assertContains(doe.mom, step.select(doe));
	}

	public void testLessThanFilterDoNotMatchDouble() throws ParsingException {
		Query step = new Step(new String[] { "members" }, new LessThanFilter(
				new String[] { "height" }, "0.8"));
		assertTrue(step.select(doe).isEmpty());
	}
}
