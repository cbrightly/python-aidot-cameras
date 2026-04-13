package org.apache.http.conn.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.logging.h;
import org.apache.http.b;
import org.apache.http.util.a;

/* compiled from: PublicSuffixMatcherLoader */
public final class f {
    private static volatile e a;

    private static e b(InputStream in) {
        return new e(new d().a(new InputStreamReader(in, b.a)));
    }

    public static e c(URL url) {
        a.i(url, "URL");
        InputStream in = url.openStream();
        try {
            return b(in);
        } finally {
            in.close();
        }
    }

    public static e a() {
        Class<f> cls = f.class;
        if (a == null) {
            synchronized (cls) {
                if (a == null) {
                    URL url = cls.getResource("/mozilla/public-suffix-list.txt");
                    if (url != null) {
                        try {
                            a = c(url);
                        } catch (IOException ex) {
                            org.apache.commons.logging.a log = h.n(cls);
                            if (log.isWarnEnabled()) {
                                log.warn("Failure loading public suffix list from default resource", ex);
                            }
                        }
                    } else {
                        a = new e(Arrays.asList(new String[]{"com"}), (Collection<String>) null);
                    }
                }
            }
        }
        return a;
    }
}
