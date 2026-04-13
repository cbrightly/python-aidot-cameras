package org.apache.commons.logging;

import java.io.IOException;
import java.security.PrivilegedAction;

/* compiled from: LogFactory */
public final class e implements PrivilegedAction {
    private final /* synthetic */ ClassLoader a;
    private final /* synthetic */ String b;

    e(ClassLoader classLoader, String str) {
        this.a = classLoader;
        this.b = str;
    }

    public Object run() {
        try {
            ClassLoader classLoader = this.a;
            if (classLoader != null) {
                return classLoader.getResources(this.b);
            }
            return ClassLoader.getSystemResources(this.b);
        } catch (IOException e) {
            if (h.w()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Exception while trying to find configuration file ");
                stringBuffer.append(this.b);
                stringBuffer.append(":");
                stringBuffer.append(e.getMessage());
                h.y(stringBuffer.toString());
            }
            return null;
        } catch (NoSuchMethodError e2) {
            return null;
        }
    }
}
