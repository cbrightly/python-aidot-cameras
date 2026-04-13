package org.glassfish.grizzly.monitoring;

public interface MonitoringAware<E> {
    MonitoringConfig<E> getMonitoringConfig();
}
