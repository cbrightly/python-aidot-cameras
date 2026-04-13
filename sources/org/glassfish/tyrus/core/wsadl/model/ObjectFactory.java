package org.glassfish.tyrus.core.wsadl.model;

public class ObjectFactory {
    public Application createApplication() {
        return new Application();
    }

    public Endpoint createEndpoint() {
        return new Endpoint();
    }
}
