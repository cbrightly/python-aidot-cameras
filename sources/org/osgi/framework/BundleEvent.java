package org.osgi.framework;

import java.util.EventObject;

public class BundleEvent extends EventObject {
    public static final int INSTALLED = 1;
    public static final int LAZY_ACTIVATION = 512;
    public static final int RESOLVED = 32;
    public static final int STARTED = 2;
    public static final int STARTING = 128;
    public static final int STOPPED = 4;
    public static final int STOPPING = 256;
    public static final int UNINSTALLED = 16;
    public static final int UNRESOLVED = 64;
    public static final int UPDATED = 8;
    static final long serialVersionUID = 4080640865971756012L;
    private final Bundle bundle;
    private final Bundle origin;
    private final int type;

    public BundleEvent(int type2, Bundle bundle2, Bundle origin2) {
        super(bundle2);
        if (origin2 != null) {
            this.bundle = bundle2;
            this.type = type2;
            this.origin = origin2;
            return;
        }
        throw new IllegalArgumentException("null origin");
    }

    public BundleEvent(int type2, Bundle bundle2) {
        super(bundle2);
        this.bundle = bundle2;
        this.type = type2;
        this.origin = bundle2;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public int getType() {
        return this.type;
    }

    public Bundle getOrigin() {
        return this.origin;
    }
}
