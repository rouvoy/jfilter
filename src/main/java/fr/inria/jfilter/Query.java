package fr.inria.jfilter;

import java.util.Map;


public interface Query {
	Object apply(Object nodes, Map<String, Object> context);
}
