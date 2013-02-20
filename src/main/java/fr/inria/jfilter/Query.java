package fr.inria.jfilter;

import java.util.Collection;
import java.util.Map;


public interface Query {
	Collection<Object> apply(Object nodes);
	Collection<Object> apply(Object nodes, Map<String, Object> context);
}
