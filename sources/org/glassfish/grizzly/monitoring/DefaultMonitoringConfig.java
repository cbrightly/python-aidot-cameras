package org.glassfish.grizzly.monitoring;

import org.glassfish.grizzly.utils.ArraySet;

public class DefaultMonitoringConfig<E> implements MonitoringConfig<E> {
    private final ArraySet<E> monitoringProbes;

    public DefaultMonitoringConfig(Class<E> clazz) {
        this.monitoringProbes = new ArraySet<>(clazz);
    }

    public final void addProbes(E... probes) {
        this.monitoringProbes.addAll((T[]) probes);
    }

    public final boolean removeProbes(E... probes) {
        return this.monitoringProbes.removeAll((Object[]) probes);
    }

    public final E[] getProbes() {
        return this.monitoringProbes.obtainArrayCopy();
    }

    public final E[] getProbesUnsafe() {
        return this.monitoringProbes.getArray();
    }

    public boolean hasProbes() {
        return !this.monitoringProbes.isEmpty();
    }

    public final void clearProbes() {
        this.monitoringProbes.clear();
    }

    public Object createManagementObject() {
        return null;
    }
}
