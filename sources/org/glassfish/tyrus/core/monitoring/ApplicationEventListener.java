package org.glassfish.tyrus.core.monitoring;

import org.glassfish.tyrus.core.Beta;

@Beta
public interface ApplicationEventListener {
    public static final String APPLICATION_EVENT_LISTENER = "org.glassfish.tyrus.core.monitoring.ApplicationEventListener";
    public static final ApplicationEventListener NO_OP = new ApplicationEventListener() {
        public void onApplicationInitialized(String applicationName) {
        }

        public void onApplicationDestroyed() {
        }

        public EndpointEventListener onEndpointRegistered(String endpointPath, Class<?> cls) {
            return EndpointEventListener.NO_OP;
        }

        public void onEndpointUnregistered(String endpointPath) {
        }
    };

    void onApplicationDestroyed();

    void onApplicationInitialized(String str);

    EndpointEventListener onEndpointRegistered(String str, Class<?> cls);

    void onEndpointUnregistered(String str);
}
