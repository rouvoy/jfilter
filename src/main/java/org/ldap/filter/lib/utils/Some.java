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
package org.ldap.filter.lib.utils;

public class Some<T> implements Option<T>{
	private final T value;
	
	public Some(T val) {
		this.value = val;
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean isDefined() {
		return true;
	}

	public T get() {
		return value;
	}

	public T getOr(Option<T>... opt) {
		return value;
	}

	public Option<T> or(Option<T>... opt) {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Option)
			return value.equals(((Option<?>)obj).get());
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Some("+value.toString()+")";
	}
	
	public static <T> Option<T> some(T val) {
		return new Some<T>(val);
	}
}
