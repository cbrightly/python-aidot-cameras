package org.glassfish.tyrus.core.wsadl.model;

import java.util.ArrayList;
import java.util.List;

public class Application {
    protected List<Object> any;
    protected List<Endpoint> endpoint;

    public List<Endpoint> getEndpoint() {
        if (this.endpoint == null) {
            this.endpoint = new ArrayList();
        }
        return this.endpoint;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList();
        }
        return this.any;
    }
}
