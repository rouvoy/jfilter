package org.ldap.filter.lib;

public class Tuple<X,Y> {
	public final X _1;
	public final Y _2;
	
	public Tuple(X x, Y y) {
		this._1 = x;
		this._2 = y;
	}
	
	public static final <S,T> Tuple<S,T> tuple(S s, T t) {
		return new Tuple<S,T>(s,t);
	}
	
	public String toString() {
		return "<"+_1.toString()+", "+_2.toString()+">";
	}
}
