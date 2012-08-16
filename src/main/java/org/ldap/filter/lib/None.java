package org.ldap.filter.lib;

public class None<T> implements Option<T> {

	private None() {
	}

	public boolean isEmpty() {
		return true;
	}

	public boolean isDefined() {
		return false;
	}

	public T get() {
		return null;
	}

	public T getOr(Option<T>... opt) {
		return or(opt).get();
	}

	public Option<T> or(Option<T>... opt) {
		for (int i=0;i<opt.length;i++)
			if (opt[i].isDefined())
				return opt[i];
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Option)
			return ((Option<?>) obj).isEmpty();
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "None";
	}

	@SuppressWarnings("rawtypes")
	public static final Option<?> none = new None();

	public static final <T> Option<T> none() {
		return new None<T>();
	}
}
