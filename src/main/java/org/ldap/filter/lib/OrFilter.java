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

import java.util.Arrays;

import org.ldap.filter.Filter;

public class OrFilter implements Filter {
	private final Filter[] delegates;

	public OrFilter(Filter... delegates) {
		this.delegates = delegates;
	}

	public boolean match(Object bean) {
		for (Filter f : delegates)
			if (f.match(bean))
				return true;
		return false;
	}

	public String toString() {
		return "||" + Arrays.toString(delegates);
	}
}
