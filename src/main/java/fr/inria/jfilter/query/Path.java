package fr.inria.jfilter.query;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.inria.jfilter.Query;
import fr.inria.jfilter.utils.Views;

public class Path implements Query {
	private final List<Query> steps = new LinkedList<Query>();

	public Collection<Object> apply(Object node, Map<String, Object> context) {
		Collection<Object> result = Views.asView(node);
		for (Query step : this.steps) {
			result = step.apply(result, context);
			if (result.isEmpty())
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
