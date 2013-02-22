package fr.inria.jfilter;

import java.util.Collection;
import java.util.Map;


public interface Query {
	Collection<Object> select(Object nodes);
	Collection<Object> select(Object nodes, Map<String, Object> context);
}
