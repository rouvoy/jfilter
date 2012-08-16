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
package org.ldap.filter.lib;

public class Tuple<X,Y> {
	public final X _1;
	public final Y _2;
	
	public Tuple(X x, Y y) {
		this._1 = x;
		this._2 = y;
	}
	
	public static final <S,T> Tuple<S,T> tuple(S s, T t) {
		return new Tuple<S,T>(s,t);
	}
	
	public String toString() {
		return "<"+_1.toString()+", "+_2.toString()+">";
	}
}
