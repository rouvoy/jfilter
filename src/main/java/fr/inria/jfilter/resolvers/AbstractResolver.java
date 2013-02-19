package fr.inria.jfilter.resolvers;

import java.util.Collection;

import fr.inria.jfilter.Context;

public abstract class AbstractResolver implements Resolver {

	public Collection<Object> resolve(Object pojo, String axe) {
		return resolve(pojo, axe, Context.newContext());
	}
}