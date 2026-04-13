package com.alibaba.fastjson.support.jaxrs;

import javax.annotation.Priority;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;

@Priority(1999)
public class FastJsonAutoDiscoverable implements AutoDiscoverable {
    public static final String FASTJSON_AUTO_DISCOVERABLE = "fastjson.auto.discoverable";
    public static volatile boolean autoDiscover;

    static {
        autoDiscover = true;
        try {
            autoDiscover = Boolean.parseBoolean(System.getProperty(FASTJSON_AUTO_DISCOVERABLE, String.valueOf(autoDiscover)));
        } catch (Throwable th) {
        }
    }

    public void configure(FeatureContext context) {
        if (!context.getConfiguration().isRegistered(FastJsonFeature.class) && autoDiscover) {
            context.register(FastJsonFeature.class);
        }
    }
}
