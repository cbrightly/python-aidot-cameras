package org.apache.http.conn.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.conn.routing.b;
import org.apache.http.util.a;

@Deprecated
public final class ConnPerRouteBean implements a {
    public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
    private volatile int defaultMax;
    private final ConcurrentHashMap<b, Integer> maxPerHostMap;

    public ConnPerRouteBean(int defaultMax2) {
        this.maxPerHostMap = new ConcurrentHashMap<>();
        setDefaultMaxPerRoute(defaultMax2);
    }

    public ConnPerRouteBean() {
        this(2);
    }

    public int getDefaultMax() {
        return this.defaultMax;
    }

    public int getDefaultMaxPerRoute() {
        return this.defaultMax;
    }

    public void setDefaultMaxPerRoute(int max) {
        a.j(max, "Default max per route");
        this.defaultMax = max;
    }

    public void setMaxForRoute(b route, int max) {
        a.i(route, "HTTP route");
        a.j(max, "Max per route");
        this.maxPerHostMap.put(route, Integer.valueOf(max));
    }

    public int getMaxForRoute(b route) {
        a.i(route, "HTTP route");
        Integer max = this.maxPerHostMap.get(route);
        if (max != null) {
            return max.intValue();
        }
        return this.defaultMax;
    }

    public void setMaxForRoutes(Map<b, Integer> map) {
        if (map != null) {
            this.maxPerHostMap.clear();
            this.maxPerHostMap.putAll(map);
        }
    }

    public String toString() {
        return this.maxPerHostMap.toString();
    }
}
