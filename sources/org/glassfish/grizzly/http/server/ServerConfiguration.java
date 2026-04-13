package org.glassfish.grizzly.http.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import org.glassfish.grizzly.http.server.jmxbase.JmxEventListener;

public class ServerConfiguration extends ServerFilterConfiguration {
    private static final AtomicInteger INSTANCE_COUNT = new AtomicInteger(-1);
    private static final HttpHandlerRegistration[] ROOT_MAPPING = {HttpHandlerRegistration.ROOT};
    private boolean allowPayloadForUndefinedHttpMethods;
    final Map<HttpHandler, HttpHandlerRegistration[]> handlers;
    final Object handlersSync = new Object();
    final HttpServer instance;
    private boolean jmxEnabled;
    private final Set<JmxEventListener> jmxEventListeners = new CopyOnWriteArraySet();
    private long maxPayloadRemainderToSkip = -1;
    private final HttpServerMonitoringConfig monitoringConfig = new HttpServerMonitoringConfig();
    private String name;
    final List<HttpHandler> orderedHandlers = new LinkedList();
    private final Map<HttpHandler, HttpHandlerRegistration[]> unmodifiableHandlers;

    ServerConfiguration(HttpServer instance2) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        this.handlers = concurrentHashMap;
        this.unmodifiableHandlers = Collections.unmodifiableMap(concurrentHashMap);
        this.instance = instance2;
    }

    public void addHttpHandler(HttpHandler httpHandler) {
        addHttpHandler(httpHandler, ROOT_MAPPING);
    }

    public void addHttpHandler(HttpHandler httpHandler, String... mappings) {
        if (mappings == null || mappings.length == 0) {
            addHttpHandler(httpHandler, ROOT_MAPPING);
            return;
        }
        HttpHandlerRegistration[] registrations = new HttpHandlerRegistration[mappings.length];
        for (int i = 0; i < mappings.length; i++) {
            registrations[i] = HttpHandlerRegistration.fromString(mappings[i]);
        }
        addHttpHandler(httpHandler, registrations);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0006, code lost:
        if (r4.length == 0) goto L_0x0008;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addHttpHandler(org.glassfish.grizzly.http.server.HttpHandler r3, org.glassfish.grizzly.http.server.HttpHandlerRegistration... r4) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.handlersSync
            monitor-enter(r0)
            if (r4 == 0) goto L_0x0008
            int r1 = r4.length     // Catch:{ all -> 0x0024 }
            if (r1 != 0) goto L_0x000b
        L_0x0008:
            org.glassfish.grizzly.http.server.HttpHandlerRegistration[] r1 = ROOT_MAPPING     // Catch:{ all -> 0x0024 }
            r4 = r1
        L_0x000b:
            java.util.Map<org.glassfish.grizzly.http.server.HttpHandler, org.glassfish.grizzly.http.server.HttpHandlerRegistration[]> r1 = r2.handlers     // Catch:{ all -> 0x0024 }
            java.lang.Object r1 = r1.put(r3, r4)     // Catch:{ all -> 0x0024 }
            if (r1 == 0) goto L_0x0018
            java.util.List<org.glassfish.grizzly.http.server.HttpHandler> r1 = r2.orderedHandlers     // Catch:{ all -> 0x0024 }
            r1.remove(r3)     // Catch:{ all -> 0x0024 }
        L_0x0018:
            java.util.List<org.glassfish.grizzly.http.server.HttpHandler> r1 = r2.orderedHandlers     // Catch:{ all -> 0x0024 }
            r1.add(r3)     // Catch:{ all -> 0x0024 }
            org.glassfish.grizzly.http.server.HttpServer r1 = r2.instance     // Catch:{ all -> 0x0024 }
            r1.onAddHttpHandler(r3, r4)     // Catch:{ all -> 0x0024 }
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            return
        L_0x0024:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0024 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.ServerConfiguration.addHttpHandler(org.glassfish.grizzly.http.server.HttpHandler, org.glassfish.grizzly.http.server.HttpHandlerRegistration[]):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0021, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean removeHttpHandler(org.glassfish.grizzly.http.server.HttpHandler r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.Object r0 = r3.handlersSync     // Catch:{ all -> 0x0023 }
            monitor-enter(r0)     // Catch:{ all -> 0x0023 }
            java.util.Map<org.glassfish.grizzly.http.server.HttpHandler, org.glassfish.grizzly.http.server.HttpHandlerRegistration[]> r1 = r3.handlers     // Catch:{ all -> 0x001e }
            java.lang.Object r1 = r1.remove(r4)     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x000e
            r1 = 1
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            if (r1 == 0) goto L_0x001b
            java.util.List<org.glassfish.grizzly.http.server.HttpHandler> r2 = r3.orderedHandlers     // Catch:{ all -> 0x001e }
            r2.remove(r4)     // Catch:{ all -> 0x001e }
            org.glassfish.grizzly.http.server.HttpServer r2 = r3.instance     // Catch:{ all -> 0x001e }
            r2.onRemoveHttpHandler(r4)     // Catch:{ all -> 0x001e }
        L_0x001b:
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            monitor-exit(r3)
            return r1
        L_0x001e:
            r1 = move-exception
        L_0x001f:
            monitor-exit(r0)     // Catch:{ all -> 0x0021 }
            throw r1     // Catch:{ all -> 0x0023 }
        L_0x0021:
            r1 = move-exception
            goto L_0x001f
        L_0x0023:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.ServerConfiguration.removeHttpHandler(org.glassfish.grizzly.http.server.HttpHandler):boolean");
    }

    @Deprecated
    public Map<HttpHandler, String[]> getHttpHandlers() {
        Map<HttpHandler, String[]> map = new HashMap<>(this.unmodifiableHandlers.size());
        for (Map.Entry<HttpHandler, HttpHandlerRegistration[]> entry : this.unmodifiableHandlers.entrySet()) {
            HttpHandlerRegistration[] regs = entry.getValue();
            String[] strRegs = new String[regs.length];
            for (int i = 0; i < regs.length; i++) {
                String contextPath = regs[i].getContextPath();
                String urlPattern = regs[i].getUrlPattern();
                if (contextPath == null) {
                    strRegs[i] = urlPattern;
                } else if (urlPattern == null) {
                    strRegs[i] = contextPath;
                } else if (!contextPath.endsWith("/") || !urlPattern.startsWith("/")) {
                    strRegs[i] = contextPath + urlPattern;
                } else {
                    strRegs[i] = contextPath.substring(0, contextPath.length() - 1) + urlPattern;
                }
            }
            map.put(entry.getKey(), strRegs);
        }
        return Collections.unmodifiableMap(map);
    }

    public Map<HttpHandler, HttpHandlerRegistration[]> getHttpHandlersWithMapping() {
        return this.unmodifiableHandlers;
    }

    public HttpServerMonitoringConfig getMonitoringConfig() {
        return this.monitoringConfig;
    }

    public String getName() {
        String str;
        if (this.name == null) {
            if (!this.instance.isStarted()) {
                return null;
            }
            int count = INSTANCE_COUNT.incrementAndGet();
            if (count == 0) {
                str = "HttpServer";
            } else {
                str = "HttpServer-" + count;
            }
            this.name = str;
        }
        return this.name;
    }

    public void setName(String name2) {
        if (!this.instance.isStarted()) {
            this.name = name2;
        }
    }

    public boolean isJmxEnabled() {
        return this.jmxEnabled;
    }

    public void setJmxEnabled(boolean jmxEnabled2) {
        this.jmxEnabled = jmxEnabled2;
        if (!this.instance.isStarted()) {
            return;
        }
        if (jmxEnabled2) {
            this.instance.enableJMX();
            if (!this.jmxEventListeners.isEmpty()) {
                for (JmxEventListener l : this.jmxEventListeners) {
                    l.jmxEnabled();
                }
                return;
            }
            return;
        }
        if (!this.jmxEventListeners.isEmpty()) {
            for (JmxEventListener l2 : this.jmxEventListeners) {
                l2.jmxDisabled();
            }
        }
        this.instance.disableJMX();
    }

    public void addJmxEventListener(JmxEventListener listener) {
        if (listener != null) {
            this.jmxEventListeners.add(listener);
        }
    }

    public void removeJmxEventListener(JmxEventListener listener) {
        if (listener != null) {
            this.jmxEventListeners.remove(listener);
        }
    }

    public Set<JmxEventListener> getJmxEventListeners() {
        return this.jmxEventListeners;
    }

    public boolean isAllowPayloadForUndefinedHttpMethods() {
        return this.allowPayloadForUndefinedHttpMethods;
    }

    public void setAllowPayloadForUndefinedHttpMethods(boolean allowPayloadForUndefinedHttpMethods2) {
        this.allowPayloadForUndefinedHttpMethods = allowPayloadForUndefinedHttpMethods2;
    }

    public long getMaxPayloadRemainderToSkip() {
        return this.maxPayloadRemainderToSkip;
    }

    public void setMaxPayloadRemainderToSkip(long maxPayloadRemainderToSkip2) {
        this.maxPayloadRemainderToSkip = maxPayloadRemainderToSkip2;
    }
}
