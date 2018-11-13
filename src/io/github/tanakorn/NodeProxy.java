package io.github.tanakorn;

import java.util.Collection;

public interface NodeProxy {
	
	public Collection<String> getEventQueueNames();
	public void propose(String eventQueueName, Event event);
	public boolean isStable();
	public void setStable(boolean isStable);
	public NodeState getState();

}
