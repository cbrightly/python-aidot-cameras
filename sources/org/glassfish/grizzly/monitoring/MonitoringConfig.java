package org.glassfish.grizzly.monitoring;

public interface MonitoringConfig<E> {
    void addProbes(E... eArr);

    void clearProbes();

    Object createManagementObject();

    E[] getProbes();

    boolean hasProbes();

    boolean removeProbes(E... eArr);
}
