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
import java.util.Collections;
import java.util.HashSet;

public class BeanResolver extends ValueResolver {
    public static final ValueResolver bean = new BeanResolver();

    public Collection<Object> getValue(Object bean, String key) {

        //System.out.println(bean.getClass().getSimpleName()+"-"+key);

        Collection<Object> res = new HashSet<Object>();
        res.addAll(getMethodValue(bean, key, true));
        if (res.isEmpty()) {
            res.addAll(getMethodValue(bean, key, false));
        }
        if (res.isEmpty()) {
            res.addAll(getFieldValue(bean, key));
        }

        //System.out.println(res.size());

        return res;
    }

    @SuppressWarnings("unchecked")
    private Collection<Object> getFieldValue(Object bean, String key) {
        for (Field f : bean.getClass().getDeclaredFields())
            if (key.equals(f.getName())) {
                f.setAccessible(true);
                try {
                    Object res = f.get(bean);
                    if (res instanceof Collection)
                        return (Collection<Object>) res;
                    return Collections.singleton(res);
                } catch (Exception e) {
                }
            }
        return Collections.emptySet();
    }

    @SuppressWarnings("unchecked")
    private Collection<Object> getMethodValue(Object bean, String key, boolean tryGetter) {
        String getter = "";
        if (tryGetter) {
            getter = "get" + key.substring(0, 1).toUpperCase()
                    + key.substring(1);
        } else {
            getter = key;
        }
        for (Method m : bean.getClass().getDeclaredMethods())
            if (getter.equals(m.getName())) {
                m.setAccessible(true);
                try {
                    Object res = m.invoke(bean);
                    if (res instanceof Collection)
                        return (Collection<Object>) res;
                    return Collections.singleton(res);
                } catch (Exception e) {
                }
            }
        return Collections.emptySet();
    }
}
