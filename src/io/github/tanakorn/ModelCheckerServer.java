package io.github.tanakorn;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ModelCheckerServer {
	
	private boolean isInit;
	
	private StateSpaceExplorer explorer;
	private Collection<NodeProxy> system;
	
	private Map<NodeProxy, Boolean> stableStates;
	private Map<String, Collection<Event>> eventQueues;
	
	private Object stableMonitor;
	
	public ModelCheckerServer(StateSpaceExplorer explorer) {
		this.explorer = explorer;
		isInit = false;
		stableMonitor = new Object();
	}
	
	public void init(Collection<NodeProxy> system) {
		if (isInit) {
			throw new RuntimeException("The server has already been initialized");
		}
		isInit = true;
		this.system = system;
		stableStates = new HashMap<>();
		eventQueues = new HashMap<>();
		for (NodeProxy node : system) {
			stableStates.put(node, false);
			for (String queueName : node.getEventQueueNames()) {
				if (!eventQueues.containsKey(queueName)) {
					eventQueues.put(queueName, new LinkedList<>());
				}
			}
		}
	}
	
	private boolean isSystemStable() {
		for (NodeProxy node : system) {
			if (!node.isStable()) {
				return false;
			}
		}
		return true;
	}
	
	public void run() {
		while (true) {
			while (!isSystemStable()) {
				synchronized (stableMonitor) {
					try {
						stableMonitor.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Event nextEvent = explorer.retrieveNextEvent(eventQueues.values());
			nextEvent.enable();
			for (NodeProxy node : system) {
				node.setStable(false);
			}
		}
	}

}
