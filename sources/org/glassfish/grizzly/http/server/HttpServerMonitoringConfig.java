package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.TransportProbe;
import org.glassfish.grizzly.http.HttpProbe;
import org.glassfish.grizzly.http.server.filecache.FileCacheProbe;
import org.glassfish.grizzly.memory.MemoryProbe;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;

public final class HttpServerMonitoringConfig {
    private final DefaultMonitoringConfig<ConnectionProbe> connectionConfig = new DefaultMonitoringConfig<>(ConnectionProbe.class);
    private final DefaultMonitoringConfig<FileCacheProbe> fileCacheConfig = new DefaultMonitoringConfig<>(FileCacheProbe.class);
    private final DefaultMonitoringConfig<HttpProbe> httpConfig = new DefaultMonitoringConfig<>(HttpProbe.class);
    private final DefaultMonitoringConfig<MemoryProbe> memoryConfig = new DefaultMonitoringConfig<>(MemoryProbe.class);
    private final DefaultMonitoringConfig<ThreadPoolProbe> threadPoolConfig = new DefaultMonitoringConfig<>(ThreadPoolProbe.class);
    private final DefaultMonitoringConfig<TransportProbe> transportConfig = new DefaultMonitoringConfig<>(TransportProbe.class);
    private final DefaultMonitoringConfig<HttpServerProbe> webServerConfig = new DefaultMonitoringConfig<>(HttpServerProbe.class);

    public MonitoringConfig<MemoryProbe> getMemoryConfig() {
        return this.memoryConfig;
    }

    public MonitoringConfig<ConnectionProbe> getConnectionConfig() {
        return this.connectionConfig;
    }

    public MonitoringConfig<ThreadPoolProbe> getThreadPoolConfig() {
        return this.threadPoolConfig;
    }

    public MonitoringConfig<TransportProbe> getTransportConfig() {
        return this.transportConfig;
    }

    public MonitoringConfig<FileCacheProbe> getFileCacheConfig() {
        return this.fileCacheConfig;
    }

    public MonitoringConfig<HttpProbe> getHttpConfig() {
        return this.httpConfig;
    }

    public MonitoringConfig<HttpServerProbe> getWebServerConfig() {
        return this.webServerConfig;
    }
}
