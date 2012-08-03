package org.ldap.filter.lib;

public class Some<T> implements Option<T>{
	private final T value;
	
	public Some(T val) {
		this.value = val;
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean isDefined() {
		return true;
	}

	public T get() {
		return value;
	}

	public T getOrElse(Option<T> opt) {
		return value;
	}

	public Option<T> orElse(Option<T> opt) {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Option)
			return value.equals(((Option<?>)obj).get());
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Some("+value.toString()+")";
	}
	
	public static <T> Option<T> some(T val) {
		return new Some<T>(val);
	}
}
