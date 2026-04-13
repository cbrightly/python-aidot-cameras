package org.glassfish.grizzly.memory;

public interface DefaultMemoryManagerFactory {
    public static final String DMMF_PROP_NAME = "org.glassfish.grizzly.MEMORY_MANAGER_FACTORY";

    MemoryManager createMemoryManager();
}
