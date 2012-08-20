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
package fr.inria.jfilter.utils;

public class None<T> implements Option<T> {

	private None() {
	}

	public boolean isEmpty() {
		return true;
	}

	public boolean isDefined() {
		return false;
	}

	public T get() {
		return null;
	}

	public T getOr(Option<T>... opt) {
		return or(opt).get();
	}

	public Option<T> or(Option<T>... opt) {
		for (int i=0;i<opt.length;i++)
			if (opt[i].isDefined())
				return opt[i];
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Option)
			return ((Option<?>) obj).isEmpty();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "None";
	}

	@SuppressWarnings("rawtypes")
	public static final Option<?> none = new None();

	public static final <T> Option<T> none() {
		return new None<T>();
	}
}
