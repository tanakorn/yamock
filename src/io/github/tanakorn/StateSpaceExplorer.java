package io.github.tanakorn;

import java.util.Collection;

public interface StateSpaceExplorer {
	
	public Event retrieveNextEvent(Collection<Collection<Event>> eventQueues);

}
