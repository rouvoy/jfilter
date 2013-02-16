package fr.inria.jfilter.operators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.Filter;

public abstract class FilterImpl implements Filter {

	public <E> Collection<E> filter(Collection<E> collection, Map<String, Object> context) {
		try {
			Collection<E> res = new ArrayList<E>();//collection.getClass().newInstance();
			for (E item : collection)
				if (match(item, null))
					res.add(item);
			return res;
		} catch (Exception e) {
			return null;
		}
	}
}
