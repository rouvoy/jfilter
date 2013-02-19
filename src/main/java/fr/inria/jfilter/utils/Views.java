package fr.inria.jfilter.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Views {
	public static final <T> Collection<T> newView() {
		return new HashSet<T>();
	}

	@SuppressWarnings("unchecked")
	public static final <T, S extends T> Collection<T> asView(S elt) {
		if (elt instanceof Collection<?>)
			return (Collection<T>) elt;
		Collection<T> res = newView();
		res.add(elt);
		return res;
	}

	public static final <T> Collection<T> noView() {
		return Collections.emptySet();
	}

	public static final <T> Collection<T> mergeViews(Collection<T> viewA,
			Collection<T> viewB) {
		if (viewB != null)
			viewA.addAll(viewB);
		return viewA;
	}
}
