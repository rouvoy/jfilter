package fr.inria.jfilter;

public interface Parser<T> {
	T parse(String query) throws ParsingException;
}
