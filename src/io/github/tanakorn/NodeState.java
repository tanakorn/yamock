package io.github.tanakorn;

import java.util.HashMap;
import java.util.Map;

public class NodeState {
	
	private Map<String, Object> state;
	
	public NodeState() {
		state = new HashMap<>();
	}
	
	public void set(String key, Object value) {
		state.put(key, value);
	}
	
	public Object get(String key) {
		return state.get(key);
	}

}
