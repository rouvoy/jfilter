package fr.inria.jfilter.query;

import java.util.Collection;

import fr.inria.jfilter.Context;
import fr.inria.jfilter.Query;

public abstract class AbstractQuery implements Query{

	public Collection<Object> select(Object nodes) {
		return select(nodes,Context.newContext());
	}
}
