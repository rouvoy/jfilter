package fr.inria.jfilter.query;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.inria.jfilter.Query;

public class Path implements Query {
	private final List<Query> steps = new LinkedList<Query>();

	public Object apply(Object node, Map<String, Object> context) {
		Object result = node;
		for (Query step : this.steps) {
			result = step.apply(result, context);
			if (result == null)
				break;
		}
		return result;
	}

	public void expand(Query s) {
		this.steps.add(s);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Query step : this.steps)
			buffer.append("[" + step + "]");
		return buffer.toString();
	}
}
