package com.amazonaws.http.conn;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.metrics.AwsSdkMetrics;
import com.amazonaws.metrics.ServiceLatencyProvider;
import com.amazonaws.util.AWSServiceMetrics;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.http.conn.e;

public class ClientConnectionRequestFactory {
    private static final Class<?>[] INTERFACES = {e.class, Wrapped.class};
    /* access modifiers changed from: private */
    public static final Log log = LogFactory.getLog(ClientConnectionRequestFactory.class);

    ClientConnectionRequestFactory() {
    }

    static e wrap(e orig) {
        if (!(orig instanceof Wrapped)) {
            return (e) Proxy.newProxyInstance(ClientConnectionRequestFactory.class.getClassLoader(), INTERFACES, new Handler(orig));
        }
        throw new IllegalArgumentException();
    }

    public static class Handler implements InvocationHandler {
        private final e orig;

        Handler(e orig2) {
            this.orig = orig2;
        }

        public Object invoke(Object proxy, Method method, Object[] args) {
            ServiceLatencyProvider latencyProvider;
            try {
                if (!"getConnection".equals(method.getName())) {
                    return method.invoke(this.orig, args);
                }
                latencyProvider = new ServiceLatencyProvider(AWSServiceMetrics.HttpClientGetConnectionTime);
                Object invoke = method.invoke(this.orig, args);
                AwsSdkMetrics.getServiceMetricCollector().collectLatency(latencyProvider.endTiming());
                return invoke;
            } catch (InvocationTargetException e) {
                ClientConnectionRequestFactory.log.debug("", e);
                throw e.getCause();
            } catch (Throwable th) {
                AwsSdkMetrics.getServiceMetricCollector().collectLatency(latencyProvider.endTiming());
                throw th;
            }
        }
    }
}
