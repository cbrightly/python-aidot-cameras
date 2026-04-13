package org.glassfish.grizzly.jmxbase;

import java.util.Iterator;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.ServiceFinder;

public abstract class GrizzlyJmxManager {
    private static final GrizzlyJmxManager manager;

    public abstract void deregister(Object obj);

    public abstract Object register(Object obj, Object obj2);

    public abstract Object register(Object obj, Object obj2, String str);

    public abstract Object registerAtRoot(Object obj);

    public abstract Object registerAtRoot(Object obj, String str);

    static {
        GrizzlyJmxManager jmxManager;
        Iterator<GrizzlyJmxManager> it = ServiceFinder.find(GrizzlyJmxManager.class).iterator();
        if (it.hasNext()) {
            jmxManager = it.next();
        } else {
            try {
                jmxManager = (GrizzlyJmxManager) loadClass("org.glassfish.grizzly.monitoring.jmx.DefaultJmxManager").newInstance();
            } catch (Exception e) {
                jmxManager = new NullJmxManager();
            }
        }
        manager = jmxManager;
    }

    private static Class<?> loadClass(String classname) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = MonitoringUtils.class.getClassLoader();
        }
        return classLoader.loadClass(classname);
    }

    public static GrizzlyJmxManager instance() {
        return manager;
    }

    public static final class NullJmxManager extends GrizzlyJmxManager {
        private NullJmxManager() {
        }

        public Object registerAtRoot(Object object) {
            return null;
        }

        public Object registerAtRoot(Object object, String name) {
            return null;
        }

        public Object register(Object parent, Object object) {
            return null;
        }

        public Object register(Object parent, Object object, String name) {
            return null;
        }

        public void deregister(Object object) {
        }
    }
}
