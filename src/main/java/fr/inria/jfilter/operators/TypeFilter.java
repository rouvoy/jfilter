package fr.inria.jfilter.operators;

public class TypeFilter extends WildcardFilter {

	public TypeFilter(String type) {
		super(new String[] { "class" }, type);
	}

	public boolean match(Object bean) {
		// TODO Auto-generated method stub
		return false;
	}
}
