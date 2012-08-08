package org.ldap.filter.lib;

import java.util.Map;

public class MapResolver implements ValueResolver {

	public Option<Object> getValue(Object bean, String key) {
		if (bean instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) bean;
			return map.containsKey(key) ? Some.some(map.get(key)) : None.none();
		}
		return None.none();
	}
}
