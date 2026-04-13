package com.alibaba.android.arouter.facade.enums;

/* compiled from: RouteType */
public enum a {
    ACTIVITY(0, "android.app.Activity"),
    SERVICE(1, "android.app.Service"),
    PROVIDER(2, "com.alibaba.android.arouter.facade.template.IProvider"),
    CONTENT_PROVIDER(-1, "android.app.ContentProvider"),
    BOARDCAST(-1, ""),
    METHOD(-1, ""),
    FRAGMENT(-1, "android.app.Fragment"),
    UNKNOWN(-1, "Unknown route type");
    
    String className;
    int id;

    public int getId() {
        return this.id;
    }

    public a setId(int id2) {
        this.id = id2;
        return this;
    }

    public String getClassName() {
        return this.className;
    }

    public a setClassName(String className2) {
        this.className = className2;
        return this;
    }

    private a(int id2, String className2) {
        this.id = id2;
        this.className = className2;
    }

    public static a parse(String name) {
        for (a routeType : values()) {
            if (routeType.getClassName().equals(name)) {
                return routeType;
            }
        }
        return UNKNOWN;
    }
}
