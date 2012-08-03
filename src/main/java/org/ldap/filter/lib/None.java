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

	public T getOrElse(Option<T> opt) {
		return opt.get();
	}

	public Option<T> orElse(Option<T> opt) {
		return opt;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Option)
			return ((Option<?>)obj).isEmpty();
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "None";
	}
	
	@SuppressWarnings("rawtypes")
	public static final Option<?> none = new None();
}
