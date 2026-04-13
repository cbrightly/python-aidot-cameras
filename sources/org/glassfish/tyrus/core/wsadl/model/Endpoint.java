package org.glassfish.tyrus.core.wsadl.model;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public class Endpoint {
    protected String id;
    private Map<QName, String> otherAttributes = new HashMap();
    protected String path;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String value) {
        this.path = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}
