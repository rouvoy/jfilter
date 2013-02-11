package fr.inria.jfilter.operators;

import java.util.ArrayList;
import java.util.Collection;

import fr.inria.jfilter.Filter;

public abstract class FilterImpl implements Filter {

	public <E> Collection<E> filter(Collection<E> collection) {
		try {
			Collection<E> res = new ArrayList<E>();//collection.getClass().newInstance();
			for (E item : collection)
				if (match(item))
					res.add(item);
			return res;
		} catch (Exception e) {
			return null;
		}
	}
}
