package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.monitoring.MonitoringAware;

public interface MemoryManager<E extends Buffer> extends MonitoringAware<MemoryProbe> {
    public static final MemoryManager DEFAULT_MEMORY_MANAGER = MemoryManagerInitializer.initManager();

    E allocate(int i);

    E allocateAtLeast(int i);

    E reallocate(E e, int i);

    void release(E e);

    boolean willAllocateDirect(int i);
}
