package com.alibaba.fastjson.support.jaxrs;

import java.util.Map;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.util.PropertiesHelper;

public class FastJsonFeature implements Feature {
    private static final String JSON_FEATURE = FastJsonFeature.class.getSimpleName();

    public boolean configure(FeatureContext context) {
        try {
            Configuration config = context.getConfiguration();
            Map properties = config.getProperties();
            RuntimeType runtimeType = config.getRuntimeType();
            String str = JSON_FEATURE;
            if (!str.equalsIgnoreCase((String) CommonProperties.getValue(properties, runtimeType, "jersey.config.jsonFeature", str, String.class))) {
                return false;
            }
            context.property(PropertiesHelper.getPropertyNameForRuntime("jersey.config.jsonFeature", config.getRuntimeType()), str);
            if (!config.isRegistered(FastJsonProvider.class)) {
                context.register(FastJsonProvider.class, new Class[]{MessageBodyReader.class, MessageBodyWriter.class});
            }
            return true;
        } catch (NoSuchMethodError e) {
        }
    }
}
