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
package fr.inria.jfilter.resolvers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

public class BeanResolver extends ValueResolver {
    public static final ValueResolver bean = new BeanResolver();

    public Collection<Object> getValues(Object bean, String key) {
        Collection<Object> res = update(new HashSet<Object>(),
                getMethodValue(bean, key, true));
        if (res.isEmpty()) {
            res = update(new HashSet<Object>(),
                    getMethodValue(bean, key, false));
        }
        if (res.isEmpty()) {
            res = update(new HashSet<Object>(),
                    getFieldValue(bean, key));
        }
        return res;
    }

    private Object getFieldValue(Object bean, String key) {
        for (Field f : bean.getClass().getDeclaredFields())
            if (key.equalsIgnoreCase(f.getName())) {
                f.setAccessible(true);
                try {
                    return f.get(bean);
                } catch (Exception e) {
                }
            }
        return null;
    }

    private Object getMethodValue(Object bean, String key, boolean tryGetter) {
        String getter;
        if (tryGetter) {
            getter = "get" + key;
        } else {
            getter = key;
        }
        for (Method m : bean.getClass().getMethods()) {
            if (getter.equalsIgnoreCase(m.getName())
                    || key.equalsIgnoreCase(m.getName())) {
                m.setAccessible(true);
                try {
                    return m.invoke(bean);
                } catch (Exception e) {
                }
            }
        }
        return null;
    }
}
