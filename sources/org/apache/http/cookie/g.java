package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;

/* compiled from: CookiePathComparator */
public class g implements Serializable, Comparator<c> {
    public static final g INSTANCE = new g();
    private static final long serialVersionUID = 7523645369616405818L;

    private String a(c cookie) {
        String path = cookie.getPath();
        if (path == null) {
            path = "/";
        }
        if (path.endsWith("/")) {
            return path;
        }
        return path + '/';
    }

    public int compare(c c1, c c2) {
        String path1 = a(c1);
        String path2 = a(c2);
        if (path1.equals(path2)) {
            return 0;
        }
        if (path1.startsWith(path2)) {
            return -1;
        }
        if (path2.startsWith(path1)) {
            return 1;
        }
        return 0;
    }
}
