package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;

public final class KeepAlive implements MonitoringAware<KeepAliveProbe> {
    private int idleTimeoutInSeconds;
    private int maxRequestsCount;
    protected final DefaultMonitoringConfig<KeepAliveProbe> monitoringConfig;

    public KeepAlive() {
        this.idleTimeoutInSeconds = 30;
        this.maxRequestsCount = 256;
        this.monitoringConfig = new DefaultMonitoringConfig<KeepAliveProbe>(KeepAliveProbe.class) {
            public Object createManagementObject() {
                return KeepAlive.this.createJmxManagementObject();
            }
        };
    }

    public KeepAlive(KeepAlive keepAlive) {
        this.idleTimeoutInSeconds = 30;
        this.maxRequestsCount = 256;
        this.monitoringConfig = keepAlive.monitoringConfig;
        this.idleTimeoutInSeconds = keepAlive.idleTimeoutInSeconds;
        this.maxRequestsCount = keepAlive.maxRequestsCount;
    }

    public int getIdleTimeoutInSeconds() {
        return this.idleTimeoutInSeconds;
    }

    public void setIdleTimeoutInSeconds(int idleTimeoutInSeconds2) {
        if (idleTimeoutInSeconds2 < 0) {
            this.idleTimeoutInSeconds = -1;
        } else {
            this.idleTimeoutInSeconds = idleTimeoutInSeconds2;
        }
    }

    public int getMaxRequestsCount() {
        return this.maxRequestsCount;
    }

    public void setMaxRequestsCount(int maxRequestsCount2) {
        this.maxRequestsCount = maxRequestsCount2;
    }

    public MonitoringConfig<KeepAliveProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    /* access modifiers changed from: protected */
    public Object createJmxManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.jmx.KeepAlive", this, KeepAlive.class);
    }

    protected static void notifyProbesConnectionAccepted(KeepAlive keepAlive, Connection connection) {
        KeepAliveProbe[] probes = (KeepAliveProbe[]) keepAlive.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (KeepAliveProbe probe : probes) {
                probe.onConnectionAcceptEvent(connection);
            }
        }
    }

    protected static void notifyProbesHit(KeepAlive keepAlive, Connection connection, int requestNumber) {
        KeepAliveProbe[] probes = (KeepAliveProbe[]) keepAlive.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (KeepAliveProbe probe : probes) {
                probe.onHitEvent(connection, requestNumber);
            }
        }
    }

    protected static void notifyProbesRefused(KeepAlive keepAlive, Connection connection) {
        KeepAliveProbe[] probes = (KeepAliveProbe[]) keepAlive.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (KeepAliveProbe probe : probes) {
                probe.onRefuseEvent(connection);
            }
        }
    }

    protected static void notifyProbesTimeout(KeepAlive keepAlive, Connection connection) {
        KeepAliveProbe[] probes = (KeepAliveProbe[]) keepAlive.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (KeepAliveProbe probe : probes) {
                probe.onTimeoutEvent(connection);
            }
        }
    }
}
