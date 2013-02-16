package fr.inria.jfilter;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public static final String VISIT_COLLECTION = "visit_collection";

	public Map<String, Object> newContext() {
		HashMap<String, Object> ctx = new HashMap<String, Object>();
		ctx.put(VISIT_COLLECTION, true);
		return ctx;
	}
}
