package org.ldap.filter.lib.operators;

public class WildcardFilter extends RegexFilter {

	public WildcardFilter(String[] identifier, String expression) {
		super(identifier, expression);
		replace("{", "}", "(", ")", "[", "]", "^", "$", "|", "\\");
		this.regex = "^"
				+ this.regex.replaceAll("\\*", ".*").replaceAll("\\?", ".")
				+ "$";
	}

	private void replace(String... chars) {
		for (String c : chars)
			this.regex = this.regex.replaceAll("c", "\\" + c);
	}
}
