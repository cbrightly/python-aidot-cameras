package org.osgi.framework;

import java.security.AccessController;
import java.security.PrivilegedAction;

/* compiled from: FrameworkUtil */
public class c {

    /* compiled from: FrameworkUtil */
    public static final class a implements PrivilegedAction<Object> {
        final /* synthetic */ Class a;

        a(Class cls) {
            this.a = cls;
        }

        public Object run() {
            return this.a.getClassLoader();
        }
    }

    public static Bundle a(Class<?> classFromBundle) {
        Object cl = AccessController.doPrivileged(new a(classFromBundle));
        if (cl instanceof b) {
            return ((b) cl).d();
        }
        return null;
    }
}
