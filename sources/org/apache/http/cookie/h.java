package org.apache.http.cookie;

import java.util.Comparator;
import java.util.Date;
import org.apache.http.impl.cookie.d;

/* compiled from: CookiePriorityComparator */
public class h implements Comparator<c> {
    public static final h c = new h();

    private int b(c cookie) {
        String path = cookie.getPath();
        if (path != null) {
            return path.length();
        }
        return 1;
    }

    /* renamed from: a */
    public int compare(c c1, c c2) {
        int result = b(c2) - b(c1);
        if (result == 0 && (c1 instanceof d) && (c2 instanceof d)) {
            Date d1 = ((d) c1).getCreationDate();
            Date d2 = ((d) c2).getCreationDate();
            if (!(d1 == null || d2 == null)) {
                return (int) (d1.getTime() - d2.getTime());
            }
        }
        return result;
    }
}
