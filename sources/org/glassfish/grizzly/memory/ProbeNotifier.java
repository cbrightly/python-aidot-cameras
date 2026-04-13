package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;

public final class ProbeNotifier {
    ProbeNotifier() {
    }

    static void notifyBufferAllocated(DefaultMonitoringConfig<MemoryProbe> config, int size) {
        MemoryProbe[] probes = (MemoryProbe[]) config.getProbesUnsafe();
        if (probes != null) {
            for (MemoryProbe probe : probes) {
                probe.onBufferAllocateEvent(size);
            }
        }
    }

    static void notifyBufferAllocatedFromPool(DefaultMonitoringConfig<MemoryProbe> config, int size) {
        MemoryProbe[] probes = (MemoryProbe[]) config.getProbesUnsafe();
        if (probes != null) {
            for (MemoryProbe probe : probes) {
                probe.onBufferAllocateFromPoolEvent(size);
            }
        }
    }

    static void notifyBufferReleasedToPool(DefaultMonitoringConfig<MemoryProbe> config, int size) {
        MemoryProbe[] probes = (MemoryProbe[]) config.getProbesUnsafe();
        if (probes != null) {
            for (MemoryProbe probe : probes) {
                probe.onBufferReleaseToPoolEvent(size);
            }
        }
    }
}
