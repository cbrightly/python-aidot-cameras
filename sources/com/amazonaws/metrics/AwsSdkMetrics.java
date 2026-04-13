package com.amazonaws.metrics;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.MetricCollector;
import com.amazonaws.regions.Regions;
import com.amazonaws.util.AWSRequestMetrics;
import com.amazonaws.util.AWSServiceMetrics;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum AwsSdkMetrics {
    ;
    
    public static final String AWS_CREDENTAIL_PROPERTIES_FILE = "credentialFile";
    public static final String CLOUDWATCH_REGION = "cloudwatchRegion";
    private static final boolean DEFAULT_METRICS_ENABLED = false;
    private static final String DEFAULT_METRIC_COLLECTOR_FACTORY = "com.amazonaws.metrics.internal.cloudwatch.DefaultMetricCollectorFactory";
    public static final String DEFAULT_METRIC_NAMESPACE = "AWSSDK/Java";
    public static final String EXCLUDE_MACHINE_METRICS = "excludeMachineMetrics";
    public static final String HOST_METRIC_NAME = "hostMetricName";
    public static final String INCLUDE_PER_HOST_METRICS = "includePerHostMetrics";
    public static final String JVM_METRIC_NAME = "jvmMetricName";
    private static final String MBEAN_OBJECT_NAME = null;
    public static final String METRIC_NAME_SPACE = "metricNameSpace";
    public static final String METRIC_QUEUE_SIZE = "metricQueueSize";
    public static final String QUEUE_POLL_TIMEOUT_MILLI = "getQueuePollTimeoutMilli";
    private static final int QUEUE_POLL_TIMEOUT_MILLI_MINUMUM = 1000;
    private static final MetricRegistry REGISTRY = null;
    public static final String USE_SINGLE_METRIC_NAMESPACE = "useSingleMetricNamespace";
    private static volatile String credentialFile;
    private static volatile AWSCredentialsProvider credentialProvider;
    private static boolean dirtyEnabling;
    private static volatile String hostMetricName;
    private static volatile String jvmMetricName;
    private static volatile boolean machineMetricsExcluded;
    private static volatile MetricCollector mc;
    private static volatile String metricNameSpace;
    private static volatile Integer metricQueueSize;
    private static volatile boolean perHostMetricsIncluded;
    private static volatile Long queuePollTimeoutMilli;
    private static volatile Regions region;
    private static volatile boolean singleMetricNamespace;

    static {
        Class<AwsSdkMetrics> cls;
        char c;
        MBEAN_OBJECT_NAME = "com.amazonaws.management:type=" + cls.getSimpleName();
        metricNameSpace = DEFAULT_METRIC_NAMESPACE;
        String defaultMetrics = System.getProperty(SDKGlobalConfiguration.DEFAULT_METRICS_SYSTEM_PROPERTY);
        int i = 1;
        boolean z = defaultMetrics != null;
        DEFAULT_METRICS_ENABLED = z;
        if (z) {
            String[] values = defaultMetrics.split(",");
            int length = values.length;
            int i2 = 0;
            boolean useSingleMetricNamespace = false;
            boolean includePerHostMetrics = false;
            boolean excludeMachineMetrics = false;
            while (i2 < length) {
                String part = values[i2].trim();
                if (!excludeMachineMetrics && EXCLUDE_MACHINE_METRICS.equals(part)) {
                    excludeMachineMetrics = true;
                } else if (!includePerHostMetrics && INCLUDE_PER_HOST_METRICS.equals(part)) {
                    includePerHostMetrics = true;
                } else if (useSingleMetricNamespace || !USE_SINGLE_METRIC_NAMESPACE.equals(part)) {
                    String[] pair = part.split("=");
                    if (pair.length == 2) {
                        String key = pair[c].trim();
                        String value = pair[i].trim();
                        try {
                            if (AWS_CREDENTAIL_PROPERTIES_FILE.equals(key)) {
                                setCredentialFile0(value);
                            } else if (CLOUDWATCH_REGION.equals(key)) {
                                region = Regions.fromName(value);
                            } else if (METRIC_QUEUE_SIZE.equals(key)) {
                                Integer i3 = new Integer(value);
                                if (i3.intValue() >= i) {
                                    metricQueueSize = i3;
                                } else {
                                    throw new IllegalArgumentException("metricQueueSize must be at least 1");
                                }
                            } else if (QUEUE_POLL_TIMEOUT_MILLI.equals(key)) {
                                Long i4 = new Long(value);
                                if (i4.intValue() >= 1000) {
                                    queuePollTimeoutMilli = i4;
                                } else {
                                    throw new IllegalArgumentException("getQueuePollTimeoutMilli must be at least 1000");
                                }
                            } else if (METRIC_NAME_SPACE.equals(key)) {
                                metricNameSpace = value;
                            } else if (JVM_METRIC_NAME.equals(key)) {
                                jvmMetricName = value;
                            } else if (HOST_METRIC_NAME.equals(key)) {
                                hostMetricName = value;
                            } else {
                                LogFactory.getLog((Class) cls).debug("Ignoring unrecognized parameter: " + part);
                            }
                        } catch (Exception e) {
                            LogFactory.getLog((Class) cls).debug("Ignoring failure", e);
                        }
                    } else {
                        continue;
                    }
                } else {
                    useSingleMetricNamespace = true;
                }
                i2++;
                c = 0;
                i = 1;
            }
            machineMetricsExcluded = excludeMachineMetrics;
            perHostMetricsIncluded = includePerHostMetrics;
            singleMetricNamespace = useSingleMetricNamespace;
        }
        REGISTRY = new MetricRegistry();
    }

    public static <T extends RequestMetricCollector> T getRequestMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? RequestMetricCollector.NONE : mc.getRequestMetricCollector();
    }

    public static <T extends ServiceMetricCollector> T getServiceMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? ServiceMetricCollector.NONE : mc.getServiceMetricCollector();
    }

    static MetricCollector getInternalMetricCollector() {
        return mc;
    }

    public static <T extends MetricCollector> T getMetricCollector() {
        if (mc == null && isDefaultMetricsEnabled()) {
            enableDefaultMetrics();
        }
        return mc == null ? MetricCollector.NONE : mc;
    }

    public static synchronized void setMetricCollector(MetricCollector mc2) {
        synchronized (AwsSdkMetrics.class) {
            MetricCollector old = mc;
            mc = mc2;
            if (old != null) {
                old.stop();
            }
        }
    }

    public static void setMachineMetricsExcluded(boolean excludeMachineMetrics) {
        machineMetricsExcluded = excludeMachineMetrics;
    }

    public static void setPerHostMetricsIncluded(boolean includePerHostMetrics) {
        perHostMetricsIncluded = includePerHostMetrics;
    }

    public static boolean isDefaultMetricsEnabled() {
        return DEFAULT_METRICS_ENABLED;
    }

    public static boolean isSingleMetricNamespace() {
        return singleMetricNamespace;
    }

    public static void setSingleMetricNamespace(boolean singleMetricNamespace2) {
        singleMetricNamespace = singleMetricNamespace2;
    }

    public static boolean isMetricsEnabled() {
        MetricCollector mc2 = mc;
        return mc2 != null && mc2.isEnabled();
    }

    public static boolean isMachineMetricExcluded() {
        return machineMetricsExcluded;
    }

    public static boolean isPerHostMetricIncluded() {
        return perHostMetricsIncluded;
    }

    public static boolean isPerHostMetricEnabled() {
        if (perHostMetricsIncluded) {
            return true;
        }
        String host = hostMetricName;
        if ((host == null ? "" : host.trim()).length() > 0) {
            return true;
        }
        return false;
    }

    public static synchronized boolean enableDefaultMetrics() {
        Class<AwsSdkMetrics> cls = AwsSdkMetrics.class;
        synchronized (cls) {
            if (mc == null || !mc.isEnabled()) {
                if (!dirtyEnabling) {
                    dirtyEnabling = true;
                    try {
                        MetricCollector instance = ((MetricCollector.Factory) Class.forName(DEFAULT_METRIC_COLLECTOR_FACTORY).newInstance()).getInstance();
                        if (instance != null) {
                            setMetricCollector(instance);
                            dirtyEnabling = false;
                            return true;
                        }
                        dirtyEnabling = false;
                    } catch (Exception e) {
                        try {
                            LogFactory.getLog((Class) cls).warn("Failed to enable the default metrics", e);
                            return false;
                        } finally {
                            dirtyEnabling = false;
                        }
                    }
                } else {
                    throw new IllegalStateException("Reentrancy is not allowed");
                }
            }
        }
    }

    public static void disableMetrics() {
        setMetricCollector(MetricCollector.NONE);
    }

    public static boolean add(MetricType type) {
        if (type == null) {
            return false;
        }
        return REGISTRY.addMetricType(type);
    }

    public static <T extends MetricType> boolean addAll(Collection<T> types) {
        if (types == null || types.size() == 0) {
            return false;
        }
        return REGISTRY.addMetricTypes(types);
    }

    public static <T extends MetricType> void set(Collection<T> types) {
        REGISTRY.setMetricTypes(types);
    }

    public static boolean remove(MetricType type) {
        if (type == null) {
            return false;
        }
        return REGISTRY.removeMetricType(type);
    }

    public static Set<MetricType> getPredefinedMetrics() {
        return REGISTRY.predefinedMetrics();
    }

    public static AWSCredentialsProvider getCredentialProvider() {
        StackTraceElement[] e = Thread.currentThread().getStackTrace();
        for (StackTraceElement className : e) {
            if (className.getClassName().equals(DEFAULT_METRIC_COLLECTOR_FACTORY)) {
                return credentialProvider;
            }
        }
        SecurityException ex = new SecurityException();
        LogFactory.getLog(AwsSdkMetrics.class).warn("Illegal attempt to access the credential provider", ex);
        throw ex;
    }

    public static synchronized void setCredentialProvider(AWSCredentialsProvider provider) {
        synchronized (AwsSdkMetrics.class) {
            credentialProvider = provider;
        }
    }

    public static Regions getRegion() {
        return region;
    }

    public static void setRegion(Regions region2) {
        region = region2;
    }

    public static String getCredentailFile() {
        return credentialFile;
    }

    public static void setCredentialFile(String filepath) {
        setCredentialFile0(filepath);
    }

    private static void setCredentialFile0(String filepath) {
        final PropertiesCredentials cred = new PropertiesCredentials(new File(filepath));
        synchronized (AwsSdkMetrics.class) {
            credentialProvider = new AWSCredentialsProvider() {
                public void refresh() {
                }

                public AWSCredentials getCredentials() {
                    return cred;
                }
            };
            credentialFile = filepath;
        }
    }

    public static Integer getMetricQueueSize() {
        return metricQueueSize;
    }

    public static void setMetricQueueSize(Integer size) {
        metricQueueSize = size;
    }

    public static Long getQueuePollTimeoutMilli() {
        return queuePollTimeoutMilli;
    }

    public static void setQueuePollTimeoutMilli(Long timeoutMilli) {
        queuePollTimeoutMilli = timeoutMilli;
    }

    public static String getMetricNameSpace() {
        return metricNameSpace;
    }

    public static void setMetricNameSpace(String metricNameSpace2) {
        if (metricNameSpace2 == null || metricNameSpace2.trim().length() == 0) {
            throw new IllegalArgumentException();
        }
        metricNameSpace = metricNameSpace2;
    }

    public static String getJvmMetricName() {
        return jvmMetricName;
    }

    public static void setJvmMetricName(String jvmMetricName2) {
        jvmMetricName = jvmMetricName2;
    }

    public static String getHostMetricName() {
        return hostMetricName;
    }

    public static void setHostMetricName(String hostMetricName2) {
        hostMetricName = hostMetricName2;
    }

    public static class MetricRegistry {
        private final Set<MetricType> metricTypes;
        private volatile Set<MetricType> readOnly;

        MetricRegistry() {
            HashSet hashSet = new HashSet();
            this.metricTypes = hashSet;
            hashSet.add(AWSRequestMetrics.Field.ClientExecuteTime);
            hashSet.add(AWSRequestMetrics.Field.Exception);
            hashSet.add(AWSRequestMetrics.Field.HttpClientRetryCount);
            hashSet.add(AWSRequestMetrics.Field.HttpRequestTime);
            hashSet.add(AWSRequestMetrics.Field.RequestCount);
            hashSet.add(AWSRequestMetrics.Field.RetryCount);
            hashSet.add(AWSRequestMetrics.Field.HttpClientSendRequestTime);
            hashSet.add(AWSRequestMetrics.Field.HttpClientReceiveResponseTime);
            hashSet.add(AWSRequestMetrics.Field.HttpClientPoolAvailableCount);
            hashSet.add(AWSRequestMetrics.Field.HttpClientPoolLeasedCount);
            hashSet.add(AWSRequestMetrics.Field.HttpClientPoolPendingCount);
            hashSet.add(AWSServiceMetrics.HttpClientGetConnectionTime);
            syncReadOnly();
        }

        private void syncReadOnly() {
            this.readOnly = Collections.unmodifiableSet(new HashSet(this.metricTypes));
        }

        public boolean addMetricType(MetricType type) {
            boolean added;
            synchronized (this.metricTypes) {
                added = this.metricTypes.add(type);
                if (added) {
                    syncReadOnly();
                }
            }
            return added;
        }

        public <T extends MetricType> boolean addMetricTypes(Collection<T> types) {
            boolean added;
            synchronized (this.metricTypes) {
                added = this.metricTypes.addAll(types);
                if (added) {
                    syncReadOnly();
                }
            }
            return added;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
            if (r3.size() == 0) goto L_0x000b;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public <T extends com.amazonaws.metrics.MetricType> void setMetricTypes(java.util.Collection<T> r3) {
            /*
                r2 = this;
                java.util.Set<com.amazonaws.metrics.MetricType> r0 = r2.metricTypes
                monitor-enter(r0)
                if (r3 == 0) goto L_0x000b
                int r1 = r3.size()     // Catch:{ all -> 0x002c }
                if (r1 != 0) goto L_0x001c
            L_0x000b:
                java.util.Set<com.amazonaws.metrics.MetricType> r1 = r2.metricTypes     // Catch:{ all -> 0x002c }
                int r1 = r1.size()     // Catch:{ all -> 0x002c }
                if (r1 != 0) goto L_0x0015
                monitor-exit(r0)     // Catch:{ all -> 0x002c }
                return
            L_0x0015:
                if (r3 != 0) goto L_0x001c
                java.util.List r1 = java.util.Collections.emptyList()     // Catch:{ all -> 0x002c }
                r3 = r1
            L_0x001c:
                java.util.Set<com.amazonaws.metrics.MetricType> r1 = r2.metricTypes     // Catch:{ all -> 0x002c }
                r1.clear()     // Catch:{ all -> 0x002c }
                boolean r1 = r2.addMetricTypes(r3)     // Catch:{ all -> 0x002c }
                if (r1 != 0) goto L_0x002a
                r2.syncReadOnly()     // Catch:{ all -> 0x002c }
            L_0x002a:
                monitor-exit(r0)     // Catch:{ all -> 0x002c }
                return
            L_0x002c:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002c }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.metrics.AwsSdkMetrics.MetricRegistry.setMetricTypes(java.util.Collection):void");
        }

        public boolean removeMetricType(MetricType type) {
            boolean removed;
            synchronized (this.metricTypes) {
                removed = this.metricTypes.remove(type);
                if (removed) {
                    syncReadOnly();
                }
            }
            return removed;
        }

        public Set<MetricType> predefinedMetrics() {
            return this.readOnly;
        }
    }
}
