package com.google.maps.android.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class Feature extends Observable {
    private Geometry mGeometry;
    protected String mId;
    private final Map<String, String> mProperties;

    public Feature(Geometry featureGeometry, String id, Map<String, String> properties) {
        this.mGeometry = featureGeometry;
        this.mId = id;
        if (properties == null) {
            this.mProperties = new HashMap();
        } else {
            this.mProperties = properties;
        }
    }

    public Iterable<String> getPropertyKeys() {
        return this.mProperties.keySet();
    }

    public Iterable getProperties() {
        return this.mProperties.entrySet();
    }

    public String getProperty(String property) {
        return this.mProperties.get(property);
    }

    public String getId() {
        return this.mId;
    }

    public boolean hasProperty(String property) {
        return this.mProperties.containsKey(property);
    }

    public Geometry getGeometry() {
        return this.mGeometry;
    }

    public boolean hasProperties() {
        return this.mProperties.size() > 0;
    }

    public boolean hasGeometry() {
        return this.mGeometry != null;
    }

    /* access modifiers changed from: protected */
    public String setProperty(String property, String propertyValue) {
        return this.mProperties.put(property, propertyValue);
    }

    /* access modifiers changed from: protected */
    public String removeProperty(String property) {
        return this.mProperties.remove(property);
    }

    /* access modifiers changed from: protected */
    public void setGeometry(Geometry geometry) {
        this.mGeometry = geometry;
    }
}
