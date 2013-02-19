package fr.inria.jfilter.operators;

import java.util.Collection;
import java.util.Map;

import fr.inria.jfilter.Context;
import fr.inria.jfilter.Filter;
import fr.inria.jfilter.utils.Views;

public abstract class FilterImpl implements Filter {

	public boolean match(Object bean) {
		return match(bean, Context.newContext());
	}

	public <E> Collection<E> filter(Collection<E> collection) {
		return filter(collection, Context.newContext());
	}

	public <E> Collection<E> filter(Collection<E> collection,
			Map<String, Object> context) {
		try {
			Collection<E> res = Views.newView();
			for (E item : collection)
				if (match(item, context))
					res.add(item);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
