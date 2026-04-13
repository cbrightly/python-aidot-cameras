package org.apache.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/* compiled from: VersionInfo */
public class k {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;

    protected k(String pckg, String module, String release, String time, String clsldr) {
        a.i(pckg, "Package identifier");
        this.a = pckg;
        String str = "UNAVAILABLE";
        this.b = module != null ? module : str;
        this.c = release != null ? release : str;
        this.d = time != null ? time : str;
        this.e = clsldr != null ? clsldr : str;
    }

    public final String b() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.a.length() + 20 + this.b.length() + this.c.length() + this.d.length() + this.e.length());
        sb.append("VersionInfo(");
        sb.append(this.a);
        sb.append(':');
        sb.append(this.b);
        if (!"UNAVAILABLE".equals(this.c)) {
            sb.append(':');
            sb.append(this.c);
        }
        if (!"UNAVAILABLE".equals(this.d)) {
            sb.append(':');
            sb.append(this.d);
        }
        sb.append(')');
        if (!"UNAVAILABLE".equals(this.e)) {
            sb.append('@');
            sb.append(this.e);
        }
        return sb.toString();
    }

    public static k d(String pckg, ClassLoader clsldr) {
        InputStream is;
        a.i(pckg, "Package identifier");
        ClassLoader cl = clsldr != null ? clsldr : Thread.currentThread().getContextClassLoader();
        Properties vip = null;
        try {
            is = cl.getResourceAsStream(pckg.replace('.', '/') + "/" + "version.properties");
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                vip = props;
                is.close();
            }
        } catch (IOException e2) {
        } catch (Throwable th) {
            is.close();
            throw th;
        }
        if (vip != null) {
            return a(pckg, vip, cl);
        }
        return null;
    }

    protected static k a(String pckg, Map<?, ?> info, ClassLoader clsldr) {
        String clsldrstr;
        a.i(pckg, "Package identifier");
        String module = null;
        String release = null;
        String timestamp = null;
        if (info != null) {
            module = (String) info.get("info.module");
            if (module != null && module.length() < 1) {
                module = null;
            }
            release = (String) info.get("info.release");
            if (release != null && (release.length() < 1 || release.equals("${pom.version}"))) {
                release = null;
            }
            timestamp = (String) info.get("info.timestamp");
            if (timestamp != null && (timestamp.length() < 1 || timestamp.equals("${mvn.timestamp}"))) {
                timestamp = null;
            }
        }
        if (clsldr != null) {
            clsldrstr = clsldr.toString();
        } else {
            clsldrstr = null;
        }
        return new k(pckg, module, release, timestamp, clsldrstr);
    }

    public static String c(String name, String pkg, Class<?> cls) {
        k vi = d(pkg, cls.getClassLoader());
        return String.format("%s/%s (Java/%s)", new Object[]{name, vi != null ? vi.b() : "UNAVAILABLE", System.getProperty("java.version")});
    }
}
