package org.glassfish.grizzly.filterchain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ShutdownEvent implements FilterChainEvent {
    public static final Object TYPE = ShutdownEvent.class.getName();
    private long gracePeriod;
    private Set<Callable<Filter>> shutdownFutures;
    private TimeUnit timeUnit;

    public ShutdownEvent(long gracePeriod2, TimeUnit timeUnit2) {
        this.gracePeriod = gracePeriod2;
        this.timeUnit = timeUnit2;
    }

    public Object type() {
        return TYPE;
    }

    public void addShutdownTask(Callable<Filter> future) {
        if (future != null) {
            if (this.shutdownFutures == null) {
                this.shutdownFutures = new LinkedHashSet(4);
            }
            this.shutdownFutures.add(future);
        }
    }

    public Set<Callable<Filter>> getShutdownTasks() {
        Set<Callable<Filter>> set = this.shutdownFutures;
        return set != null ? set : Collections.emptySet();
    }

    public long getGracePeriod() {
        return this.gracePeriod;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }
}
