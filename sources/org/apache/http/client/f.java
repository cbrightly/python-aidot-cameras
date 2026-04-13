package org.apache.http.client;

import java.util.Date;
import java.util.List;
import org.apache.http.cookie.c;

/* compiled from: CookieStore */
public interface f {
    void addCookie(c cVar);

    boolean clearExpired(Date date);

    List<c> getCookies();
}
